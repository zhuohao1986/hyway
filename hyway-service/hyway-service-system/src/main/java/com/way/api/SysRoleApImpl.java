package com.way.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysRoleApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysRole;
import com.way.common.pojos.system.SysRoleResources;
import com.way.common.pojos.system.dto.RoleDTO;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.system.service.SysRoleMenuService;
import com.way.system.service.SysRoleService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@Service
public class SysRoleApImpl implements SysRoleApi {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	Result result = new Result();

	@Override
	public String selectSysRolePage(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		PageInfo<RoleDTO> SysRolepage = sysRoleService.selectwithDeptPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(SysRolepage);
		return result.toJSONString();
	}
	
	@Override
	public String selectList(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<SysRole> sysRolepage = sysRoleService.selectListByDeptId(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysRolepage);
		return result.toJSONString();
	}

	@Override
	public String sysRole(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer dictId = obj.getInteger("RoleId");
		SysRole SysRole = sysRoleService.selectById(dictId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(SysRole));
		return result.toJSONString();
	}

	@Override
	public String insertSysRole(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		RoleDTO roleDTO = JSONObject.parseObject(rw.getValue(), RoleDTO.class);
		Boolean insert = sysRoleService.insertRole(roleDTO);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(insert);
		return result.toJSONString();
	}

	@Override
	public String deleteSysRoleById(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer RoleId = obj.getInteger("RoleId");
		int deleteById = sysRoleService.deleteRoleById(RoleId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(deleteById);
		return result.toJSONString();
	}

	@Override
	public String updateSysRole(String param) throws ClientToolsException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		RoleDTO roleDTO = JSONObject.parseObject(rw.getValue(), RoleDTO.class);
		boolean updateSysRole = sysRoleService.updateRoleById(roleDTO);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(updateSysRole));
		return result.toJSONString();
	}

	@Override
	public String updateRoleResources(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
