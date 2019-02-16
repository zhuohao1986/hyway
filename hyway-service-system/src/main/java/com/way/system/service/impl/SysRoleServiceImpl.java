package com.way.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.pojos.system.SysRole;
import com.way.common.pojos.system.SysRoleDept;
import com.way.common.pojos.system.dto.RoleDTO;
import com.way.dao.SysRoleDeptMapper;
import com.way.dao.SysRoleMapper;
import com.way.system.api.SysRoleService;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
@Service
public class SysRoleServiceImpl  implements SysRoleService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.insert(sysRole);
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    /**
     * 分页查角色列表
     *
     * @param query   查询条件
     * @param wrapper wapper
     * @return page
     */
    @Override
    public PageInfo<RoleDTO> selectwithDeptPage(Map<String,Object> paramMap) {
    	Integer pageNum=Integer.parseInt(String.valueOf(paramMap.get("pageNum")));
    	Integer pageSize=Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
    	PageHelper.startPage(pageNum, pageSize);
    	List<RoleDTO> selectRolePage = sysRoleMapper.selectRolePage(paramMap);
    	PageInfo<RoleDTO> page=new PageInfo<RoleDTO>(selectRolePage);
        return page;
    }

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        //删除原有的角色部门关系
        Example example=new Example(SysRoleDept.class);
        example.createCriteria().andEqualTo("roleId", roleDto.getRoleId());
        sysRoleDeptMapper.deleteByExample(example);

        //更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);

        //维护角色部门关系
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectListByDeptId(Integer deptId) {
        return sysRoleMapper.selectListByDeptId(deptId);
    }
}
