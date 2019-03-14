package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.way.api.hystrix.SysRouteGateWayApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 动态系统路由 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.GATEWAY_SERVICE,fallbackFactory=SysRouteGateWayApiHystrixFeignFallbackFactory.class)
public interface SysRouteGateWayFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/insertSysRouteConfig", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String insertSysRouteConfig(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/deleteSysRouteConfig", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysRouteConfigById(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/updateSysRouteConfig", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSysRouteConfig(String param);
	

}
