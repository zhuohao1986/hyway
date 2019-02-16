package com.way.admin.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcAppConfig {

	/** 
	 * 
	 * 注册拦截器 
	 * Created by SYSTEM on 2017/8/16. 
	 */  
	public class WebAppConfig extends WebMvcConfigurationSupport {  
	  
		@Bean   //把我们的拦截器注入为bean
	    public HandlerInterceptor getLoginInterceptor(){
	        return new LoginInterceptor();
	    }

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
	        // excludePathPatterns 用户排除拦截
	       registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	    }
	} 
}
