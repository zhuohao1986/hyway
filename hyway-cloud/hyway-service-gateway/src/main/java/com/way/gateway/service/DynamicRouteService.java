package com.way.gateway.service;

import java.util.List;

import org.springframework.cloud.gateway.route.RouteDefinition;

import reactor.core.publisher.Flux;

public interface DynamicRouteService {

	public String add(String param);

	String update(String param);
	
	String delete(String param);

	List<RouteDefinition> getRouteDefinitions();

}