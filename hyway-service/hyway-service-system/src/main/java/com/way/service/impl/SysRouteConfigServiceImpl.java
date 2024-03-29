package com.way.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysRouteConfig;
import com.way.dao.SysRouteConfigMapper;
import com.way.service.SysRouteConfigService;

@Service
public class SysRouteConfigServiceImpl implements SysRouteConfigService {

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

	@Override
	public SysRouteConfig getSysRouteConfig(Integer id) {
		return sysRouteConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<SysRouteConfig> selectSysRouteConfigPage(Map<String, Object> paramMap) {
		Integer page = Integer.parseInt(String.valueOf(paramMap.get("page")));
		Integer limit = Integer.parseInt(String.valueOf(paramMap.get("limit")));
		PageHelper.startPage(page, limit);
		List<SysRouteConfig> sysRouteConfigPage = sysRouteConfigMapper.selectList(paramMap);
		PageInfo<SysRouteConfig> pages = new PageInfo<SysRouteConfig>(sysRouteConfigPage);
		return pages;
	}

	@Override
	public List<SysRouteConfig> selectSysRouteConfigList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return sysRouteConfigMapper.selectList(paramMap);
	}

	@Override
	public int insert(SysRouteConfig sysRouteConfig) {
		// TODO Auto-generated method stub
		return sysRouteConfigMapper.insert(sysRouteConfig);
	}

}
