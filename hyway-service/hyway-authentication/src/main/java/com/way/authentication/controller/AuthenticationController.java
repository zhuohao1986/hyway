package com.way.authentication.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.authentication.api.AuthenticationApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
@RequestMapping(value="/auth",produces=MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController extends BaseController{

	@Autowired 
	private AuthenticationApi authenticationApi;
	
	@RequestMapping(value="/login",produces=MediaType.APPLICATION_JSON_VALUE)
	public String login() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.login(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/logout",produces=MediaType.APPLICATION_JSON_VALUE)
	public String logout() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.logout(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/refreshToken",produces=MediaType.APPLICATION_JSON_VALUE)
	public String user() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.refreshToken(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	  @GetMapping("/") 
	  public String index() { 
	    return "auth-service: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
	  } 
	
}
