package com.way.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.way.api.sso.SsoApi;
import com.way.common.constant.CodeConstants;
import com.way.common.stdo.RequestWrapper;
import com.way.common.utils.CookieUtils;

public class LoginInterceptor  implements HandlerInterceptor{  
	  
     private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);  
     
     @Autowired
     private SsoApi ssoApi;
  
    /** 
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {  
  
        log.info("---------------------开始进入请求地址拦截----------------------------");  
        String hy_token = CookieUtils.getCookieValue(httpServletRequest, CodeConstants.REDIS_USER_KEY);
        log.info("CookieInterceptor>>>preHandle>>mw_token:"+hy_token);
		
        if(!StringUtils.isEmpty(hy_token)){  
        	RequestWrapper requestWrapper = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,hy_token);
        	ssoApi.resetUserCache(requestWrapper.toString());
			return true;
        }  
        else{  
            PrintWriter printWriter = httpServletResponse.getWriter();  
            printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");  
            return false;  
        }  
  
    }
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		 log.info("--------------处理请求完成后视图渲染之前的处理操作---------------"); 
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		 log.info("---------------视图渲染之后的操作-------------------------0");  
		
	}  
  
    
}  
