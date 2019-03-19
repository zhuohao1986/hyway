package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.way.api.hystrix.GateWayRouteApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 动态系统路由 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.GATEWAY_SERVICE,fallbackFactory=GateWayRouteApiHystrixFeignFallbackFactory.class)
public interface GateWayRouteFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/route/add", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String addRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/delete", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/update", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/routes/refresh", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshRoute();
}
