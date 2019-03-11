package com.way.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysDictApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
@RestController
public class SysDictController extends BaseController{

	@Autowired 
	private SysDictApi sysDictApi;
	 
	@RequestMapping(value="/dictPage",produces=MediaType.APPLICATION_JSON_VALUE)
	public String dictPage() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysDictPageStr = sysDictApi.selectSysDictPage(rw.toString());
			result = JSONObject.parseObject(sysDictPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/sysDict",produces=MediaType.APPLICATION_JSON_VALUE)
	public String sysDict() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysDictStr = sysDictApi.sysDict(rw.toString());
		result = JSONObject.parseObject(sysDictStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/sysDictList",produces=MediaType.APPLICATION_JSON_VALUE)
	public String sysDictList() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String sysDictListStr = sysDictApi.selectSysDictPage(rw.toString());
		result = JSONObject.parseObject(sysDictListStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/insertSysDict",produces=MediaType.APPLICATION_JSON_VALUE)
	public String insertSysDict() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String insertSysDictStr = sysDictApi.sysDict(rw.toString());
		result = JSONObject.parseObject(insertSysDictStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/deleteSysDictById",produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteSysDictById() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String deleteSysDictByIdStr = sysDictApi.selectSysDictPage(rw.toString());
		result = JSONObject.parseObject(deleteSysDictByIdStr, Result.class);
		return result.toString();
	}
	
	@RequestMapping(value="/updateSysDict",produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateSysDict() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String updateSysDictStr = sysDictApi.sysDict(rw.toString());
		result = JSONObject.parseObject(updateSysDictStr, Result.class);
		return result.toString();
	}
	@RequestMapping(value="/refresh",produces=MediaType.APPLICATION_JSON_VALUE)
	public String refresh() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
		String refreshStr = sysDictApi.refresh(rw.toString());
		result = JSONObject.parseObject(refreshStr, Result.class);
		return result.toString();
	}
}
