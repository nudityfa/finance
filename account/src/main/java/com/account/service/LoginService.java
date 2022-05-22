package com.account.service;

import com.account.controller.dto.LoginDTO;
import com.account.controller.vo.LoginVo;
import com.account.dao.LoginMapper;
import com.account.entity.Account;
import com.adminUtil.commonUtil.JwtUtil;
import com.adminUtil.exception.MyException;
import com.adminUtil.redis.RedisKey;
import com.alibaba.fastjson.JSON;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/2 23:36
 */
@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedissonClient redissonClient;

    public LoginVo selectAccountByUsername(LoginDTO dto) {
        Account account = loginMapper.selectAccountByUsername(dto.getUsername());
        if (account == null){
            throw new MyException(String.format("用户名%s，账号不存在",dto.getUsername()));
        }
        if (!dto.getPassword().equals(account.getPassword())){
            throw new MyException(String.format("用户名%s，密码错误",dto.getUsername()));
        }
        String token;
        try {
            token = JwtUtil.sign(account.getPassword(),account.getUsername(),account.getId());
        } catch (UnsupportedEncodingException e) {
            throw new MyException("token生成失败");
        }
        if (token == null){
            throw new MyException("token生成失败");
        }
        loginMapper.incLoginFrequency(account.getId(),new Date());
        LoginVo vo = new LoginVo();
        vo.setToken(token);
        redissonClient.getBucket(RedisKey.token.getValue() + dto.getUsername()).set(token,30, TimeUnit.MINUTES);
        return vo;
    }

    public Account test() {
        Account account = new Account();
        account.setPassword("sb");
        account.setUsername("ai");
        Codec codec = redissonClient.getConfig().getCodec();
        String admin = redissonClient.getBucket("admin").get().toString();
        redissonClient.getBucket("test").set(account);
        Object a = redissonClient.getBucket("test").get();
        Account ab = JSON.parseObject(a.toString(),Account.class);
        ab.setId(admin);
        return ab;
    }

    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (token != null){
            String username = JwtUtil.getUsername(token);
            redissonClient.getKeys().delete(RedisKey.token.getValue() + username);
        }

    }
}
