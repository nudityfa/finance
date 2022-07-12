package com.nudity.dao;

import com.nudity.entity.DanJuanJiJinHistoryDataBody;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/22 23:17
 */
@Mapper
public interface JiJinMapper {

    /**
     * 写入基金数据
     * @param code
     * @param list
     */
    void insertHistoryData(@Param("code") String code,@Param("list") List<DanJuanJiJinHistoryDataBody> list);
}
