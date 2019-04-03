package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysResourcesApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
@RequestMapping(value="/res",produces=MediaType.APPLICATION_JSON_VALUE)
public class SysResourcesController extends BaseController{

	@Autowired 
	private SysResourcesApi sysResourcesApi;
	
	@RequestMapping(value="/selectById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectById() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysresourcesPageStr = sysResourcesApi.sysResources(rw.toString());
			result = JSONObject.parseObject(SysresourcesPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/resourcesPage",produces=MediaType.APPLICATION_JSON_VALUE)
	public String resourcesPage() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysResourcesPageStr = sysResourcesApi.selectSysResourcesPage(rw.toString());
			result = JSONObject.parseObject(sysResourcesPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysResources",produces=MediaType.APPLICATION_JSON_VALUE)
	public String sysResources() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysResourcesStr = sysResourcesApi.sysResources(rw.toString());
		result = JSONObject.parseObject(sysResourcesStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/selectListTree",produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectListTree() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysResourcesListStr = sysResourcesApi.selectListTree(rw.toString());
		result = JSONObject.parseObject(sysResourcesListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysResources",produces=MediaType.APPLICATION_JSON_VALUE)
	public String insertSysResources() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysResourcesStr = sysResourcesApi.insertSysResources(rw.toString());
		result = JSONObject.parseObject(insertSysResourcesStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysResourcesById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysResourcesById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysResourcesByIdStr = sysResourcesApi.deleteSysResourcesById(rw.toString());
		result = JSONObject.parseObject(deleteSysResourcesByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updateSysResources",produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateSysResources() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysResourcesStr = sysResourcesApi.updateSysResources(rw.toString());
		result = JSONObject.parseObject(updateSysResourcesStr, Result.class);
		return result.toString();
	}
}
