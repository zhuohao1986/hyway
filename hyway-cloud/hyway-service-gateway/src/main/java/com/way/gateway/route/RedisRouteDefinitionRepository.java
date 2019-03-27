/*package com.way.gateway.route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.way.gateway.cache.JedisClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {
 
    public static final String GATEWAY_ROUTES = "geteway_routes";
 
    @Autowired
    private JedisClient jedisClient;
 
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
    	List<RouteDefinition> routeDefinitions=new ArrayList<>();
		try {
			String routeDefinitionStr= jedisClient.get(GATEWAY_ROUTES);
			routeDefinitions=JSONObject.parseArray(routeDefinitionStr, RouteDefinition.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Flux.fromIterable(routeDefinitions);
    }
 
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route
                .flatMap(routeDefinition -> {
                	jedisClient.hset(GATEWAY_ROUTES,routeDefinition.getId(),JSONObject.toJSONString(routeDefinition));
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (jedisClient.hexists(GATEWAY_ROUTES, id)) {
            	jedisClient.hdel(GATEWAY_ROUTES, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }
 
}*/