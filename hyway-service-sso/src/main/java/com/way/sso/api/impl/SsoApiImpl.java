package com.way.sso.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.KeyConstant;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.service.sso.api.SsoApi;

public class SsoApiImpl implements SsoApi {
	private static final Logger logger = LoggerFactory.getLogger(SsoApiImpl.class);

	@Autowired
	private JedisClientSingle jedisClient;
	
	Result result;
	
	@Override
	public String getUserByToken(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		String mw_token = rw.getValue();
		JSONObject josn = JSONObject.parseObject(mw_token);
		String redisKey = KeyConstant.REDIS_USER_SESSION_KEY + ":" + josn.getString("mw_token");
		logger.info("redisKey:" + redisKey);
		// 取出用户信息json
		String frUserStr = jedisClient.get(redisKey);
		// 构造用户
		Result result=new Result();
		return result.toJSONString();
	}

	@Override
	public String userLogout(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String resetUserCache(String param) {
		// TODO Auto-generated method stub
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String mw_token = rw.getValue();
			logger.info("UserApiImpl>>>resetUserCache>>mw_token:" + mw_token);
			jedisClient.expire(KeyConstant.REDIS_USER_SESSION_KEY + ":" + mw_token, KeyConstant.SSO_SESSION_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(CodeConstants.RESULT_FAIL, "重设用户缓存有效时间失败");
			return result.toString();
		}
		result = new Result(CodeConstants.RESULT_SUCCESS, "重设用户缓存有效时间成功");
		return result.toString();
	}

	@Override
	public String checkLogin(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkCaptcha() {
		// TODO Auto-generated method stub
		return null;
	}

}
