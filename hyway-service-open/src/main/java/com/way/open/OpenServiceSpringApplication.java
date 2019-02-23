package com.way.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@ComponentScan(basePackages = {"com.way"})
@SpringBootApplication
public class OpenServiceSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OpenServiceSpringApplication.class, args);
	}
}
