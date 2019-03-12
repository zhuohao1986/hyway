package com.way.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.way.gateway.filter.RequestTimeGatewayFilterFactory;
import com.way.gateway.filter.TokenFilter;

@Configuration
@EnableDiscoveryClient
@SpringBootApplication
public class GateWaySpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(GateWaySpringApplication.class, args);
	}
	@Bean
	public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
		return new RequestTimeGatewayFilterFactory();
	}

	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}
}
