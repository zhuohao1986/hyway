package com.way.user.api;

import java.util.Map;

import com.way.common.pojos.User;

public interface UserService {

	boolean checkUserName(String userName) throws Exception;

	User selectUserByUserNameAndPwd(Map<String, Object> paramMap);

}
