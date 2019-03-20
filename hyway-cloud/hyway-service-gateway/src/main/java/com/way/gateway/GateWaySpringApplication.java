package com.way.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

import com.way.gateway.filter.RequestTimeGatewayFilterFactory;

@EnableDiscoveryClient
@EnableWebFlux
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class GateWaySpringApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(GateWaySpringApplication.class, args);
	}
	@Bean
	public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
		return new RequestTimeGatewayFilterFactory();
	}

	/*@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}*/
	
	/*@Bean
	public CustomeGatewayFilter customeGatewayFilter() {
		return new CustomeGatewayFilter();
	}*/
}
