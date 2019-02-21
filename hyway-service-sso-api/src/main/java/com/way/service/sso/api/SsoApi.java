package com.way.service.sso.api;

public interface SsoApi {
	
	public String getUserByToken(String param);
	
	//退出
    public String userLogout(String param);

    // 重新设置用户信息缓存有效时间
    public String resetUserCache(String param);
    
    public String checkLogin(String param);

    public String getMessage(String param);

    public String checkCaptcha();

}
