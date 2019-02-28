package com.way.api.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.api.hystrix.SysDictApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@FeignClient(value=ServiceConstants.SYSTEM_SERVICE,fallbackFactory=SysDictApiHystrixFeignFallbackFactory.class)
public interface SysDictApi{
	
	@RequestMapping(method=RequestMethod.GET,value="/sysDictPage", produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysDictPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysDict();

}