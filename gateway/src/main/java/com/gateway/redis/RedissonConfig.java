package com.gateway.redis;

import com.adminUtil.redis.LjrwJsonJacksonCodec;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/3 4:47
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    /**
     * 主从模式
     * @return
     */
    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
        masterSlaveServersConfig.setDnsMonitoringInterval(-1).setPingConnectionInterval(1000).
                setDatabase(redisConfigProperties.getDatabase()).setPassword(redisConfigProperties.getPassword()).
                setMasterAddress(redisConfigProperties.getMasterAddress()).
                setSlaveAddresses(redisConfigProperties.getSlaveAddress());

        Codec codec = new LjrwJsonJacksonCodec();
        config.setCodec(codec);
        return Redisson.create(config);
    }

//    /**
//     * 哨兵模式
//     * @return
//     */
//    @Bean
//    public RedissonClient redisson() {
//        Config config = new Config();
//        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("rediss://192.168.10.102:26379","rediss://192.168.10.103:26379","rediss://192.168.10.104:26379").setPingConnectionInterval(1000).setDatabase(1).setPassword("!redis@ljh");
//        Codec codec = new LjrwJsonJacksonCodec();
//        config.setCodec(codec);
//        return Redisson.create(config);
//    }

    //单节点
//    @Bean
//    public RedissonClient redisson() {
//        Config config = new Config();
//        String addr = "redis://" + redisConfigProperties.getHost()+":"+redisConfigProperties.getPort();
//        SingleServerConfig singleConfig = config.useSingleServer().setAddress(addr).setDatabase(redisConfigProperties.getDatabase()).
//                //心跳检测，定时与redis连接，可以防止一段时间过后，与redis的连接断开
//                        setPingConnectionInterval(1000);
//        singleConfig.setPassword(redisConfigProperties.getPassword());
//        Codec codec = new LjrwJsonJacksonCodec();
//        config.setCodec(codec);
//        return Redisson.create(config);
//    }


}
