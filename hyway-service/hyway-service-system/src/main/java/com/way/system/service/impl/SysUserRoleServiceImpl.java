package com.way.system.service.impl;

import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysUserRole;
import com.way.dao.SysUserRoleMapper;
import com.way.system.service.SysUserRoleService;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
@Service
public class SysUserRoleServiceImpl  implements SysUserRoleService {
	
	private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    @Override
    public Boolean deleteByUserId(Integer userId) {
        return sysUserRoleMapper.deleteByUserId(userId);
    }

	@Override
	public void insert(SysUserRole userRole) {
		sysUserRoleMapper.insert(userRole);
	}
}
