package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysRouteConfig;

public interface SysRouteConfigMapper extends IBaseMapper<SysRouteConfig> {
	
	Long selectLastestVersion();

	List<SysRouteConfig> selectList(Map<String, Object> paramMap);
}