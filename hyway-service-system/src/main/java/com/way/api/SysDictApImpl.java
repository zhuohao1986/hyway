package com.way.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysDictApi;
import com.way.common.constant.CodeConstants;
import com.way.common.pojos.system.SysDict;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.system.service.SysDictService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@RestController
@RequestMapping(value = "/v1/cache/limitlist")
public class SysDictApImpl implements SysDictApi{
	
	@Autowired
	private SysDictService sysDictService;
	
	Result result;

	@Override
	public String selectSysDictPage(String param) {
		result=new Result(CodeConstants.RESULT_FAIL);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Map<String, Object> paramMap=JSONObject.parseObject(obj.toString(),  new TypeReference<HashMap<String,Object>>() {});
		PageInfo<SysDict> sysDictpage = sysDictService.selectPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysDictpage);
		return result.toJSONString();
	}

	@Override
	public String sysDict() {
		result=new Result(CodeConstants.RESULT_FAIL);
		SysDict sysDict = sysDictService.selectById(1);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(sysDict));
		return result.toJSONString();
	}
}
