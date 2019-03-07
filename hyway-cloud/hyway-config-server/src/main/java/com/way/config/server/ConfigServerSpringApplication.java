package com.way.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
/**
 * springclound 配置服务中心
 * @author way
 *
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerSpringApplication.class, args);
	}
}
