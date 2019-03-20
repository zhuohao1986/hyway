package com.way.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysRouteConfig;
/**
 * 动态路由配置表
 * @author way
 *
 */
public interface SysRouteConfigService {

	Long getSysRouteConfigLastVersion();

	int update(SysRouteConfig sysRouteConfig);

	SysRouteConfig getSysRouteConfig(Integer id);

	PageInfo<SysRouteConfig> selectSysRouteConfigPage(Map<String, Object> paramMap);

	List<SysRouteConfig> selectSysRouteConfigList(Map<String, Object> paramMap);

	int insert(SysRouteConfig sysRouteConfig);

}
