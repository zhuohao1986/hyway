package com.way.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysDict;
import com.way.dao.SysDictMapper;
import com.way.service.SysDictService;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-11-19
 */
@Service
public class SysDictServiceImpl  implements SysDictService {
	
	@Autowired
	private SysDictMapper sysDictMapper;

	@Override
	public SysDict selectById(Integer id) {
		return sysDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<SysDict> selectPage(Map<String, Object> paramMap) {
		Integer page=Integer.parseInt(String.valueOf(paramMap.get("page")));
    	Integer limit=Integer.parseInt(String.valueOf(paramMap.get("limit")));
    	PageHelper.startPage(page, limit);
    	List<SysDict> selectRolePage = sysDictMapper.selectList(paramMap);
    	PageInfo<SysDict> pages=new PageInfo<SysDict>(selectRolePage);
		return pages;
	}

	@Override
	public List<SysDict> selectList(Map<String, Object> paramMap) {
		List<SysDict> selectRolePage = sysDictMapper.selectList(paramMap);
		return selectRolePage;
	}

	@Override
	public int insert(SysDict sysDict) {
		return sysDictMapper.insert(sysDict);
	}

	@Override
	public int deleteById(Integer id) {
		return sysDictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateSysDict(SysDict sysDict) {
		return sysDictMapper.updateByPrimaryKeySelective(sysDict);
	}

}
