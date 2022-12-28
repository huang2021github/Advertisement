package com.advertisement.service;

import com.advertisement.common.R;
import com.advertisement.domain.SysAccountEntity;
import com.advertisement.domain.ro.LoginResponse;
import com.advertisement.form.LoginForm;
import com.advertisement.form.SysAccountForm;
import com.advertisement.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

public interface SysAccountService extends IService<SysAccountEntity> {

    //测试接口获取全部
    PageUtils<SysAccountEntity> accountList(SysAccountForm form);

    Optional<SysAccountEntity> findByPhone(String phone);

    R<LoginResponse> login(LoginForm form);
}
