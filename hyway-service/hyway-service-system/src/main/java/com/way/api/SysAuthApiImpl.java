package com.way.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysAuthApi;
import com.way.common.constant.CodeConstants;
import com.way.common.pojos.system.SysAuth;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.service.SysAuthService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 * 
 */
@Service
public class SysAuthApiImpl implements SysAuthApi {

	@Autowired
	private SysAuthService sysAuthService;
	
	@Override
	public String authToken(String param) throws Exception {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String ip=obj.getString("ip");
		String authToken = sysAuthService.authToken(ip);
		result.setValue(authToken);
		return result.toString();
	}

	@Override
	public String setAuthState(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String authToken=obj.getString("authToken");
		String uuid=obj.getString("uuid");
		Map<String, Integer> setAuthState = sysAuthService.setAuthState(authToken, uuid);
		result.setValue(setAuthState);
		return result.toString();
		
	}

	@Override
	public String getAuthInfo(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		//Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Object>>(){});
		String authToken=obj.getString("authToken");
		String uuid=obj.getString("uuid");
		boolean isScan=obj.getBoolean("isScan");
		SysAuth sysAuth = sysAuthService.getAuthInfo(authToken, uuid, isScan);
		result.setValue(sysAuth);
		return result.toString();
	}
}
