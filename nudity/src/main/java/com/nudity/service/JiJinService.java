package com.nudity.service;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import com.adminUtil.exception.MyException;
import com.adminUtil.redis.RedisKey;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nudity.aop.LogAspect;
import com.nudity.controller.dto.RollBugDTO;
import com.nudity.dao.JiJinMapper;
import com.nudity.entity.DanJuanJiJinHistoryDataBody;
import com.nudity.entity.DanJuanJiJinTopBody;
import com.nudity.entity.DanJuanJiJinTypeEnum;
import com.nudity.timer.ScheduledBug;
import com.nudity.util.DanJuanJiJinUtil;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 16:38
 */
@Service
public class JiJinService {

    private static final Logger log = LoggerFactory.getLogger(JiJinService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private JiJinMapper jiJinMapper;

    public void doRollTop100() {
        String jiJinType;
        Object type = redissonClient.getBucket(RedisKey.ji_jin_data_top_100_type.getValue()).get();
        if (type != null){
            jiJinType = type.toString();
        }else {
            jiJinType = DanJuanJiJinTypeEnum.GU_PIAO_TYPE.getCode();
        }
        String url = ScheduledBug.JI_JIN_DATA_TOP_100_URL + "type=" + jiJinType + "&order_by=td&size=20&page=";
        int z = 1;
        for (int i = 1 ; i <= 5; i ++){
            List<DanJuanJiJinTopBody> list = JSON.parseArray(DanJuanJiJinUtil.getDanJuanResponseItems(url,i), DanJuanJiJinTopBody.class);
            if (list == null || list.size() == 0){
                break;
            }
            for (int j = 0; j < list.size(); j++){
                redissonClient.getScoredSortedSet(RedisKey.ji_jin_data_day_of_data.getValue()).add(z,list.get(j));
                z++;
            }
        }
    }

    public void rollBug(RollBugDTO dto) {
        String url = ScheduledBug.JI_JIN_HISTORY_DATA + dto.getCode() + "?size=30&page=";
        List<DanJuanJiJinHistoryDataBody> sortList = new ArrayList<>();
        List<DanJuanJiJinHistoryDataBody> list = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            try {
                List<DanJuanJiJinHistoryDataBody> reList = JSON.parseArray(DanJuanJiJinUtil.getDanJuanResponseItems(url,i), DanJuanJiJinHistoryDataBody.class);
                if (reList != null && reList.size() > 0){
                    list.addAll(reList);
                }
            }catch (MyException e){
                break;
            }
        }
        if (list.size() > 0){
            sortList = list.stream().sorted(Comparator.comparing(DanJuanJiJinHistoryDataBody :: getDate).reversed()).collect(Collectors.toList());
        }
        jiJinMapper.insertHistoryData(dto.getCode(),sortList);
    }

    public Collection<ScoredEntry<Object>> top10() {
        log.warn("tp[10");
        return redissonClient.getScoredSortedSet(RedisKey.ji_jin_data_day_of_data.getValue()).entryRange(1,10);
    }


}
