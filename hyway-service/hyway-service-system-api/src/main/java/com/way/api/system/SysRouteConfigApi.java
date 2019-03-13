package com.way.api.system;

import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;

/**
 * <p>
 *系统 路由 服务类
 * </p>
 * 
 */
public interface SysRouteConfigApi{
	/**
	 * 分页查询路由
	 * @param param limit  page
	 * @return
	 */
	public String selectSysRouteConfigPage(String param) throws BusinessException;
	/**
	 * 路由Id查询
	 * @param param dictId
	 * @return
	 */
	public String SysRouteConfig(String param)  throws BusinessException;
	/**
	 * 查询路由List
	 * @param param dictType
	 * @return
	 */
	public String selectList(String param)  throws BusinessException;
    /**
     * 查询路由
     * @param param
     * @return
     */
	public String insert(String param)  throws BusinessException;
    /**
     * 删除路由
     * @param param
     * @return
     */
	public String deleteById(String param) throws BusinessException;
    /**
     * 更新路由
     * @param param
     * @return
     */
	public String updateSysRouteConfig(String param) throws BusinessException;
	 /**
	  * 刷新路由緩存
     * @param param
     * @return
     */
	public String refresh(String string) throws ClientToolsException;
	
	
	public String getSysRouteConfigLastVersion() throws BusinessException;

}
