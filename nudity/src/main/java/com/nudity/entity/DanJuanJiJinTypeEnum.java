package com.nudity.entity;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 16:23
 */
public enum DanJuanJiJinTypeEnum {
    //
    GU_PIAO_TYPE("1","股票型"),
    ZHAI_QUAN_TYPE("2","债券型"),
    HUN_HE_TYPE("3","混合型"),
    ZHI_SHU_TYPE("5","指数型"),
    Q_DII_TYPE("11","QDII型"),

    ;

    private String code;

    private String value;

    DanJuanJiJinTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }
}
