package com.way.api.system;

import com.way.common.exception.BusinessException;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 * 
 */
public interface SysUserApi{
	/**
	 * 获取当前用户信息（角色、权限）
	 * 并且异步初始化用户部门信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public String userinfo(String param) throws BusinessException;
	
	/**
	  * 通过ID查询当前用户信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public String user(String param) throws BusinessException;
	
	public String deleteUser(String param) throws BusinessException;
	
	public String insertUser(String param) throws BusinessException;
	
	public String updateUser(String param) throws BusinessException;
	
	public String selectUserByName(String param) throws BusinessException;

	public String selectSysUserPage(String param) throws BusinessException;

	public String sysUser(String param) throws BusinessException;

	public String sysUserList(String param) throws BusinessException;

	public String userSignIn(String param);

	public String userSignOut(String param);

	public String getUserByToken(String param);

}
