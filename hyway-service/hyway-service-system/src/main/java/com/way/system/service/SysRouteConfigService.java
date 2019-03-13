package com.way.system.service;

import com.way.common.pojos.system.SysRouteConfig;

public interface SysRouteConfigService {

	Long getSysRouteConfigLastVersion();

	int update(SysRouteConfig sysRouteConfig);

}
