package com.way.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysResourcesApi;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysResources;
import com.way.common.pojos.system.SysUserRole;
import com.way.common.pojos.system.dto.ResourcesTree;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.vo.UserVO;
import com.way.system.service.SysResourcesService;
import com.way.system.service.SysUserRoleService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@Service
public class SysResourcesApiImpl implements SysResourcesApi {

	@Autowired
	private SysResourcesService sysResourcesService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private JedisClient jedisClient;

	Result result = new Result();

	@Override
	public String selectSysResourcesPage(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		PageInfo<SysResources> SysResourcespage = sysResourcesService.selectPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(SysResourcespage);
		return result.toJSONString();
	}
	
	@Override
	public String selectListTree(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<ResourcesTree> sysResourcespage = sysResourcesService.selectListTree(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysResourcespage);
		return result.toJSONString();
	}
	
	@Override
	public String selectRoleListTree(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer roleId = obj.getInteger("roleId");
		List<ResourcesTree> sysResourcespage = sysResourcesService.selectListResourcesTree(roleId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysResourcespage);
		return result.toJSONString();
	}

	@Override
	public String sysResources(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer dictId = obj.getInteger("deptId");
		SysResources SysResources = sysResourcesService.selectById(dictId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(SysResources));
		return result.toJSONString();
	}

	@Override
	public String selectSysResourcesList(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Map<String, Object> paramMap = JSONObject.parseObject(obj.toString(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<SysResources> SysResourcesList = sysResourcesService.selectList(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(SysResourcesList));
		return result.toJSONString();
	}

	@Override
	public String insertSysResources(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysResources SysResources = JSONObject.parseObject(rw.getValue(), SysResources.class);
		Boolean insert = sysResourcesService.insertSysResources(SysResources);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(insert));
		return result.toJSONString();
	}

	@Override
	public String deleteSysResourcesById(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer resourcesId = obj.getInteger("resourcesId");
		boolean deleteById = sysResourcesService.deleteResourcesById(resourcesId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(deleteById));
		return result.toJSONString();
	}

	@Override
	public String updateSysResources(String param) throws ClientToolsException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysResources SysResources = JSONObject.parseObject(rw.getValue(), SysResources.class);
		boolean updateSysResources = sysResourcesService.updateSysResourcesById(SysResources);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(updateSysResources));
		return result.toJSONString();
	}

	@Override
	public String selectUserSysResources(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String userStr = jedisClient.hget(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+obj.getString(ConfigKeyConstant.AUTHENTICATION_KEY),ConfigKeyConstant.AUTHENTICATION_USERINFO_CACHE_KEY);
		UserVO user=JSONObject.parseObject(userStr, UserVO.class);
		SysUserRole userRole = sysUserRoleService.getUserRole(user.getUserId());
		List<ResourcesTree> resourcesTree=new ArrayList<>();
		if(jedisClient.exists(ConfigKeyConstant.REDIS_ROLE_RESOURCES_SESSION_KEY+userRole.getRoleId())) {
			String roleResourcesStr=jedisClient.get(ConfigKeyConstant.REDIS_ROLE_RESOURCES_SESSION_KEY+userRole.getRoleId());
			resourcesTree=JSONObject.parseArray(roleResourcesStr, ResourcesTree.class);
		}else {
			resourcesTree = sysResourcesService.selectListResourcesTree(userRole.getRoleId());
		}
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(resourcesTree));
		return result.toJSONString();
	}
}
