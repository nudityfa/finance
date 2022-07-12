package com.nudity.timer;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.adminUtil.redis.RedisKey;
import com.alibaba.fastjson.JSON;
import com.nudity.dao.JiJinMapper;
import com.nudity.entity.DanJuanJiJinTopBody;
import com.nudity.entity.DanJuanJiJinTypeEnum;
import com.nudity.service.JiJinService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 * 爬数据
 * @author luojinhua
 * @since 2022/5/22 21:49
 */
@Async
@Component
public class ScheduledBug {

    private static final Logger log = LoggerFactory.getLogger(ScheduledBug.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private JiJinService jiJinService;

    private static final String OPEN_FLAG = "1";

    private static final String CLOSE_FLAG = "0";

    public static final String JI_JIN_DATA_TOP_100_URL = "https://danjuanapp.com/djapi/v3/filter/fund?";

    public static final String JI_JIN_HISTORY_DATA = "https://danjuanapp.com/djapi/fund/nav/history/";

    public static final Map<String,String> headerMap = new HashMap<>(1);
    static{
        headerMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36");
    }

    /**
     * 获取基金数据,默认类型是  股票型  日涨幅
     * 只写入缓存中
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void getFinanceJiJinDataTop100(){
        log.warn("基金数据每日top100爬取开始==========================================================");
        Object open = redissonClient.getBucket(RedisKey.ji_jin_data_top_100_open.getValue()).get();
        if (open == null || CLOSE_FLAG.equals(open.toString())){
            log.warn("基金数据top100爬取开关未打开");
        }else {
            jiJinService.doRollTop100();
        }
        log.warn("基金数据每日top100爬取关闭==========================================================");
    }
}
