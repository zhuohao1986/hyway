package com.way.authentication.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysUserFeignApi;
import com.way.authentication.api.AuthenticationApi;
import com.way.authentication.auth.JedisClient;
import com.way.common.auth.JWTUtil;
import com.way.common.constant.CodeConstants;
import com.way.common.pojos.system.SysUser;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.StringUtils;

@Service
public class AuthenticationApiImpl implements AuthenticationApi {

	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private SysUserFeignApi sysUserFeignApi;

	@Override
	public String login(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		
		String userName = obj.getString("username");
		String pwd = obj.getString("password");
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("username", userName);
		reqMap.put("password", pwd);
		RequestWrapper rwq=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_SERVICE,reqMap.toString());
		String loginStr = sysUserFeignApi.login(reqMap.toString());
		Result result=JSONObject.parseObject(loginStr, Result.class);
		if(result.getCode().equals(CodeConstants.RESULT_FAIL)) {
			return result.toJSONString();
		}
		SysUser user=JSONObject.parseObject(result.getValue().toString(), SysUser.class);
		// 生成token
		String token = JWTUtil.generateToken(userName);
		// 生成refreshToken
		String refreshToken = StringUtils.getUUIDString();
		// 数据放入redis
		jedisClient.hset(refreshToken, "token", refreshToken);
		jedisClient.hset(refreshToken, "username", refreshToken);
		// 设置token的过期时间
		jedisClient.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME);
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("token", token);
		respMap.put("refreshToken", refreshToken);
		respMap.put("uuid", user.getUuid());
		respMap.put("name", user.getUsername());
		
		return new Result(CodeConstants.RESULT_SUCCESS, "success", JSONObject.toJSONString(respMap)).toJSONString();
	}

	@Override
	public String refreshToken(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String refreshToken = obj.getString("refreshToken");
		String username = jedisClient.hget("username", refreshToken);
		if (StringUtils.isEmpty(username)) {
			return new Result(CodeConstants.RESULT_FAIL, "refreshToken error").toJSONString();
		}
		// 生成新的token
		String newToken = JWTUtil.generateToken(username);
		jedisClient.hset(refreshToken, "token", newToken);
		return new Result(CodeConstants.RESULT_FAIL, "success", newToken).toJSONString();
	}

	@Override
	public String logout(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String refreshToken = obj.getString("refreshToken");
		String userName = obj.getString("username");
		jedisClient.hdel(refreshToken, userName);
		return new Result(CodeConstants.RESULT_SUCCESS, "success").toJSONString();
	}
}
