package com.way.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysAuth;

public interface SysAuthMapper extends IBaseMapper<SysAuth> {

	SysAuth getAuthByToken(@Param("authToken" )String authToken);

	int setAuthState(Map<String,Object> paramMap);
}