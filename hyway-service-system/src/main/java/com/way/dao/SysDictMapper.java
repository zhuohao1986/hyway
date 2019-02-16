package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysDict;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-11-19
 */
public interface SysDictMapper extends IBaseMapper<SysDict> {

	List<SysDict> selectList(Map<String, Object> paramMap);

}
