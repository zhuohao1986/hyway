package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysDeptApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
@RequestMapping(value="/dept",produces=MediaType.APPLICATION_JSON_VALUE)
public class SysDeptController extends BaseController{

	@Autowired 
	private SysDeptApi sysDeptApi;
	
	@RequestMapping(value="/selectById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectById() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.sysDept(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/deptPage",produces=MediaType.APPLICATION_JSON_VALUE)
	public String dictPage() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.selectSysDeptPage(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysDept",produces=MediaType.APPLICATION_JSON_VALUE)
	public String SysDept() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysDeptStr = sysDeptApi.sysDept(rw.toString());
		result = JSONObject.parseObject(SysDeptStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/selectListTree",produces=MediaType.APPLICATION_JSON_VALUE)
	public String SysDeptList() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String SysDeptListStr = sysDeptApi.selectListTree(rw.toString());
		result = JSONObject.parseObject(SysDeptListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysDept",produces=MediaType.APPLICATION_JSON_VALUE)
	public String insertSysDept() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysDeptStr = sysDeptApi.insertSysDept(rw.toString());
		result = JSONObject.parseObject(insertSysDeptStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysDeptById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysDeptById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysDeptByIdStr = sysDeptApi.deleteSysDeptById(rw.toString());
		result = JSONObject.parseObject(deleteSysDeptByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updateSysDept",produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDept() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysDeptStr = sysDeptApi.updateSysDept(rw.toString());
		result = JSONObject.parseObject(updateSysDeptStr, Result.class);
		return result.toString();
	}
}
