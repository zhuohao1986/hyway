package com.way.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin.server.internal.EnableZipkinServer;


@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class ZipkinSpringAppliaction {
	
	public static void main(String[] args) {
			SpringApplication.run(ZipkinSpringAppliaction.class, args);
	}
}
