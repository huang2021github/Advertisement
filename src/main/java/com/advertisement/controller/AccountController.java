package com.advertisement.controller;

import cn.hutool.json.JSONUtil;
import com.advertisement.common.R;
import com.advertisement.domain.SysAccountEntity;
import com.advertisement.domain.ro.LoginResponse;
import com.advertisement.form.LoginForm;
import com.advertisement.form.SysAccountForm;
import com.advertisement.service.SysAccountService;
import com.advertisement.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    SysAccountService sysAccountService;

    //获取到全部用户信息（测试）
    @PostMapping("accountList")
    public R<PageUtils<SysAccountEntity>> accountList(@RequestBody SysAccountForm form,SysAccountEntity sysAccountEntity){

        if(sysAccountEntity.getRole() == null){
            return R.fail("暂无权限1");
        }
        return R.ok(sysAccountService.accountList(form));
    }

    //登录测试
    @PostMapping("/login")
    public R<LoginResponse> login(@RequestBody LoginForm loginForm){
        log.info("satart exec user login form:{}", JSONUtil.toJsonStr(loginForm));
        return sysAccountService.login(loginForm);
    }

}
