package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysAuthApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
public class SysAuthController extends BaseController{

	@Autowired 
	private SysAuthApi sysAuthApi;
	
	/**
	 * 查询用户分页
	 * @return
	 */
	@RequestMapping(value="/authToken")
	public String authToken() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String authTokenStr = sysAuthApi.authToken(rw.toString());
			result = JSONObject.parseObject(authTokenStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	/**
	 * 查询用户
	 * @return
	 */
	@RequestMapping(value="/getAuthInfo")
	public String getAuthInfo() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String authInfo = sysAuthApi.getAuthInfo(rw.toString());
		result = JSONObject.parseObject(authInfo, Result.class);
		return result.toString();
	}
	/**
	 * setAuthState
	 * @return
	 */
	@RequestMapping(value="/setAuthState")
	public Result setAuthState() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String authState = sysAuthApi.setAuthState(rw.toString());
		result = JSONObject.parseObject(authState, Result.class);
		return result;
	}
}
