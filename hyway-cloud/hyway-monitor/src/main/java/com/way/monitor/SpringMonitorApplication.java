package com.way.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class SpringMonitorApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringMonitorApplication.class, args);
	}
}
