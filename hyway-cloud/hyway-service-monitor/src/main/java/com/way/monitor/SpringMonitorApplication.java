package com.way.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@EnableEurekaClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableTurbine
@EnableFeignClients
@SpringBootApplication
public class SpringMonitorApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringMonitorApplication.class, args);
	}
}
