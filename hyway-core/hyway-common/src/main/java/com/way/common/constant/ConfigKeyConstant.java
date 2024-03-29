package com.way.common.constant;

/**
 * 缓存KEY
 * @author Administrator
 *
 */
public class ConfigKeyConstant {
	// token
	public final static String REDIS_USER_SESSION_KEY = "REDIS_USER_SESSION";
	//全局COOKIE TOKEN
	public final static String REDIS_USER_KEY = "HY_TOKEN";
	//全局字典缓存
	public final static String REDIS_SYS_DICT_KEY = "REDIS_SYS_DICT_KEY";
	//路由
	public final static String GATEWAY_ROUTES="GATEWAY_ROUTES";
	//发布路由信息的版本号
	public final static String GATEWAY_ROUTES_VERSION="GATEWAY_ROUTES_VERSION";
	
	public final static String REDIS_ADMIN_USER_SESSION_KEY="HYWAY_ADMIN_TOKEN:";
	
	public static final String AUTHENTICATION_KEY="user_token";
	
	public static final String AUTHENTICATION_USERINFO_CACHE_KEY="user";
	
	public final static String REDIS_ROLE_RESOURCES_SESSION_KEY="ROLE_RESOURCES:";
	
	public final static Integer REDIS_ADMIN_USER_EXPIRE=1800;
	
	public final static String GATEWAY_IGNORE_URLS_KEY="GATEWAY_IGNORE_URLS";
}
