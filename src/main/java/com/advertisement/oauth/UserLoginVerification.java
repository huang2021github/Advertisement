package com.advertisement.oauth;

import java.lang.annotation.*;

/**
 * 标注上这个注解的方法，访问权限为必须已经登录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLoginVerification {
}
