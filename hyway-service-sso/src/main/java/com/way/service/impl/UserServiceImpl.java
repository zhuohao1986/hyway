package com.way.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.sso.User;
import com.way.dao.UserMapper;
import com.way.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean checkUserName(String userName) {
		List<User> userList = userMapper.selectUser(userName);
		if(userList.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public User selectUserByUserNameAndPwd(Map<String,Object> paramMap) {
		List<User> userList = userMapper.selectUserByUserNameAndPwd(paramMap);
		if(userList.size()>0) {
			return userList.get(0);
		}
		return null;
	}
}
