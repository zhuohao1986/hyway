package com.way.authentication.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.way.common.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysUserFeignApi;
import com.way.authentication.api.AuthenticationApi;
import com.way.authentication.auth.JedisClient;
import com.way.common.auth.JWTUtil;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.ConfigKeyConstant;
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
		
		Result result = sysUserFeignApi.login(JSONObject.toJSON(reqMap).toString());
		if(result.getCode().equals(CodeConstants.RESULT_FAIL)) {
			return result.toJSONString();
		}
		SysUser user=JSONObject.parseObject(result.getValue().toString(), SysUser.class);
		
		String user_token = StringUtils.getUUIDString();
		// 生成token
		String token = JWTUtil.generateToken(user_token);
		// 生成refreshToken
		// 数据放入redis
		jedisClient.hset(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, ConfigKeyConstant.AUTHENTICATION_KEY, user_token);
		jedisClient.hset(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, "user", result.getValue().toString());
		// 设置token的过期时间
		jedisClient.expire(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, ConfigKeyConstant.REDIS_ADMIN_USER_EXPIRE);
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("token", token);
		respMap.put("refreshToken", user_token);
		respMap.put("uuid", user.getUuid());
		respMap.put("name", user.getUsername());
		
		return new Result(CodeConstants.RESULT_SUCCESS, "success", JSONObject.toJSONString(respMap)).toJSONString();
	}

	@Override
	public String refreshToken(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String refreshToken = obj.getString("refreshToken");
		String username = jedisClient.hget(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ refreshToken, ConfigKeyConstant.AUTHENTICATION_KEY);
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
		long del = jedisClient.del(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ refreshToken);
		return new Result(CodeConstants.RESULT_SUCCESS, "success",del).toJSONString();
	}

	@Override
	public String getInfo(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String refreshToken = obj.getString("refreshToken");
		String hgetval = jedisClient.hget(refreshToken, "user");
		SysUser user=JSONObject.parseObject(hgetval, SysUser.class);
		return new Result(CodeConstants.RESULT_SUCCESS, "success",JSONObject.toJSON(user)).toJSONString();
	}
	@Override
	public String openIDLogin(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		String mw_token;
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String valueJson = rw.getValue();
			JSONObject valueStr = JSONObject.parseObject(valueJson);
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("openId", valueStr.getString("authToken"));
			if (paramMap.isEmpty()) {
				result = new Result(CodeConstants.RESULT_FAIL, "001");// 没有该用户
			}else{
				Result resultLogin = sysUserFeignApi.loginOpenId(JSONObject.toJSON(paramMap).toString());
				if(resultLogin.getCode().equals(CodeConstants.RESULT_FAIL)) {
					return result.toJSONString();
				}
				SysUser user=JSONObject.parseObject(result.getValue().toString(), SysUser.class);

				String user_token = StringUtils.getUUIDString();
				// 生成token
				String token = JWTUtil.generateToken(user_token);
				// 生成refreshToken
				// 数据放入redis
				jedisClient.hset(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, ConfigKeyConstant.AUTHENTICATION_KEY, user_token);
				jedisClient.hset(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, "user", result.getValue().toString());
				// 设置token的过期时间
				jedisClient.expire(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ user_token, ConfigKeyConstant.REDIS_ADMIN_USER_EXPIRE);
				Map<String, String> respMap = new HashMap<String, String>();
				respMap.put("token", token);
				respMap.put("refreshToken", user_token);
				respMap.put("uuid", user.getUuid());
				respMap.put("name", user.getUsername());
				return new Result(CodeConstants.RESULT_SUCCESS, "success", JSONObject.toJSONString(respMap)).toJSONString();
			}
		}catch (Exception e) {
			result = new Result(CodeConstants.RESULT_FAIL, "001");// 没有该用户
		}
		return result.toString();
	}
	@Override
	public String updateOpenId(String param) throws Exception {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
//			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
//			frUser = JSONObject.parseObject(rw.getValue(), FrUser.class);
//			JSONObject valueStr = JSONObject.parseObject(rw.getValue());
//			frUser=new FrUser();
//			frUser.setUserLoginName(valueStr.getString("userLoginName"));
//			String userPwd=valueStr.getString("userPwd");
//			List<FrUser> userList = frUserService.userLogin(frUser);
//			if(userList.size() >0){
//				FrUser user = userList.get(0);
//				// 比对密码
//				if (!MD5Utils.getMD5Password(userPwd).equals(user.getUserPwd())) {
//					result = new Result(CodeConstants.RESULT_SUCCESS, "002", "密码错误");
//					return result.toString();
//				}
//				FrUser useropen = new FrUser();
//				useropen.setUserId(user.getUserId());
//				useropen.setQqOpenId(valueStr.getString("authToken"));
//				frUserService.updateUserQqOpenId(useropen);
//				result = new Result(CodeConstants.RESULT_SUCCESS,"000","绑定成功");
//			}else {
//				result = new Result(CodeConstants.RESULT_SUCCESS,"001","账号不存在");
//			}
		} catch (Exception e) {
			throw  new  Exception();
		}
		return result.toString();

	}

	@Override
	public String userCreate(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		String valueJson = rw.getValue();
		JSONObject valueStr = JSONObject.parseObject(valueJson);
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("openId", valueStr.getString("qqOpenId"));
		if (paramMap.isEmpty()) {
			result = new Result(CodeConstants.RESULT_FAIL, "001");// 没有该用户
		}else{
			Result resultUser = sysUserFeignApi.userAutoReg(JSONObject.toJSON(paramMap).toString());
			if(resultUser.getCode().equals(CodeConstants.RESULT_FAIL)) {
				return result.toJSONString();
			}
		}
		return new Result(CodeConstants.RESULT_SUCCESS, "success").toJSONString();
	}
}
