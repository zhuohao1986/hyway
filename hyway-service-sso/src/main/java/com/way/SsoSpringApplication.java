package com.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.context.BaseController;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@RestController
@MapperScan(basePackages = "com.way.dao")
@ComponentScan(basePackages = { "com.way"})
@EnableFeignClients(basePackages = "com.way.api.sso")
public class SsoSpringApplication extends BaseController {

	public static void main(String[] args) {
		SpringApplication.run(SsoSpringApplication.class, args);
	}
}
