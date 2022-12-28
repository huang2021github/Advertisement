package com.advertisement.domain.ro;

import lombok.Data;

@Data
public class LoginResponse {
    /** token */
    private String token;
    /** 过期时间, 单位:秒 */
    private Integer expire;
    /** 角色权限 */
    private Integer role;
}
