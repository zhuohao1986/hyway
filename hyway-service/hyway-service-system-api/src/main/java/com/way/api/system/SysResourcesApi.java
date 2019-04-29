package com.way.api.system;

import com.way.common.exception.BusinessException;

public interface SysResourcesApi {
	/**
	 * 菜单分页
	 * @param param
	 * @return
	 */
	String selectSysResourcesPage(String param);
    /**
     * 菜单详细
     * @param param
     * @return
     */
	String sysResources(String param);
    /**
     * 删除菜单
     * @param param
     * @return
     */
	String deleteSysResourcesById(String param);
    /**
     * 更新菜单
     * @param param
     * @return
     */
	String updateSysResources(String param);
    /**
     * 查询树形菜单
     * @param param
     * @return
     * @throws BusinessException
     */
	String selectListTree(String param) throws BusinessException;
    /**
     * 查询所有菜单
     * @param param
     * @return
     * @throws BusinessException
     */
	String selectSysResourcesList(String param) throws BusinessException;
    /**
     * 添加菜单
     * @param param
     * @return
     * @throws BusinessException
     */
	String insertSysResources(String param) throws BusinessException;
    /**
     * 查询用户菜单
     * @param param
     * @return
     */
	String selectUserSysResources(String param);
	
	
	String selectRoleListTree(String param) throws BusinessException;

}
