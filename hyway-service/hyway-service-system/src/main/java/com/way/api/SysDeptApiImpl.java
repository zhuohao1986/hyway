package com.way.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysDeptApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysDept;
import com.way.common.pojos.system.dto.DeptTree;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.service.SysDeptService;

/**
 * <p>
 * 部门表 服务类
 * </p>
 * 
 */
@Service
public class SysDeptApiImpl implements SysDeptApi {

	@Autowired
	private SysDeptService sysDeptService;

	Result result = new Result();

	@Override
	public String selectSysDeptPage(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		PageInfo<SysDept> SysDeptpage = sysDeptService.selectPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(SysDeptpage);
		return result.toJSONString();
	}
	
	@Override
	public String selectListTree(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<DeptTree> sysDeptpage = sysDeptService.selectListTree(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysDeptpage);
		return result.toJSONString();
	}

	@Override
	public String sysDept(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer dictId = obj.getInteger("deptId");
		SysDept SysDept = sysDeptService.selectById(dictId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(SysDept));
		return result.toJSONString();
	}

	@Override
	public String selectSysDeptList(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Map<String, Object> paramMap = JSONObject.parseObject(obj.toString(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<SysDept> SysDeptList = sysDeptService.selectList(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(SysDeptList));
		return result.toJSONString();
	}

	@Override
	public String insertSysDept(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysDept sysDept = JSONObject.parseObject(rw.getValue(), SysDept.class);
		Boolean insert = sysDeptService.insertDept(sysDept);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(insert));
		return result.toJSONString();
	}

	@Override
	public String deleteSysDeptById(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer deptId = obj.getInteger("deptId");
		boolean deleteById = sysDeptService.deleteDeptById(deptId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(deleteById));
		return result.toJSONString();
	}

	@Override
	public String updateSysDept(String param) throws ClientToolsException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysDept sysDept = JSONObject.parseObject(rw.getValue(), SysDept.class);
		boolean updateSysDept = sysDeptService.updateDeptById(sysDept);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(updateSysDept));
		return result.toJSONString();
	}
}
