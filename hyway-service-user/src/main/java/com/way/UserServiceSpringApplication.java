package com.way;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.constant.KeyConstant;
import com.way.common.jcache.RedisCache;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@RestController
@ComponentScan(basePackages = {"com.way.service"})
@EnableFeignClients
public class UserServiceSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceSpringApplication.class, args);
	}
	
	//@Autowired
	//private JedisClientSingle jedisClientSingle;
	@Autowired
	private RedisCache redisCache;
	
	@GetMapping("/redisConfig")
	public String redisConfig(String redisConfigKey,String redisConfigval) {
		
		redisCache.put(KeyConstant.REDIS_USER_SESSION_KEY+":"+redisConfigKey, redisConfigval, 0, TimeUnit.MINUTES);
		
		return "";
	}
}
