package com.way.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysResources;
import com.way.common.vo.MenuVO;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysResourcesMapper extends IBaseMapper<SysResources> {

    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    List<MenuVO> findMenuByRoleName(@Param("role") String role);

	List<SysResources> selectList(Map<String, Object> paramMap);
}
