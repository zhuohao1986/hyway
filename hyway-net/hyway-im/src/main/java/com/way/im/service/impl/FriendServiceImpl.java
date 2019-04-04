package com.way.im.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.im.Friendlist;
import com.way.dao.FriendlistMapper;
import com.way.dao.UserMapper;
import com.way.im.net.bean.User;
import com.way.im.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FriendlistMapper friendlistMapper;
	
	@Override
	public List<User> getFriend(int id) {


		return userMapper.getUser(id);
	}

	@Override
	public void addFriend(int id, int friendID) {
		Friendlist friendlist=new Friendlist();
		friendlist.setFriendid(friendID);
		friendlist.setMaster(id);
		friendlistMapper.insert(friendlist);
		
	}
	

}
