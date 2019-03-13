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
public interface GateWayRouteConfigFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/sysDictPage",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysDictPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/sysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysDict(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/sysDictList", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectList(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/insertSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String insert(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/deleteSysDictById", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteById(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/updateSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDict(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/refreshSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshSysDict(String param);

}
