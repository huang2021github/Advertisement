package com.advertisement.oauth;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.advertisement.common.SysConstants;
import com.advertisement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，配合JWT一起使用
 */
@Component
public class UserAuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果当前方法上表明了 AppUserLoginVerification 注解，就会走下面的流程，最后把token放入request的attribute
        UserLoginVerification annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(UserLoginVerification.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(SysConstants.AUTH_HEADER);
        if (StrUtil.isBlank(token)) {
            //如果header里面获取不到，就从参数里面获取token
            token = request.getParameter(SysConstants.AUTH_HEADER);
        }

        //token为空
        if (StrUtil.isBlank(token)) {
            throw  new RuntimeException("401:token is empty");
        }

        if (jwtUtils.isExpired(token,SysConstants.TOKEN_SECRET)) {
            throw  new RuntimeException("401:token error");
        }
        String payloadByBase64 = jwtUtils.getPayloadByBase64(token);
        JSONObject jsonObject = JSONUtil.parseObj(payloadByBase64);
        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(SysConstants.PHONE, (String)jsonObject.get(SysConstants.LOGIN_SMS));

        return true;
    }
}
