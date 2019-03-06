package com.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import tk.mybatis.spring.annotation.MapperScan;


@EnableDiscoveryClient
@MapperScan("com.way.dao")
@SpringBootApplication
public class ImServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImServerApplication.class, args);
	}

}

