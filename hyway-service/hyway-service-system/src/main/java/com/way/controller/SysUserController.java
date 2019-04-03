package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysUserApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
@RequestMapping(value="/user",produces=MediaType.APPLICATION_JSON_VALUE)
public class SysUserController extends BaseController{

	@Autowired 
	private SysUserApi sysUserApi;
	 
	@RequestMapping(value="/sysUserPage")
	public String sysUserPage() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysUsertPageStr = sysUserApi.selectSysUserPage(rw.toString());
			result = JSONObject.parseObject(SysUsertPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysUser")
	public String sysUser() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysUsertStr = sysUserApi.sysUser(rw.toString());
		result = JSONObject.parseObject(SysUsertStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/sysUserList")
	public String sysUserList() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysUsertListStr = sysUserApi.sysUserList(rw.toString());
		result = JSONObject.parseObject(SysUsertListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysUser")
	public String insertSysUser() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysUsertStr = sysUserApi.userInsert(rw.toString());
		result = JSONObject.parseObject(insertSysUsertStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysUserById")
	public String deleteSysUsertById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysUsertByIdStr = sysUserApi.userDelete(rw.toString());
		result = JSONObject.parseObject(deleteSysUsertByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updatesysUser")
	public String updateSysUsert() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysUsertStr = sysUserApi.userUpdate(rw.toString());
		result = JSONObject.parseObject(updateSysUsertStr, Result.class);
		return result.toString();
	}
}
