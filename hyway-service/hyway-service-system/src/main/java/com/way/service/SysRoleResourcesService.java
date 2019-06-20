package com.way.service;


import java.util.Collection;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysRoleResourcesService{

    /**
     * 更新角色菜单
     *
     *
     * @param role
     * @param roleId  角色
     * @param menuIds 菜单列表
     * @return
     */
    Boolean insertRoleResources(String role, Integer roleId, Collection<Integer> menuIds);
}
