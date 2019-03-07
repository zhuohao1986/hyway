package com.way.service;

import java.util.Map;

import com.way.common.pojos.sso.User;

public interface UserService {

	boolean checkUserName(String userName);

	User selectUserByUserNameAndPwd(Map<String, Object> paramMap);

}
