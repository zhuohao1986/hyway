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
			String sysDictPageStr = sysUserApi.selectSysUserPage(rw.toString());
			result = JSONObject.parseObject(sysDictPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysUser")
	public String sysUser() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysDictStr = sysUserApi.sysUser(rw.toString());
		result = JSONObject.parseObject(sysDictStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/sysUserList")
	public String sysUserList() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysDictListStr = sysUserApi.sysUserList(rw.toString());
		result = JSONObject.parseObject(sysDictListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysUser")
	public String insertSysUser() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysDictStr = sysUserApi.userInsert(rw.toString());
		result = JSONObject.parseObject(insertSysDictStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysDictById")
	public String deleteSysDictById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysDictByIdStr = sysUserApi.userDelete(rw.toString());
		result = JSONObject.parseObject(deleteSysDictByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updateSysDict")
	public String updateSysDict() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysDictStr = sysUserApi.userInsert(rw.toString());
		result = JSONObject.parseObject(updateSysDictStr, Result.class);
		return result.toString();
	}
}
