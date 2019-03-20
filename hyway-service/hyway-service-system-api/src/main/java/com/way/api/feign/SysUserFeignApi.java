package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.way.api.hystrix.SysUserApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 后台用户 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.SYSTEM_SERVICE,fallbackFactory=SysUserApiHystrixFeignFallbackFactory.class)
public interface SysUserFeignApi {
	
	@RequestMapping(method=RequestMethod.GET,value="/user/sysUserPage",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectSysUserPage(@RequestParam String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/user", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String sysUser(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/userList", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectUserList(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/insertUser", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String insertUser(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/deleteSysUserById", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysUserById(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/updateSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDict(String param);
	
	@RequestMapping(method=RequestMethod.GET,value="/user/refreshSysDict", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshSysDict(String param);

}
