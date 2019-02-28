package com.way.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 三方服务
 * @author way
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableEurekaClient
public class OpenServiceSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OpenServiceSpringApplication.class, args);
	}
}
