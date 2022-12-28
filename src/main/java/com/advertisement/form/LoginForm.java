package com.advertisement.form;

import lombok.Data;

@Data
public class LoginForm {
    /** 账号/手机号 */
    private String phone;
    /** 验证码 */
    private String code;
}
