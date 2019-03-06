package com.way.dao;

import java.util.List;
import java.util.Map;

import com.way.common.context.IBaseMapper;
import com.way.im.net.bean.User;

public interface UserMapper extends IBaseMapper<User> {

	int selectMaxId();
	
	List<User> selectFriendByMix();
	
	List<User> selectFriendByAccountOrID(Object condition);

	User selectAccountAndPwd(Map<String, String> map);

	List<User> getUser(int id);
}