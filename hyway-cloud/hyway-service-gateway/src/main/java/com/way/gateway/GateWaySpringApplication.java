package com.way.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication
public class GateWaySpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(GateWaySpringApplication.class, args);
	}
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
	    return builder.routes()
	            .route("WEB", r -> r.path("/web/{segment}")
	                    .filters(f -> f.setPath("/{segment}"))
	                    .uri("http://127.0.0.1:8661"))
	            .build();
	}

}
