package com.way.common.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Override
	public KeyGenerator keyGenerator() {
		// 设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
		// 使用：进行分割，可以很多显示出层级关系
		// 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下、
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(":");
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(":" + String.valueOf(obj));
			}
			String rsToUse = String.valueOf(sb);
			logger.info("自动生成Redis Key -> [{}]", rsToUse);
			return rsToUse;
		};
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		// 初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有配置
		logger.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory());
		return builder.build();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) { // 设置序列化
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om); // 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer); // key序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // value序列化
		redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hashvalue序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	@Bean
	public CacheErrorHandler errorHandler() {
		// 异常处理，当Redis发生异常时，打印日志，但是程序正常走
		logger.info("初始化 -> [{}]", "Redis CacheErrorHandler");
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

			@Override
			public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
				logger.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
			}

			@Override
			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
				logger.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
			}

			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
				logger.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
			}

			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				logger.error("Redis occur handleCacheClearError：", e);
			}
		};
		return cacheErrorHandler;
	}
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
	    JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(8);
	    poolConfig.setMaxIdle(8);
	    poolConfig.setMaxWaitMillis(1);
	    poolConfig.setMinIdle(0);
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
	    redisConfig.setHostName("62.234.110.157");
	    redisConfig.setPassword(RedisPassword.of("hyway@123#"));
	    redisConfig.setPort(6379);
	    redisConfig.setDatabase(0);

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
