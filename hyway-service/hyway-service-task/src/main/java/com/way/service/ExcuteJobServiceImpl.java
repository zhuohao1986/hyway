package com.way.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.api.feign.SysRouteConfigFeignApi;

@Service
public class ExcuteJobServiceImpl implements ExcuteJobService{
	
	@Autowired
	private SysRouteConfigFeignApi sysRouteConfigFeignApi;
	
	@Override
	public String  excuteRefresh() {
		
		return sysRouteConfigFeignApi.refreshRoute();
	}

}
