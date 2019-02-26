package com.way.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.constant.CommonConstant;
import com.way.common.pojos.system.SysDept;
import com.way.common.pojos.system.SysDeptRelation;
import com.way.common.pojos.system.dto.DeptTree;
import com.way.common.utils.TreeUtil;
import com.way.dao.SysDeptMapper;
import com.way.dao.SysDeptRelationMapper;
import com.way.system.service.SysDeptService;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class SysDeptServiceImpl  implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * 添加信息部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    public Boolean insertDept(SysDept dept) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        sysDeptMapper.insert(sysDept);
        insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 维护部门关系
     * @param sysDept 部门
     */
    private void insertDeptRelation(SysDept sysDept) {
        //增加部门关系表
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setDescendant(sysDept.getParentId());
        
        Example example=new Example(SysDeptRelation.class);
        example.createCriteria().andEqualTo("parentId", sysDept.getParentId());
        
        List<SysDeptRelation> deptRelationList = sysDeptRelationMapper.selectByExample(example);
        for (SysDeptRelation sysDeptRelation : deptRelationList) {
            sysDeptRelation.setDescendant(sysDept.getDeptId());
            sysDeptRelationMapper.insert(sysDeptRelation);
        }
        //自己也要维护到关系表中
        SysDeptRelation own = new SysDeptRelation();
        own.setDescendant(sysDept.getDeptId());
        own.setAncestor(sysDept.getDeptId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    public Boolean deleteDeptById(Integer id) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(id);
        sysDept.setModifyTime(new Date());
        sysDept.setDelState(CommonConstant.STATUS_DEL);
        sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        sysDeptMapper.deleteDeptRealtion(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    public Boolean updateDeptById(SysDept sysDept) {
        //更新部门状态
    	sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        //删除部门关系
        sysDeptMapper.deleteDeptRealtion(sysDept.getDeptId());
        //新建部门关系
        this.insertDeptRelation(sysDept);
        return null;
    }

    /**
     * 查询部门树
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    @Override
    public List<DeptTree> selectListTree(Map<String,Object> paramMap) {
    	
        return getDeptTree(this.selectList(paramMap), 0);
    }

    /**
     * 构建部门树
     *
     * @param depts 部门
     * @param root  根节点
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts, int root) {
        List<DeptTree> trees = new ArrayList<>();
        DeptTree node;
        for (SysDept dept : depts) {
            if (dept.getParentId().equals(dept.getDeptId())) {
                continue;
            }
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
    @Override
    public List<SysDept> selectList(Map<String,Object> paramMap){
    	
    	return sysDeptMapper.selectList(paramMap);
    }

	@Override
	public SysDept selectById(Integer id) {
		// TODO Auto-generated method stub
		return sysDeptMapper.selectByPrimaryKey(id);
	}
}
