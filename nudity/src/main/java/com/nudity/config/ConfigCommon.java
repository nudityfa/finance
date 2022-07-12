package com.nudity.config;

import com.adminUtil.redis.RedisKey;
import com.nudity.entity.DanJuanJiJinTypeEnum;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @description
 * 启动的时候加载 写入 白名单
 * @author luojinhua
 * @date 2021/5/26 18:13
 * @param
 * @return
 */
@Component
public class ConfigCommon implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConfigCommon.class);

    @Autowired
    private RedissonClient redissonClient;

    public void setDanJuanJiJinTypeInRedis(){
        log.info("正在执行初始化写入蛋卷基金类型至redis内存");
        if (DanJuanJiJinTypeEnum.values().length > 0){
            for (DanJuanJiJinTypeEnum re : DanJuanJiJinTypeEnum.values()){
                redissonClient.getSet(RedisKey.dan_juan_ji_jin_type_enum.getValue()).add(re.getCode());
            }
        }
        log.info("执行初始化写入蛋卷基金至redis内存完毕");
    }

    @Override
    public void run(String... args) throws Exception {
        setDanJuanJiJinTypeInRedis();
    }
}
