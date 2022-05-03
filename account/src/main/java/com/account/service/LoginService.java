package com.account.service;

import com.account.controller.dto.LoginDTO;
import com.account.controller.vo.LoginVo;
import com.account.dao.LoginMapper;
import com.account.entity.Account;
import com.account.util.JwtUtil;
import com.adminUtil.exception.MyException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

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
            token = JwtUtil.sign(account);
        } catch (UnsupportedEncodingException e) {
            throw new MyException("token生成失败");
        }
        if (token == null){
            throw new MyException("token生成失败");
        }
        LoginVo vo = new LoginVo();
        vo.setToken(token);
        redissonClient.getBucket(dto.getUsername()).set(token);
        return vo;
    }
}
