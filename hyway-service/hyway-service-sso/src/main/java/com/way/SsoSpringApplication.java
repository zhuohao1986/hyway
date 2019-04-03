package com.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.context.BaseController;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@SpringBootApplication
@RestController
@MapperScan(basePackages = "com.way.dao")
public class SsoSpringApplication extends BaseController {

	public static void main(String[] args) {
		SpringApplication.run(SsoSpringApplication.class, args);
	}
}
