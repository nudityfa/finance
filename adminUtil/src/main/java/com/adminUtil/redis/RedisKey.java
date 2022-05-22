package com.adminUtil.redis;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/4 1:10
 */
public enum RedisKey {
    //value 就是redis的key  一般用小写，方便观看，explain就是key的中文解释   最后一般都写上缓存类型
    token("token:","token:username     String"),
    set_white_list("set_white_list","写入白名单   Set"),
    ;

    private String value;

    private String explain;

    RedisKey(String value, String explain) {
        this.value = value;
        this.explain = explain;
    }

    public String getValue() {
        return value;
    }
}
