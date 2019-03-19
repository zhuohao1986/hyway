package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.api.hystrix.SysDictApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 系统路由 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.SYSTEM_SERVICE,fallbackFactory=SysDictApiHystrixFeignFallbackFactory.class)
public interface SysRouteConfigFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/route/add",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addRoute(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/delete", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/update", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/route", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRoute(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/routes", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String routes(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/refresh", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshRoute();



}
