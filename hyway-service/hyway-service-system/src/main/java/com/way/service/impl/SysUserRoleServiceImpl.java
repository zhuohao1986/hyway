package com.way.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysUserRole;
import com.way.dao.SysUserRoleMapper;
import com.way.service.SysUserRoleService;

import tk.mybatis.mapper.entity.Example;

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
	
	@Autowired
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
	
	@Override
	public SysUserRole getUserRole(Integer userId) {
		Example example=new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("userId", userId);
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectByExample(example);
		if(sysUserRoleList.size()>0) {
			return sysUserRoleList.get(0);
		}
		return null;
	}
}
