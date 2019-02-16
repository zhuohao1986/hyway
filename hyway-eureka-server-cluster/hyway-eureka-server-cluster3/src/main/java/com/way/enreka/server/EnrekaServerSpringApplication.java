package com.way.enreka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EnrekaServerSpringApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(EnrekaServerSpringApplication.class, args);
		}

}
