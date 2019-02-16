package com.way.system.api;

import java.util.List;
import java.util.Map;

import com.way.common.pojos.system.SysDept;
import com.way.common.pojos.system.dto.DeptTree;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author 
 * @since 
 */
public interface SysDeptService  {

    /**
     * 查询部门树菜单
     * @param Map<String,Object> paramMap
     * @return 树
     */
    List<DeptTree> selectListTree(Map<String,Object> paramMap);

    /**
     * 添加信息部门
     * @param sysDept
     * @return
     */
    Boolean insertDept(SysDept sysDept);

    /**
     * 删除部门
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean deleteDeptById(Integer id);

    /**
     * 更新部门
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);

	List<SysDept> selectList(Map<String, Object> paramMap);

	SysDept selectById(Integer id);
}
