package com.way.authentication.api;

public interface AuthenticationApi {

	String login(String param);
	
	String logout(String param);

	String refreshToken(String param);

	String getInfo(String param);

	String openIDLogin(String param);

	String updateOpenId(String param) throws Exception;

	String userCreate(String param);
}
