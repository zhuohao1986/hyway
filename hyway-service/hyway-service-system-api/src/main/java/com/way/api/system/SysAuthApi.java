package com.way.api.system;

import com.way.common.exception.BusinessException;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 * 
 */
public interface SysAuthApi{
	/**
	 * 获取当前用户信息（角色、权限）
	 * 并且异步初始化用户部门信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	String authToken(String param) throws Exception;

	String setAuthState(String param);

	String getAuthInfo(String param);
}
