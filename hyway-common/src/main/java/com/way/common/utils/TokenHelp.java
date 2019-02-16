/*package com.way.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.way.common.cache.JedisClient;
import com.way.common.constant.KeyConstant;
@Component
public class TokenHelp {
	
	@Autowired
	private JedisClient jedisClient;
	
	public String getToken(String userId) throws Exception {
    	StringBuffer buffer=new StringBuffer();
    	buffer.append(userId);
    	buffer.append("_");
    	buffer.append(System.currentTimeMillis());
        String token = AESUtil.encryptByDefaultKey(buffer.toString());
        LogUtil.infoLogs(token);
        jedisClient.set(KeyConstant.userAccessKey+userId, token);
        return token;
    }
    public String checkToken(String token) throws Exception {
        String userId = AESUtil.decryptByDefaultKey(token).split("_")[0];
        String currentToken = jedisClient.get(KeyConstant.userAccessKey+userId);
        if (StringUtils.isEmpty(currentToken)) {
            return null;
        }
        if (!token.equals(currentToken)) {
            return null;
        }
        return userId;
    }
}
*/