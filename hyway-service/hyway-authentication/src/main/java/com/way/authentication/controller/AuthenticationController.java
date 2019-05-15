package com.way.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired 
	private AuthenticationApi authenticationApi;
	
	@RequestMapping(value="/login")
	public String login() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.login(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			logger.error(request.getRequestURI(), e);
		}
		return result.toString();
	}
	@RequestMapping(value="/login/openid")
	public String loginOpenid() {
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
	@RequestMapping(value="/logout")
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
	@RequestMapping(value="/user")
	public String user() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.getInfo(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/refreshToken")
	public String refreshToken() {
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
	
}
