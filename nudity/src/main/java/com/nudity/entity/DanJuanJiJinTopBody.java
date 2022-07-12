package com.nudity.entity;

import lombok.Data;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/22 23:09
 */
@Data
public class DanJuanJiJinTopBody {

    private String f_type;

    //基金code
    private String fd_code;

    //基金名称
    private String fd_name;

    //基金类型
    private String sf_type;

    //净值
    private String unit_nav;

    //涨幅
    private String yield;
}
