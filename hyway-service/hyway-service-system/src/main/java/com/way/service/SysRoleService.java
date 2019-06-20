package com.way.service;


import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysRole;
import com.way.common.pojos.system.dto.RoleDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysRoleService{

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    Boolean insertRole(RoleDTO roleDto);

    /**
     * 分页查角色列表
     *
     * @param objectQuery         查询条件
     * @param objectEntityWrapper wapper
     * @return page
     */
    PageInfo<RoleDTO> selectwithDeptPage(Map<String,Object> paramMap);

    /**
     * 更新角色
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    Boolean updateRoleById(RoleDTO roleDto);

    /**
     * 通过部门ID查询角色列表
     * @param paramMap 部门ID
     * @return 角色列表
     */
    List<SysRole> selectListByDeptId(Map<String, Object> paramMap);

	SysRole selectById(Integer roleId);

	int deleteRoleById(Integer roleId);

	List<SysRole> selectListByDeptId(Integer deptId);
}
