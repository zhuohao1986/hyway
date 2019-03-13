package com.way.dao;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysRouteConfig;

public interface SysRouteConfigMapper extends IBaseMapper<SysRouteConfig> {
	
	Long selectLastestVersion();
}