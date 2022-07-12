package com.nudity.entity;

import lombok.Data;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 20:56
 */
@Data
public class DanJuanJiJinHistoryDataBody {

    private String date;

    /**
     * 净值
     */
    private String nav;

    /**
     * 日涨幅
     */
    private String percentage;

    /**
     * 净值
     * 2022-05-23 暂时未发现用处
     */
    private String value;
}
