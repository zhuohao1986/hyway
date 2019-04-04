package com.way.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.constant.CommonConstant;
import com.way.common.pojos.system.SysResources;
import com.way.common.pojos.system.dto.ResourcesTree;
import com.way.common.utils.TreeUtil;
import com.way.dao.SysResourcesMapper;
import com.way.system.service.SysResourcesService;

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

	@Override
	public PageInfo<SysResources> selectPage(Map<String, Object> paramMap) {
		Integer page=Integer.parseInt(String.valueOf(paramMap.get("page")));
    	Integer limit=Integer.parseInt(String.valueOf(paramMap.get("limit")));
    	PageHelper.startPage(page, limit);
    	List<SysResources> selectRolePage = sysResourcesMapper.selectList(paramMap);
    	PageInfo<SysResources> pages=new PageInfo<SysResources>(selectRolePage);
		return pages;
	}
	/**
     * 查询部门树
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    @Override
    public List<ResourcesTree> selectListTree(Map<String,Object> paramMap) {
    	
        return getResourcesTree(this.selectList(paramMap), 0);
    }
    
    @Override
    public List<ResourcesTree> selectListResourcesTree(Integer roleId) {
    	
        return getResourcesTree(sysResourcesMapper.selectRoleResourcesList(roleId), 0);
    }
    @Override
    public List<SysResources> selectRoleListResources(Integer roleId) {
    	
        return sysResourcesMapper.selectRoleResourcesList(roleId);
    }

	/**
     * 构建部门树
     *
     * @param depts 部门
     * @param root  根节点
     * @return
     */
    private List<ResourcesTree> getResourcesTree(List<SysResources> resources, int root) {
        List<ResourcesTree> trees = new ArrayList<>();
        ResourcesTree node;
        for (SysResources res : resources) {
            if (res.getParentId().equals(res.getResourceId())) {
                continue;
            }
            node = new ResourcesTree();
            node.setId(res.getResourceId());
            node.setParentId(res.getParentId());
            node.setName(res.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }

	@Override
	public SysResources selectById(Integer id) {
		
		return sysResourcesMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysResources> selectList(Map<String, Object> paramMap) {
		
		return sysResourcesMapper.selectList(paramMap);
	}

	@Override
	public Boolean insertSysResources(SysResources sysResources) {
		int insert = sysResourcesMapper.insert(sysResources);
		if(insert<=0) {
			return false;
		}
		return true ;
	}

	@Override
	public boolean deleteResourcesById(Integer resourcesId) {
		int delete = sysResourcesMapper.deleteByPrimaryKey(resourcesId);
		if(delete<=0) {
			return false;
		}
		return true ;
	}

	@Override
	public boolean updateSysResourcesById(SysResources sysResources) {
		int update = sysResourcesMapper.updateByPrimaryKey(sysResources);
		if(update<=0) {
			return false;
		}
		return true ;
	}
}
