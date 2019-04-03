package com.way.api.system;

import com.way.common.exception.BusinessException;

public interface SysResourcesApi {
	
	String selectSysResourcesPage(String param);

	String sysResources(String param);

	String deleteSysResourcesById(String param);

	String updateSysResources(String param);

	String selectListTree(String param) throws BusinessException;

	String selectSysResourcesList(String param) throws BusinessException;

	String insertSysResources(String param) throws BusinessException;

}
