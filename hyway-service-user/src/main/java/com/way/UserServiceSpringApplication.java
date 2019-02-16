package com.way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.cache.JedisClientSingle;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@RestController
@MapperScan(basePackages = "com.way.dao")
@ComponentScan(basePackages = {"com.way.service"})
@EnableFeignClients
public class UserServiceSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceSpringApplication.class, args);
	}
	
	@Autowired
	private JedisClientSingle jedisClientSingle;
	@GetMapping("/redisConfig")
	public String redisConfig(String redisConfigKey,String redisConfigval) {
		
		return jedisClientSingle.set(redisConfigKey, redisConfigval);
	}
}
