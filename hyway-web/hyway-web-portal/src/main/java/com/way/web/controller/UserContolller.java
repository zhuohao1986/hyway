package com.way.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.api.sso.SsoApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.utils.CookieUtils;

@RestController("user")
public class UserContolller extends BaseController{

	@Autowired
	private SsoApi ssoApi;

	@RequestMapping("/login")
	public String login(String userName,String userPwd) {
		try {
			initParams();
			return ssoApi.userLogin(userName,userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/user")
	public String user(HttpServletRequest httpServletRequest) {
		try {
			initParams();
			String hy_token = CookieUtils.getCookieValue(httpServletRequest, CodeConstants.REDIS_USER_KEY);
        	RequestWrapper requestWrapper = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,hy_token);
			return ssoApi.getUserByToken(requestWrapper.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
