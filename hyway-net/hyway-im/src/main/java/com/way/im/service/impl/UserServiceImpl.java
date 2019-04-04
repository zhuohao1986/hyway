package com.way.im.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.way.dao.UserMapper;
import com.way.im.net.bean.User;
import com.way.im.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
	private UserMapper userMapper;

	@Override
	public boolean selectAccount(String account) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("account", account);
		List<User> selectByExample = userMapper.selectByExample(example);
		if(selectByExample.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public int insertInfo(User user) {
		
		return userMapper.insert(user);
	}

	@Override
	public int getLastID() {
		// TODO Auto-generated method stub
		return userMapper.selectMaxId();
	}
	/**
	 * 进行登录的验证
	 */
	public  boolean login(User user) {
		boolean isExisted = false;
		Map<String,String> map=new HashMap<String, String>();
		map.put("account", user.getAccount());
		map.put("password",user.getPassword());
		User u=userMapper.selectAccountAndPwd(map);
		if(null!=u){
			isExisted=true;
		}
		return isExisted;
	}

	/**
	 * 更新在线状态
	 */
	@Override
	public  void updateIsOnline(int id, int isOnline) {
		User u=new User();
		u.setId(id);
		u.setIsOnline(true);
		userMapper.updateByPrimaryKeySelective(u);
		
	}
    @Override
	public  List<User> selectFriendByAccountOrID(Object condition) {
		List<User> list = new ArrayList<User>();
		
		return list;
	}
    @Override
	public  List<User> selectFriendByMix(String[] mix) {
		List<User> list = new ArrayList<User>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", 18);
		map.put("end",20);
		userMapper.selectFriendByMix(map);
		return list;
	}

}
