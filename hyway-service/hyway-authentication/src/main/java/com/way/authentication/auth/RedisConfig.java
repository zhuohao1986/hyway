package com.way.authentication.auth;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig extends CachingConfigurerSupport{

	private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${spring.redis.jedis.database}")
	private int database;

	@Value("${spring.redis.jedis.host}")
	private String host;

	@Value("${spring.redis.jedis.password}")
	private String password;

	@Value("${spring.redis.jedis.port}")
	private int port;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.jedis.timeout}")
	private int timeout;
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
	    JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(maxActive);
	    poolConfig.setMaxIdle(maxIdle);
	    poolConfig.setMaxWaitMillis(maxWaitMillis);
	    poolConfig.setMinIdle(minIdle);
	    poolConfig.setTestOnBorrow(true);
	    poolConfig.setTestOnReturn(false);
	    poolConfig.setTestWhileIdle(true);
	    JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
	            .usePooling().poolConfig(poolConfig).and().readTimeout(Duration.ofMillis(100000)).build();

	    // 单点redis
	    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
	    // 哨兵redis
	    // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
	    // 集群redis
	    // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
	    // RedisNode node=new RedisNode(host, port);
	    //redisConfig.addClusterNode(node);
	    redisConfig.setHostName(host);
	    redisConfig.setPassword(RedisPassword.of(password));
	    redisConfig.setPort(port);
	    redisConfig.setDatabase(database);

	    return new JedisConnectionFactory(redisConfig,clientConfig);
	}
	
	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMinIdle(minIdle);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		logger.info("JedisPool注入成功！");
		logger.info("redis地址：" + host + ":" + port);
		return jedisPool;
	}
}
