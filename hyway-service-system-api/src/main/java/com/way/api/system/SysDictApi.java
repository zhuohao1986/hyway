package com.way.api.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@FeignClient(value="sys-dict-service",path = "/dict",url="127.0.0.1")
//@FeignClient(name="dict",fallbackFactory=SysDictApiHystrixFeignFallbackFactory.class)
public interface SysDictApi{
	
	@RequestMapping(method=RequestMethod.GET,value="/sysDictPage", produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysDictPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysDict();

}
