package com.account.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/5 23:55
 */
public class Test {
	public static void main(String[] args) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(10);
		jedisPoolConfig.setMaxIdle(5);
		jedisPoolConfig.setMinIdle(5);

//		Set<String> sentinels = new HashSet<String>(Arrays.asList("192.168.10.103:26379"));


		JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(),"192.168.10.102",6379,1000,"!redis@ljh");
		Jedis jedis = jedisPool.getResource();
//		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig, "!redis@ljh");
//		Jedis jedis = pool.getResource();

		jedis.set("SentinelKey","test");
		System.out.println(jedis.get("SentinelKey"));
	}
}
