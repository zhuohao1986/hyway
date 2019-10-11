package com.way.authentication.controller;

import com.way.common.constant.ConfigKeyConstant;
import com.way.common.utils.CookieUtils;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.authentication.api.AuthenticationApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value="/auth",produces=MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired 
	private AuthenticationApi authenticationApi;

    private final AuthRequestFactory factory;

	public AuthenticationController(AuthRequestFactory factory) {
		this.factory = factory;
	}

	@RequestMapping("/login/{type}")
	public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = factory.get(AuthSource.valueOf(type));
		response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
	}

//	@RequestMapping("/callback/{type}")
//	public AuthResponse login(@PathVariable String type, AuthCallback callback) {
//		AuthRequest authRequest = factory.get(AuthSource.valueOf(type));
//		AuthResponse response = authRequest.login(callback);
//		logger.info("【response】= {}", JSONObject.toJSONString(response));
//		return response;
//	}

	@RequestMapping(value="/login")
	public String login() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.login(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			logger.error(request.getRequestURI(), e);
		}
		return result.toString();
	}
	@RequestMapping(value="/login/openid")
	public String loginOpenid() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.login(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/logout")
	public String logout() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.logout(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/user")
	public String user() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.getInfo(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
	@RequestMapping(value="/refreshToken")
	public String refreshToken() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = authenticationApi.refreshToken(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
    @RequestMapping("/bind")
    @ResponseBody
    public String bind(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String resultStr="";
        try {
            initParams();
            RequestWrapper requestWrapper = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
            resultStr=authenticationApi.updateOpenId(requestWrapper.toString());
            Result result=JSONObject.parseObject(resultStr, Result.class);
            if(CodeConstants.RESULT_SUCCESS.equals(result.getCode())){
                String userStr=authenticationApi.openIDLogin(requestWrapper.toString());
                Result userResult=JSONObject.parseObject(userStr, Result.class);
                // 添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
                CookieUtils.setCookie(getRequest(), getResponse(), "MW_TOKEN", String.valueOf(userResult.getValue()));
                resultStr=result.toString();
            }
        } catch (Exception e) {
            logger.error("绑定异常"+e.getMessage());
        }
        return resultStr;
    }
    /**
     * 获取授权，获取openid,获取用户信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/callback/{type}")
    public String callBackQq(@PathVariable String type, AuthCallback callback, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        AuthRequest authRequest = factory.get(AuthSource.valueOf(type));
        AuthResponse responses = authRequest.login(callback);
        logger.info("【response】= {}", JSONObject.toJSONString(responses));
        try {
            String openID  = null;
            if (responses.ok()) {
                //我们的网站被CSRF攻击了或者用户取消了授权做一些数据统计工作没有获取到响应参数");
                logger.debug("网站被CSRF攻击了或者用户取消了授权");
                return "redirect:http://www.hyway.live";
            } else {
                HashMap<String, String> backMap=JSONObject.parseObject(JSONObject.toJSONString(responses.getData()),new HashMap<String, String>().getClass());
                openID =backMap.get("openID");
                Map<String,String> map=new HashMap<String,String>();
                map.put("authToken", openID);
                //获取用户信息登录
                String mw_token="";
                String userParamStr=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WAP,JSONObject.toJSONString(map)).toString();
                String userResultStr=authenticationApi.openIDLogin(userParamStr);
                Result result=JSONObject.parseObject(userResultStr, Result.class);
                if(CodeConstants.RESULT_FAIL.equals(result.getCode())){
                    Map<String,String> authmap=new HashMap<String,String>();
                    authmap.put("qqOpenId", openID);
                    authmap.put("username", "11111");
                    authmap.put("avator", "11111");
                    String authStr=authenticationApi.userCreate(new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WAP,JSONObject.toJSONString(map)).toString());
                    Result authresult=JSONObject.parseObject(authStr, Result.class);
                    if(CodeConstants.RESULT_SUCCESS.equals(authresult.getCode())) {
                        String userLoginStr=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WAP,JSONObject.toJSONString(map)).toString();
                        String userLoginResultStr=authenticationApi.openIDLogin(userLoginStr);
                        Result userLoginresult=JSONObject.parseObject(userLoginResultStr, Result.class);
                        mw_token=String.valueOf(userLoginresult.getValue());
                    }
                }else{
                    mw_token=String.valueOf(result.getValue());
                }
                // 添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
                CookieUtils.setCookie(getRequest(), getResponse(), ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY, mw_token);
                return "redirect:http://www.hyway.live";
            }
        } catch (Exception e) {
            logger.error("QQ授权"+e.getMessage());
        }
        return JSONObject.toJSONString(responses);
    }
	
}
