package com.way.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.constant.CommonConstant;
import com.way.common.pojos.system.SysResources;
import com.way.common.vo.MenuVO;
import com.way.dao.SysResourcesMapper;
import com.way.system.api.SysResourcesService;

import tk.mybatis.mapper.entity.Example;


/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
@Service
public class SysResourcesServiceImpl implements SysResourcesService {
    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public List<MenuVO> findMenuByRoleName(String role) {
        return sysResourcesMapper.findMenuByRoleName(role);
    }

    @Override
    public Boolean deleteMenu(Integer id) {
        // 删除当前节点
    	SysResources condition1 = new SysResources();
        condition1.setResourceId(id);
        condition1.setDelState(CommonConstant.STATUS_DEL);
        this.updateMenuById(condition1);

        // 删除父节点为当前节点的节点
        SysResources conditon2 = new SysResources();
        conditon2.setParentId(id);
        SysResources sysMenu = new SysResources();
        sysMenu.setDelState(CommonConstant.STATUS_DEL);
        
        Example example=new Example(SysResources.class);
        example.createCriteria().andEqualTo("parentId", id);
        sysResourcesMapper.updateByExample(sysMenu, example);
        if(sysResourcesMapper.updateByPrimaryKeySelective(sysMenu)<=0) {
			return false;
		}
        return true;
    }

	@Override
	public Boolean updateMenuById(SysResources sysResources) {
		if(sysResourcesMapper.updateByPrimaryKeySelective(sysResources)<=0) {
			return false;
		}
		return true;
	}
}
