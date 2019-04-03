package com.way.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysRoleResources;
import com.way.dao.SysRoleResourcesMapper;
import com.way.system.service.SysRoleResourcesService;

import tk.mybatis.mapper.entity.Example;


/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
@Service
public class SysRoleResourcesServiceImpl  implements SysRoleResourcesService {
	@Autowired
	private SysRoleResourcesMapper sysRoleResourcesMapper;
    @Override
    public Boolean insertRoleResources(String role, Integer roleId, Collection<Integer> menuIds) {
        SysRoleResources condition = new SysRoleResources();
        condition.setRoleId(roleId);
        Example example=new Example(SysRoleResources.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        sysRoleResourcesMapper.deleteByExample(example);

        List<SysRoleResources> roleMenuList = new ArrayList<>();
        for (Integer menuId : menuIds) {
        	SysRoleResources roleMenu = new SysRoleResources();
            roleMenu.setRoleId(roleId);
            roleMenu.setResourcesId(menuId);
            roleMenuList.add(roleMenu);
        }
        return sysRoleResourcesMapper.insertBatch(roleMenuList);
    }
}
