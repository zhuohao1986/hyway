package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysDept;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author 
 * @since 
 */
public interface SysDeptMapper extends IBaseMapper<SysDept> {

    /**
     * 关联dept——relation
     *
     * @param delFlag 删除标记
     * @return 数据列表
     */
    List<SysDept> selectDeptDtoList(String delFlag);

    /**
     * 删除部门关系表数据
     * @param id 部门ID
     */
    void deleteDeptRealtion(Integer id);

	List<SysDept> selectList(Map<String, Object> paramMap);
}
