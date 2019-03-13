package com.way.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysRouteConfig;
import com.way.dao.SysRouteConfigMapper;
import com.way.system.service.SysRouteConfigService;
@Service
public class SysRouteConfigServiceImpl implements SysRouteConfigService{
	
    @Autowired
	SysRouteConfigMapper sysRouteConfigMapper;
	@Override
	public Long getSysRouteConfigLastVersion() {
		
		return sysRouteConfigMapper.selectLastestVersion();
	}
	@Override
	public int update(SysRouteConfig sysRouteConfig) {
		
		return sysRouteConfigMapper.updateByPrimaryKey(sysRouteConfig);
	}

}
