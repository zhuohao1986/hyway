package com.way.gateway.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.exception.GateWayException;
import com.way.common.route.GatewayRouteDefinition;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.LogUtils;
import com.way.gateway.cache.JedisClient;
import com.way.gateway.comm.RouteUils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * 使用Redis保存自定义路由配置（代替默认的InMemoryRouteDefinitionRepository）
 * <p/>
 * 存在问题：每次请求都会调用getRouteDefinitions，当网关较多时，会影响请求速度，考虑放到本地Map中，使用消息通知Map更新。
 *
 * 
 */
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService,ApplicationEventPublisherAware{

	private static final Logger Logger=LoggerFactory.getLogger(DynamicRouteServiceImpl.class);
	
	@Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
 
    private ApplicationEventPublisher publisher;
    
    @Autowired
	private RouteDefinitionLocator routeDefinitionLocator;
    
    @Autowired
    private JedisClient jedisClient;
    
    Result result;
 
    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
 
    /**
     * 增加路由
     *
     */
    @Override
    public String add(String param) throws GateWayException{
    	RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
    	GatewayRouteDefinition gwdefinition=JSONObject.parseObject(rw.getValue(), GatewayRouteDefinition.class);
    	RouteDefinition definition=RouteUils.assembleRouteDefinition(gwdefinition);
    	result=new Result(CodeConstants.RESULT_SUCCESS);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
        result.setMessage("add route  success " + definition.getId());
        jedisClient.hset(ConfigKeyConstant.GATEWAY_ROUTES,definition.getId(),JSONObject.toJSONString(definition));
        return result.toJSONString();
    }
 
 
    /**
     * 更新路由
     */
    @Override
    public String update(String param) throws GateWayException{
    	RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
    	GatewayRouteDefinition gwdefinition=JSONObject.parseObject(rw.getValue(), GatewayRouteDefinition.class);
    	RouteDefinition definition=RouteUils.assembleRouteDefinition(gwdefinition);
    	try {
	        result=new Result(CodeConstants.RESULT_SUCCESS);
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            notifyChanged();
            result.setMessage("update route  success " + definition.getId());
        } catch (Exception e) {
        	result.setMessage("update fail,not find route  routeId: " + definition.getId());
        	return result.toJSONString();
        }
    	return result.toJSONString();
    }
 
    /**
     	* 删除路由
     *
     */
    @Override
    public String delete(String param) throws GateWayException{
    	RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
    	JSONObject obj=JSONObject.parseObject(rw.getValue());
    	String routeId=obj.getString("id");
    	result=new Result(CodeConstants.RESULT_SUCCESS);
        try {
            this.routeDefinitionWriter.delete(Mono.just(routeId));
            notifyChanged();
            if (jedisClient.hexists(ConfigKeyConstant.GATEWAY_ROUTES,routeId)) {
            	jedisClient.hdel(ConfigKeyConstant.GATEWAY_ROUTES,routeId);
            }
            result.setMessage("delete success" + routeId);
            result.setValue(true);
            return result.toJSONString();
        } catch (Exception e) {
        	result.setValue(true);
            result.setMessage("update fail,not find route  routeId: " + routeId);
        	return result.toJSONString();
        }
 
    }
    @Override
    public String getRouteDefinitions() {
    	result=new Result(CodeConstants.RESULT_SUCCESS);
    	List<RouteDefinition> routeDefinitions=new ArrayList<>();
		try {
			if(jedisClient.exists(ConfigKeyConstant.GATEWAY_ROUTES)) {
				String routeDefinitionStr= jedisClient.get(ConfigKeyConstant.GATEWAY_ROUTES);
				routeDefinitions=JSONObject.parseArray(routeDefinitionStr, RouteDefinition.class);
				result.setValue(routeDefinitions);
			}else {
				Flux<RouteDefinition> routeDefinitionFlux = routeDefinitionLocator.getRouteDefinitions();
				result.setValue(JSONObject.toJSONString(routeDefinitionFlux));
			}
			result.setMessage("get routeDefinitions success");
		 } catch (Exception e) {
			 result.setMessage("get routeDefinitions fail:" + e.getMessage());
	         result.setValue(true);
        }
		return result.toJSONString();
	}
 
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    	/*RouteDefinition routeDefinition = new RouteDefinition();
    	PredicateDefinition predicateDefinition = new PredicateDefinition();
    	Map<String, String> predicateParams = new HashMap<>(8);
    	Map<String, String> filterParams = new HashMap<>(8);
    	FilterDefinition filterDefinition = new FilterDefinition();
    	URI uri = UriComponentsBuilder.fromUriString("lb://SYSTEM-SERVICE").build().toUri();

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
    	publisher.publishEvent(new RefreshRoutesEvent(this));*/
        this.publisher = applicationEventPublisher;
    }

	@Override
	public String refresh() {
		result=new Result(CodeConstants.RESULT_SUCCESS);
    	List<RouteDefinition> routeDefinitions=new ArrayList<>();
		try {
			if(jedisClient.exists(ConfigKeyConstant.GATEWAY_ROUTES)) {
				String resultRoutes= jedisClient.get(ConfigKeyConstant.GATEWAY_ROUTES);
				routeDefinitions=JSONObject.parseArray(resultRoutes, RouteDefinition.class);
				LogUtils.info(Logger, "路由信息"+routeDefinitions);
				for (RouteDefinition routeDefinition : routeDefinitions) {
					 this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
					 routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
				}
				notifyChanged();
			}else {
				result.setMessage("get routeDefinitions fail");
			}
		 } catch (Exception e) {
			 result.setMessage("get routeDefinitions fail:" + e.getMessage());
	         result.setValue(true);
        }
		return result.toJSONString();
	}
   
    /*@PostConstruct
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
       // jedisClient.hset(ConfigConstant.GATEWAY_ROUTES,"key",JSON.toJSONString(definition));
    }*/
}
