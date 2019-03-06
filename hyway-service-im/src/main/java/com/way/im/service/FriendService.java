package com.way.im.service;

import java.util.List;

import com.way.im.net.bean.User;

public interface FriendService {
	
	List<User> getFriend(int id);
	
	void addFriend(int id, int friendID);

}
