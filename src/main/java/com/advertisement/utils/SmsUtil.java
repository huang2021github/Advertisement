package com.advertisement.utils;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmsUtil {

    private static String key = "57034fafc2312d56ba7663614de8c40f";
    //营销短信
    public static String USERNAME = "shdft";
    public static String PASSWORD = "brux1f";


    public static JSONObject marketin(String mobile, String content){
        Map<String, Object> params = new LinkedHashMap<>();
        String md5Hex1 = DigestUtil.md5Hex(USERNAME+PASSWORD+mobile+content);
        params.put("userName", USERNAME);
        params.put("sign", md5Hex1);
        params.put("mobile", mobile);
        params.put("content", content);
        String s = HttpUtil.post("https://www.sms-cly.cn/v7/msg/submit.json", JSONObject.toJSONString(params));
        return JSONObject.parseObject(s);
    }
}
