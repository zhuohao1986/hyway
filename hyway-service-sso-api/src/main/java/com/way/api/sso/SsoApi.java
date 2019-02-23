package com.way.api.sso;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sso",url = "http://127.0.0.1")
public interface SsoApi {
	/**
	 * 用户登录
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping( value = "/userLogin",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public String userLogin(@RequestParam("userName") String userName, @RequestParam("userPwd") String userPwd/* ,String param */) throws Exception;
	
	@RequestMapping( value = "/getUser",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUserByToken(@RequestBody(required=false) String param);

    // 重新设置用户信息缓存有效时间
    @RequestMapping( value = "/resetUserCache",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public String resetUserCache(@RequestBody(required=false) String param);
	/*
	 * //退出 public String userLogout(String param);
	 * 
	 * public String checkLogin(String param);
	 * 
	 * public String getMessage(String param);
	 * 
	 * public String checkCaptcha();
	 */

}
