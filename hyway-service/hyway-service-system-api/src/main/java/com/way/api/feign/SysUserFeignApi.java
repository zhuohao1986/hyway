package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.common.constant.ServiceConstants;
import com.way.common.stdo.Result;

/**
 * 后台用户 服务类Feign
 * @author 
 *
 */
@FeignClient(name = ServiceConstants.SYSTEM_SERVICE/* ,fallbackFactory=SysUserApiHystrixFeignFallbackFactory.class */)
public interface SysUserFeignApi {
	
	@RequestMapping(method=RequestMethod.POST,value="/user/login", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Result login(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/logout", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String logout(@RequestParam String param);

	@RequestMapping(method=RequestMethod.GET,value="/user/login/openId", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Result loginOpenId(@RequestParam String param);

	@RequestMapping(method=RequestMethod.GET,value="/user/autoRegUser", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Result userAutoReg(@RequestParam String param);
}
