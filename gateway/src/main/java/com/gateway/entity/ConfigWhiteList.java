package com.gateway.entity;

import com.adminUtil.redis.RedisKey;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @description
 * 启动的时候加载 写入 白名单
 * @author luojinhua
 * @date 2021/5/26 18:13
 * @param
 * @return
 */
@Log4j
@Component
public class ConfigWhiteList implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConfigWhiteList.class);

    @Autowired
    private RedissonClient redissonClient;

    public void setWhiteListInRedis(){
        log.info("正在执行初始化写入白名单至redis内存");
        if (WhiteList.values().length > 0){
            for (WhiteList re : WhiteList.values()){
                redissonClient.getSet(RedisKey.set_white_list.getValue()).add(re.getValue());
            }
        }
        log.info("执行初始化写入白名单至redis内存完毕");
    }

    @Override
    public void run(String... args) throws Exception {
        setWhiteListInRedis();
    }
}
