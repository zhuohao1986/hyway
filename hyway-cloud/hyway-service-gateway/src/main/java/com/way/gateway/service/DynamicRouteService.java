package com.way.gateway.service;

import com.way.common.exception.GateWayException;

public interface DynamicRouteService {

	public String add(String param) throws GateWayException;

	public String update(String param) throws GateWayException;
	
	public String delete(String param) throws GateWayException;

	public String getRouteDefinitions() throws GateWayException;

	public String refresh();

}
