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
    dan_juan_ji_jin_type_enum("dan_juan_ji_jin_type_enum","蛋卷基金类型       Set"),
    ji_jin_data_top_100_open("ji_jin_data:top_100_open","基金数据前100爬取开关     String"),
    ji_jin_data_top_100_type("ji_jin_data:top_100_type","基金数据前100 基金类型     String"),
//    ji_jin_data_top_100_order_by("ji_jin_data:top_100_order_by","基金数据前100 基金涨幅类型     String"),
    ji_jin_data_day_of_data("ji_jin_data:day_of_data","基金数据前100 基金数据，每日划分     Hash"),
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
