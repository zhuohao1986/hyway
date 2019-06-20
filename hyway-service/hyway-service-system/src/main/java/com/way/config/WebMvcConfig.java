package com.way.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.way.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 功能描述: 配置静态资源,避免静态资源请求被拦截
	 * @date:
	 * @param:
	 * @return:
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
		super.addResourceHandlers(registry);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/*");
		super.addInterceptors(registry);
	}
}
