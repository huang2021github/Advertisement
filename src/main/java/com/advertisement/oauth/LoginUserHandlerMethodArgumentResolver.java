package com.advertisement.oauth;

import com.advertisement.common.SysConstants;
import com.advertisement.domain.SysAccountEntity;
import com.advertisement.service.SysAccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@AppLoginUser注解的方法参数，注入当前登录用户
 * 标注了@AppLoginUser就会注入AppUserEntity对象
 * 主要是给小程序用
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private SysAccountService sysAccountService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(SysAccountEntity.class)
                || parameter.getParameterType().isAssignableFrom(SysAccountEntity.class))
                && parameter.hasParameterAnnotation(UserLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(SysConstants.PHONE, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }
        if (parameter.getParameterType().isAssignableFrom(SysAccountEntity.class)) {
            //获取用户信息
            return sysAccountService.getOne(new LambdaQueryWrapper<SysAccountEntity>().eq(SysAccountEntity::getPhone, object));
        }
        return null;
    }
}
