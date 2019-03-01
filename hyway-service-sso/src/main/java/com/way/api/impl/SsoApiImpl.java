package com.way.api.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.api.sso.SsoApi;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.ResultConstant;
import com.way.common.pojos.User;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.AESUtil;
import com.way.service.UserService;

@Service
public class SsoApiImpl implements SsoApi {
	
	private static final Logger logger = LoggerFactory.getLogger(SsoApiImpl.class);
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private UserService userService;
	
	Result result;
	
	@Override
	public String userLogin(String userName, String userPwd/* String param */) throws Exception {
		result=new Result(CodeConstants.RESULT_FAIL);
		try {
			//RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			//JSONObject obj = JSONObject.parseObject(rw.getValue());
			//String userName = obj.getString("userName");
			//String userPwd = obj.getString("userPwd");
			boolean checkUserName = userService.checkUserName(userName);
			if(!checkUserName) {
				result.setMessage("用户不存在");
				result.setValue(ResultConstant.USER_ERROR_001);
				return result.toJSONString();
			}
			//加密密码
			String encryptPwd=AESUtil.encryptByDefaultKey(userPwd);
			
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("userName", userName);
			paramMap.put("userPwd", encryptPwd);
			
			User user=userService.selectUserByUserNameAndPwd(paramMap);
			if(null==user) {
				result.setMessage("用户密码错误");
				result.setValue(ResultConstant.USER_ERROR_002);
				return result.toJSONString();
			}
			String hy_token = UUID.randomUUID().toString();
			
			jedisClient.set(REDIS_USER_SESSION_KEY + ":" + hy_token, JSONObject.toJSONString(user));
			// 设置session的过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + hy_token, SSO_SESSION_EXPIRE);
			result.setCode(CodeConstants.RESULT_SUCCESS);
			result.setMessage("登陆成功");
		} catch (Exception e) {
			
		}
		return result.toJSONString();

	}
	
	
	@Override
	public String getUserByToken(String param) throws IOException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		String mw_token = rw.getValue();
		JSONObject josn = JSONObject.parseObject(mw_token);
		String redisKey = REDIS_USER_SESSION_KEY + ":" + josn.getString("hy_token");
		logger.info("redisKey:" + redisKey);
		// 取出用户信息json
		String uerStr = jedisClient.get(redisKey);
		// 构造用户
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"success",uerStr);
		return result.toJSONString();
	}

	/*
	 * @Override public String userLogout(String param) { // TODO Auto-generated
	 * method stub return null; }
	 */

	@Override
	public String resetUserCache(String param) {
		// TODO Auto-generated method stub
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String hy_token = rw.getValue();
			logger.info("UserApiImpl>>>resetUserCache>>hy_token:" + hy_token);
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + hy_token, SSO_SESSION_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(CodeConstants.RESULT_FAIL, "重设用户缓存有效时间失败");
			return result.toString();
		}
		result = new Result(CodeConstants.RESULT_SUCCESS, "重设用户缓存有效时间成功");
		return result.toString();
	}

	/*
	 * @Override public String checkLogin(String param) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public String getMessage(String param) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public String checkCaptcha() { // TODO Auto-generated method stub
	 * return null; } public static void main(String[] args) throws Exception {
	 * System.out.println(AESUtil.encryptByDefaultKey("123456")); }
	 */
}
