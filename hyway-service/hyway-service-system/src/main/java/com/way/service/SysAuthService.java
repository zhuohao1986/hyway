package com.way.service;

import java.util.Map;

import com.way.common.pojos.system.SysAuth;

/**
 * @author 
 * @date 
 */
public interface SysAuthService {
    
	/**
     * 根据用户名查询用户角色信息
     *
     * @param username 用户名
     * @return userVo
     */
	String authToken(String ip) throws Exception;

	Map<String, Integer> setAuthState(String authToken, String uuid);

	SysAuth getAuthInfo(String authToken, String userId, boolean isScan);
}
