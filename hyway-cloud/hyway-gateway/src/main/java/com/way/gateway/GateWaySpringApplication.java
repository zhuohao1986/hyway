package com.way.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.way.gateway.filter.JWTAuthFilter;
import com.way.gateway.filter.RequestGlobalFilter;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GateWaySpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(GateWaySpringApplication.class, args);
	}

	@Bean
	public ThrottleGatewayFilter throttleGatewayFilter() {
		return new ThrottleGatewayFilter();
	}
	
	@Bean
	public RequestGlobalFilter requestGlobalFilter() {
		return new RequestGlobalFilter();
	}
	
	@Bean
	public JWTAuthFilter jWTAuthFilter() {
		return new JWTAuthFilter();
	}
	

}
