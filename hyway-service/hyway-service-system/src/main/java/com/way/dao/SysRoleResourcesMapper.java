package com.way.dao;


import java.util.List;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysRoleResources;

/**
 * <p>
  * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysRoleResourcesMapper extends IBaseMapper<SysRoleResources> {

	Boolean insertBatch(List<SysRoleResources> roleResourcesList);

}
