package com.advertisement.common;

/**
 * 系统常量表
 */
public class SysConstants {
    /**
     * 登录认证header
     */
    public static final String AUTH_HEADER = "auth";

    /**
     * token过期时间 ，单位是秒 默认是7天
     */
    public static final int  TOKEN_EXPIRE = 604800;

    /**
     * 加密SECRET
     */
    public static final String TOKEN_SECRET = "DFT_ADVERTISEMENT_USER";

    /**
     * 登录短信验证码前缀
     */
    public static final String LOGIN_SMS = "GG_LOGIN_SMS:";



}
