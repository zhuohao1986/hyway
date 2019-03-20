package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.api.hystrix.SysDictApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 字典表 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.SYSTEM_SERVICE,fallbackFactory=SysDictApiHystrixFeignFallbackFactory.class)
public interface SysDictFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/sysDictPage",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysDictPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/sysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysDict(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/sysDictList", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectList(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/insertSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String insert(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/deleteSysDictById", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteById(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/updateSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDict(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dict/refreshSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshSysDict(String param);

}
