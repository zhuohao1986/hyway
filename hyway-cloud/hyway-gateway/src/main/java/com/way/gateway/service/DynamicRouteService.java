package com.way.gateway.service;

import com.way.common.exception.GateWayException;

public interface DynamicRouteService {
	/**
	 * 添加路由规则
	 * @param param
	 * @return
	 * @throws GateWayException
	 */
	public String add(String param) throws GateWayException;

	/**
	 * 更新路由规则
	 * @param param
	 * @return
	 * @throws GateWayException
	 */
	public String update(String param) throws GateWayException;

	/**
	 * 删除路由规则
	 * @param param
	 * @return
	 * @throws GateWayException
	 */
	public String delete(String param) throws GateWayException;

	/**
	 * 获取所有路由规则
	 * @return
	 * @throws GateWayException
	 */
	public String getRouteDefinitions() throws GateWayException;

	/**
	 * 刷新路由规则
	 * @return
	 */
	public String refresh();

}
