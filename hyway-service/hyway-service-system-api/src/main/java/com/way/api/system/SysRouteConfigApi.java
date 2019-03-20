package com.way.api.system;

import java.io.IOException;

import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;

/**
 * <p>
 * 系统 路由 服务类
 * </p>
 * 
 */
public interface SysRouteConfigApi {
	/**
	 * 分页查询路由
	 * 
	 * @param param limit page
	 * @return
	 */
	public String selectSysRouteConfigPage(String param) throws BusinessException;

	/**
	 * 路由Id查询
	 * 
	 * @param param id
	 * @return
	 */
	public String sysRouteConfigList(String param) throws BusinessException;

	/**
	 * 路由Id查询
	 * 
	 * @param param id
	 * @return
	 */
	public String sysRouteConfig(String param) throws BusinessException;

	/**
	 * 查询路由
	 * 
	 * @param param
	 * @return
	 */
	public String insert(String param) throws BusinessException;

	/**
	 * 删除路由
	 * 
	 * @param param
	 * @return
	 */
	public String deleteById(String param) throws BusinessException;

	/**
	 * 更新路由
	 * 
	 * @param param
	 * @return
	 */
	public String updateSysRouteConfig(String param) throws BusinessException;
    /**
     * 获取路由发布的最新版本
     * @return
     * @throws BusinessException
     */
	public String getSysRouteConfigLastVersion() throws BusinessException;

	/**
	 * 刷新路由緩存并通知网关更新
	 * 
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public String refresh() throws ClientToolsException, NumberFormatException, IOException;
    /**
     * 刷新路由緩存
     * @return
     * @throws ClientToolsException
     * @throws NumberFormatException
     * @throws IOException
     */
	public String refreshCacheGatewayRoutes() throws ClientToolsException, NumberFormatException, IOException;

}
