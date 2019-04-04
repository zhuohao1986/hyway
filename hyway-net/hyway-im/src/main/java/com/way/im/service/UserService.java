package com.way.im.service;

import java.util.List;

import com.way.im.net.bean.User;

public interface UserService {
	
	boolean selectAccount(String account);
	
	int insertInfo(User user);
	
	int getLastID();

	List<User> selectFriendByAccountOrID(Object condition);

	void updateIsOnline(int id, int isOnline);

	List<User> selectFriendByMix(String[] mix);

	boolean login(User user);

}
