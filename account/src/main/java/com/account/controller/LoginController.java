package com.account.controller;

import com.account.controller.dto.*;
import com.account.service.LoginService;
import com.adminUtil.commonUtil.ResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/2 23:35
 */
@Api(value = "账号模块",tags = "账号模块")
@RestController
@RequestMapping("/account/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public ResponseBean login(@RequestBody LoginDTO dto){
        return ResponseBean.success(loginService.selectAccountByUsername(dto));
    }


    @ApiOperation(value = "登出")
    @PostMapping("logout")
    public ResponseBean logout(){
        loginService.logout();
        return ResponseBean.success();
    }

    @ApiOperation(value = "测试")
    @PostMapping("test")
    public ResponseBean test(){
        return ResponseBean.success(loginService.test());
    }
}
