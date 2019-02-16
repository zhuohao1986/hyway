package com.way.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysDict;
import com.way.dao.SysDictMapper;
import com.way.system.api.SysDictService;

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
		// TODO Auto-generated method stub
		return sysDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<SysDict> selectPage(Map<String, Object> paramMap) {
		Integer pageNum=Integer.parseInt(String.valueOf(paramMap.get("pageNum")));
    	Integer pageSize=Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
    	PageHelper.startPage(pageNum, pageSize);
    	List<SysDict> selectRolePage = sysDictMapper.selectList(paramMap);
    	PageInfo<SysDict> page=new PageInfo<SysDict>(selectRolePage);
		return page;
	}

	@Override
	public List<SysDict> selectList(Map<String, Object> paramMap) {
		List<SysDict> selectRolePage = sysDictMapper.selectList(paramMap);
		return selectRolePage;
	}

	@Override
	public int insert(Map<String, Object> paramMap) {
		SysDict sysDict=JSONObject.parseObject(JSONObject.toJSONString(paramMap), SysDict.class);
		return sysDictMapper.insert(sysDict);
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return sysDictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateSysDict(Map<String, Object> paramMap) {
		SysDict sysDict=JSONObject.parseObject(JSONObject.toJSONString(paramMap), SysDict.class);
		return sysDictMapper.updateByPrimaryKeySelective(sysDict);
	}

}
