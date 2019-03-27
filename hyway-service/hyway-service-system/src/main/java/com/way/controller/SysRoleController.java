package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysRoleApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
@RequestMapping(value="/role",produces=MediaType.APPLICATION_JSON_VALUE)
public class SysRoleController extends BaseController{

	@Autowired 
	private SysRoleApi sysRoleApi;
	
	@RequestMapping(value="/selectById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectById() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysRolePageStr = sysRoleApi.sysRole(rw.toString());
			result = JSONObject.parseObject(SysRolePageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/rolePage",produces=MediaType.APPLICATION_JSON_VALUE)
	public String dictPage() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysRolePageStr = sysRoleApi.selectSysRolePage(rw.toString());
			result = JSONObject.parseObject(SysRolePageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysRole",produces=MediaType.APPLICATION_JSON_VALUE)
	public String SysRole() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysRoleStr = sysRoleApi.sysRole(rw.toString());
		result = JSONObject.parseObject(SysRoleStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/selectListTree",produces=MediaType.APPLICATION_JSON_VALUE)
	public String SysRoleList() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysRoleListStr = sysRoleApi.selectList(rw.toString());
		result = JSONObject.parseObject(SysRoleListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysRole",produces=MediaType.APPLICATION_JSON_VALUE)
	public String insertSysRole() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysRoleStr = sysRoleApi.insertSysRole(rw.toString());
		result = JSONObject.parseObject(insertSysRoleStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysRoleById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysRoleById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysRoleByIdStr = sysRoleApi.deleteSysRoleById(rw.toString());
		result = JSONObject.parseObject(deleteSysRoleByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updateSysRole",produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateSysRole() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysRoleStr = sysRoleApi.updateSysRole(rw.toString());
		result = JSONObject.parseObject(updateSysRoleStr, Result.class);
		return result.toString();
	}
}
