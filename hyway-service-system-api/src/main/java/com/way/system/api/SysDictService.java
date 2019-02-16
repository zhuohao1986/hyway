package com.way.system.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysDict;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
public interface SysDictService{

	SysDict selectById(Integer id);

	PageInfo<SysDict> selectPage(Map<String, Object> paramMap);

	List<SysDict> selectList(Map<String, Object> paramMap);

	int insert(Map<String, Object> paramMap);

	int deleteById(Integer id);

	int updateSysDict(Map<String, Object> paramMap);

}
