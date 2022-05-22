package com.gateway.entity;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/4 1:36
 */
public enum WhiteList {
    //
    login("/account/login","登录"),
    logout("/account/logout","登出"),
    ;

    private String value;

    private String explain;

    WhiteList(String value, String explain) {
        this.value = value;
        this.explain = explain;
    }

    public String getValue() {
        return value;
    }
}
