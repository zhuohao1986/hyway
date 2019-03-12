package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysRole;
import com.way.common.pojos.system.dto.RoleDTO;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysRoleMapper extends IBaseMapper<SysRole> {

    /**
     * 查询角色列表含有部门信息
     * @param query 查询对象
     * @param condition 条件
     * @return List
     */
    List<RoleDTO> selectRolePage(Map<String, Object> condition);

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    List<SysRole> selectListByDeptId(Integer deptId);
}
