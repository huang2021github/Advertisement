package com.advertisement.service.impl;

import com.advertisement.common.AccountConstants;
import com.advertisement.common.R;
import com.advertisement.common.SysConstants;
import com.advertisement.dao.SysAccountDao;
import com.advertisement.domain.SysAccountEntity;
import com.advertisement.domain.ro.LoginResponse;
import com.advertisement.form.LoginForm;
import com.advertisement.form.SysAccountForm;
import com.advertisement.service.SysAccountService;
import com.advertisement.utils.JedisUtil;
import com.advertisement.utils.JwtUtils;
import com.advertisement.utils.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service("sysAccountService")
public class SysAccountServiceImpl extends ServiceImpl<SysAccountDao, SysAccountEntity> implements SysAccountService {

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public PageUtils<SysAccountEntity> accountList(SysAccountForm form) {

        //获取总条数
        Integer count = this.count();

        if(count == 0){
            return new PageUtils(new ArrayList<SysAccountEntity>(),0, form.getLimit(), form.getPage());
        }
        if(form.getPage() - 1 < 0){
            form.setPageStart(0);
        }else {
            form.setPageStart((form.getPage() - 1) * form.getLimit());
        }

        List<SysAccountEntity> list = this.list();

        return new PageUtils(list,count, form.getLimit(), form.getPage());
    }


    @Override
    public Optional<SysAccountEntity> findByPhone(String phone) {
        return Optional.ofNullable(this.getOne(new LambdaQueryWrapper<SysAccountEntity>().eq(
                SysAccountEntity :: getPhone, phone)));
    }

    @Override
    public R<LoginResponse> login(LoginForm form) {
        //验证手机号
        Optional<SysAccountEntity> optionalSysAccountEntity = findByPhone(form.getPhone());

        if(!optionalSysAccountEntity.isPresent()){
            return R.fail("手机号不存在");
        }
        if(optionalSysAccountEntity.get().getStatus() != AccountConstants.NORMAL){
            return R.fail("账户已停用");
        }
        //验证短信验证码
        Jedis jedis = jedisUtil.getJedis();
        String redisCode = jedis.get(SysConstants.LOGIN_SMS + form.getPhone());
        if(!form.getCode().equalsIgnoreCase(redisCode)){
            return R.fail("验证码错误");
        }else {
            //删除验证码Token
            jedis.del(SysConstants.LOGIN_SMS + form.getPhone());
        }
        jedisUtil.close(jedis);

        //生成token
        Map<String, Object> params = new HashMap<>();
        params.put(SysConstants.LOGIN_SMS, form.getPhone());
        String token = jwtUtils.sign(params, SysConstants.TOKEN_SECRET,SysConstants.TOKEN_EXPIRE);

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpire(SysConstants.TOKEN_EXPIRE);
        response.setRole(optionalSysAccountEntity.get().getRole());

        return R.ok(response);
    }
}
