package com.way.system.service;


import java.util.List;

import com.way.common.pojos.system.SysResources;
import com.way.common.vo.MenuVO;


/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysResourcesService{
    /**
     * 通过角色名称查询URL 权限
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    List<MenuVO> findMenuByRoleName(String role);

    /**
     * 级联删除菜单
     *
     * @param id   菜单ID
     * @return 成功、失败
     */
    Boolean deleteMenu(Integer id);

    /**
     * 更新菜单信息
     *
     * @param sysMenu 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(SysResources sysResources);
}
