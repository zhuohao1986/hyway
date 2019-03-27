package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.api.hystrix.SysDeptApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 部门服务类 Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.SYSTEM_SERVICE,fallbackFactory=SysDeptApiHystrixFeignFallbackFactory.class)
public interface SysDeptFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/sysDeptPage",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysDeptPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/sysDept", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysDept(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/sysDeptList", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectList(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/insertSysDept", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String insert(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/deleteSysDeptById", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteById(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/dept/updateSysDept", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDept(String param);

	@RequestMapping(method=RequestMethod.GET,value="/dept/selectListTree", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectListTree(String string);

}
