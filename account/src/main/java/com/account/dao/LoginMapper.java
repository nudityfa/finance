package com.account.dao;

import com.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/2 23:56
 */
@Mapper
public interface LoginMapper {

    /**
     * 根据用户名获取
     * @param username
     * @return
     */
    Account selectAccountByUsername(@Param("username") String username);
}
