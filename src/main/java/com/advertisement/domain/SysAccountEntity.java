package com.advertisement.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_account")
public class SysAccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 手机号/账号
     */
    private String phone;
    /**
     * 名称
     */
    private String name;

    /**
     * 角色 0：管理员，1：
     */
    private Integer role;

    /**
     * 状态：0：停用，1：正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
