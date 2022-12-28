package com.advertisement.oauth;

import java.lang.annotation.*;

/**
 * 登录角色注入
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLogin {
}
