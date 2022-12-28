package com.advertisement.controller;

import com.advertisement.common.R;
import com.advertisement.common.SysConstants;
import com.advertisement.utils.JedisUtil;
import com.advertisement.utils.SmsUtil;
import com.advertisement.utils.Tools;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统相关的通用接口
 */
@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private JedisUtil jedisUtil;

    @GetMapping("sendSms")
    public R sendSms(@RequestParam String phone){
        phone = phone.trim();
        Jedis jedis = jedisUtil.getJedis();
        if(jedis.exists(SysConstants.LOGIN_SMS + phone)){
            return R.fail("验证码已经发送，请勿重复点击");
        }
        String code = String.valueOf(Tools.getRandomNum4());
        Map<String, String> params = new HashMap<>();
        params.put("code",code);
        System.out.println("验证码是code = " + code);
        String content = "【保理审批管理系统】您的登录密码是："+code+"。如非本人操作，请忽略本短信。";
        JSONObject response = SmsUtil.marketin(phone,content);
        if(response.get("resultCode").equals("1")){
            jedis.setex(SysConstants.LOGIN_SMS + phone,60*5, code);
            return R.ok();
        }
        return R.fail(response.get("resultCode").toString());
    }
}
