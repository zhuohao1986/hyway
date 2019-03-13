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
@FeignClient(name=ServiceConstants.GATEWAY_SERVICE,fallbackFactory=SysDictApiHystrixFeignFallbackFactory.class)
public interface SysRouteConfigFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/route/addRoute",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addRoute(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/routes/delete", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String delete(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/update", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String update(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/route/routes", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String routes();


}
