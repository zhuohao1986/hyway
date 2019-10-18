package com.way.api.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.common.constant.ServiceConstants;

/**
 * 后台用户 服务类Feign
 * @author 
 *
 */
@FeignClient(name = ServiceConstants.SYSTEM_SERVICE/* ,fallbackFactory=SysUserApiHystrixFeignFallbackFactory.class */)
public interface SysAuthFeignApi {
	
	@RequestMapping(method=RequestMethod.POST,value="/authToken", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String authToken(@RequestParam Map<String,Object> paramMap);
	
	@RequestMapping(method=RequestMethod.POST,value="/getAuthInfo", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAuthInfo(@RequestParam Map<String,Object> paramMap);

	@RequestMapping(method=RequestMethod.POST,value="/setAuthState", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String setAuthState(@RequestParam Map<String,Object> paramMap);
}
