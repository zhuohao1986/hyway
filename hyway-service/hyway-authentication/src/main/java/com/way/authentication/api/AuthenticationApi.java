package com.way.authentication.api;

public interface AuthenticationApi {

	String login(String param);
	
	String logout(String param);

	String refreshToken(String param);

}
