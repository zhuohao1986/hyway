package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.sso.User;

public interface UserMapper extends IBaseMapper<User> {

	List<User> selectUser(String userName);

	List<User> selectUserByUserNameAndPwd(Map<String, Object> paramMap);
	
}