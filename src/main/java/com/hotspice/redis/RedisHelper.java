package com.hotspice.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class RedisHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisHelper.class);
	private JedisPool jedisPool = null;

	@Autowired
	private Environment env;
	/**
   * This must be called in starting of the server
   */
	@PostConstruct
     void initiatePool(){
		logger.info("Redis poll init started..");
        String host = env.getProperty("redis.server.host");
		int port = Integer.parseInt(env.getProperty("redis.server.port"));
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(50);
		poolConfig.setMaxIdle(20);
		poolConfig.setMinIdle(10);
		poolConfig.setTestOnBorrow(true);
		jedisPool = new JedisPool(poolConfig,host,port);
		logger.info("Redis poll initialized started..");
	}

	public String setPair(String key, String value) {
		try{
			return jedisPool.getResource().set(key, value);
		}catch(Exception e){
			logger.error("Exception occured for redis " + e.getMessage());
		}
		return null;
	}

	public String getValueByKey(String key) {
		try{
			return jedisPool.getResource().get(key);
		}catch(Exception e){
			logger.error("Exception occured for redis " + e.getMessage());
		}
		return null;
	}

	public Double incrementScore(String key, double score, String member) {
		return jedisPool.getResource().zincrby(key, score, member);
	}

	public String getMaxScoreElement(String key) {
		Set<String> result = jedisPool.getResource().zrevrangeByScore(key, Double.MAX_VALUE, 0, 0, 1);
		if(result!=null && !result.isEmpty()) {
			return result.iterator().next();
		}
		return null;
	}
}
