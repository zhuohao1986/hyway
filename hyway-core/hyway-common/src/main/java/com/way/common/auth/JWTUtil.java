package com.way.common.auth;

import java.util.Date;

import com.way.common.utils.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JWTUtil {
	public static final int TOKEN_EXPIRE_TIME = 30 * 60 * 1000; // token过期时间
	public static final int REFRESH_TOKEN_EXPIRE_TIME = 10 * 60 * 1000; // refreshToken过期时间
	private static final String ISSUER = "issuer"; // 签发人

	/**
	 * 生成签名
	 */
	public static String generateToken(String username) {
		Date now = new Date();
		String token =JwtBuilderUtil.buildJWT(username, null, StringUtils.getUUIDString(), ISSUER, now, TOKEN_EXPIRE_TIME);

		return token;
	}

	/**
	 * 验证token
	 */
	public static boolean verify(String token) {
		try {
			return JwtBuilderUtil.checkJWT(token);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 从token获取username
	 */
	public static String getUserInfo(String token) {
		try {
			Jws<Claims> parseJWT = JwtBuilderUtil.parseJWT(JwtBuilderUtil.generateKey(), token);
			Object object = parseJWT.getBody().getSubject();
			return object.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}