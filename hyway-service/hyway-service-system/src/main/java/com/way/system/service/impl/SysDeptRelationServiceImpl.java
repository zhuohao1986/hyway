package com.way.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysDeptRelation;
import com.way.dao.SysDeptRelationMapper;
import com.way.system.service.SysDeptRelationService;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 
 * @since 2018-02-12
 */
@Service
public class SysDeptRelationServiceImpl  implements SysDeptRelationService {
	
	private SysDeptRelationMapper sysDeptRelationMapper;

	@Override
	public List<SysDeptRelation> selectList(Example example) {
		// TODO Auto-generated method stub
		return sysDeptRelationMapper.selectByExample(example);
	}

}
