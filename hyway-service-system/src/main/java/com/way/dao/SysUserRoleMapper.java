package com.way.dao;


import org.apache.ibatis.annotations.Param;

import com.way.common.pojos.system.SysUserRole;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * <p>
  * 用户角色表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    Boolean deleteByUserId(@Param("userId") Integer userId);
}
