package com.way.gateway.route;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.way.common.cache.JedisClient;

import reactor.core.publisher.Mono;
/**
 * 使用Redis保存自定义路由配置（代替默认的InMemoryRouteDefinitionRepository）
 * <p/>
 * 存在问题：每次请求都会调用getRouteDefinitions，当网关较多时，会影响请求速度，考虑放到本地Map中，使用消息通知Map更新。
 *
 * 
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware{

	@Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
 
    private ApplicationEventPublisher publisher;
    
    public static final String GATEWAY_ROUTES = "geteway_routes";
    
    @Autowired
    private JedisClient jedisClient;
 
    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
 
 
    /**
     * 增加路由
     *
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
        return "success";
    }
 
 
    /**
     * 更新路由
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            notifyChanged();
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }
 
 
    }
 
    /**
     * 删除路由
     *
     */
    public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            notifyChanged();
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
 
    }
 
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    	RouteDefinition routeDefinition = new RouteDefinition();
    	PredicateDefinition predicateDefinition = new PredicateDefinition();
    	Map<String, String> predicateParams = new HashMap<>(8);
    	Map<String, String> filterParams = new HashMap<>(8);
    	FilterDefinition filterDefinition = new FilterDefinition();
    	URI uri = UriComponentsBuilder.fromUriString("lb://HELLO-SERVICE").build().toUri();

    	routeDefinition.setId("rateLimitTest");
    	// 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
    	predicateDefinition.setName("Path");
    	predicateParams.put("pattern", "/rate/**");
    	predicateDefinition.setArgs(predicateParams);

    	// 名称是固定的，spring gateway会根据名称找对应的FilterFactory
    	filterDefinition.setName("RequestRateLimiter");
    	// 每秒最大访问次数
    	filterParams.put("redis-rate-limiter.replenishRate", "2");
    	// 令牌桶最大容量
    	filterParams.put("redis-rate-limiter.burstCapacity", "3");
    	// 限流策略(#{@BeanName})
    	filterParams.put("key-resolver", "#{@hostAddressKeyResolver}");
    	// 自定义限流器(#{@BeanName})
    	//filterParams.put("rate-limiter", "#{@redisRateLimiter}");
    	filterDefinition.setArgs(filterParams);

    	routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
    	routeDefinition.setFilters(Arrays.asList(filterDefinition));
    	routeDefinition.setUri(uri);
    	routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
    	publisher.publishEvent(new RefreshRoutesEvent(this));
        this.publisher = applicationEventPublisher;
    }
   
    @PostConstruct
    public void main() {
        RouteDefinition definition = new RouteDefinition();
        definition.setId("id");
        URI uri = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8888/header").build().toUri();
       // URI uri = UriComponentsBuilder.fromHttpUrl("http://baidu.com").build().toUri();
        definition.setUri(uri);
 
        //定义第一个断言
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
 
        Map<String, String> predicateParams = new HashMap<>(8);
        predicateParams.put("pattern", "/jd");
        predicate.setArgs(predicateParams);
 
        //定义Filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("AddRequestHeader");
        Map<String, String> filterParams = new HashMap<>(8);
        //该_genkey_前缀是固定的，见org.springframework.cloud.gateway.support.NameUtils类
        filterParams.put("_genkey_0", "header");
        filterParams.put("_genkey_1", "addHeader");
        filter.setArgs(filterParams);
 
        FilterDefinition filter1 = new FilterDefinition();
        filter1.setName("AddRequestParameter");
        Map<String, String> filter1Params = new HashMap<>(8);
        filter1Params.put("_genkey_0", "param");
        filter1Params.put("_genkey_1", "addParam");
        filter1.setArgs(filter1Params);
 
        definition.setFilters(Arrays.asList(filter, filter1));
        definition.setPredicates(Arrays.asList(predicate));
 
        System.out.println("definition:" + JSON.toJSONString(definition));
        jedisClient.hset(GATEWAY_ROUTES,"key",JSON.toJSONString(definition));
    }

}
