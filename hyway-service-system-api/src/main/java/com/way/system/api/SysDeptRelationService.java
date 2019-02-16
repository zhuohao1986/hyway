package com.way.system.api;

import java.util.List;

import com.way.common.pojos.system.SysDeptRelation;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-02-12
 */
public interface SysDeptRelationService{

	List<SysDeptRelation> selectList(Example example);

}
