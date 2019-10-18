package com.way.service.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.pojos.system.SysAuth;
import com.way.common.utils.DateUtils;
import com.way.common.utils.IPUtils;
import com.way.dao.SysAuthMapper;
import com.way.service.SysAuthService;

/**
 * @author 
 * @date 2017/10/31
 */
@Service
public class SysAuthServiceImpl  implements SysAuthService {
	
    @Autowired
    private SysAuthMapper sysAuthMapper;

	@Override
	public String authToken(String ip) throws Exception {
		// 通过UUID生成随机的token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 通过IPUtil获取客户端的真实IP地址
        String address = IPUtils.parseIpAmap(ip);
        SysAuth auth=new SysAuth();
        
        auth.setAuthIp(ip);
        auth.setAuthToken(token);
        auth.setAuthAddress(address);
        auth.setAuthState(0);
        auth.setAuthTime(DateUtils.getCurrentDate());
        // 将token相关信息存入数据库中
        sysAuthMapper.insert(auth);
        // 将token返回给客户端
		return token;
	}
	
	@Override
	public SysAuth getAuthInfo(String authToken, String userId, boolean isScan) {
		SysAuth auth = sysAuthMapper.getAuthByToken(authToken);
        // 为空则获取信息失败
        if (auth == null) {
            return auth;
        }
        //手机端访问，如果token等待验证或正在验证，则将token的state和userId更新
        if (isScan && (auth.getAuthState() == 0 || auth.getAuthState() == 2)) {
        	Map<String,Object> paramMap=new HashMap<String, Object>();
        	paramMap.put("authToken",authToken);
        	paramMap.put("state", 2);
        	paramMap.put("userId", userId);
        	sysAuthMapper.setAuthState(paramMap);
        }
        return auth;
    }
	
	@Override
    public Map<String, Integer> setAuthState(String authToken, String uuid) {
        //tokenState：0等待验证，1验证成功，2正在验证，3验证失败（过期）
        Integer state = 3; // 默认token为3，不存在
    	SysAuth auth = sysAuthMapper.getAuthByToken(authToken);
        if (null != auth) {
            state = auth.getAuthState(); // 获得token的状态
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        if (uuid != null && (state == 0 || state == 2)) { // token状态为0，等待验证
            // TODO 要判断token的时间是否已经过期，可以通过时间戳相减获得
            System.out.println("===" + (System.currentTimeMillis() - auth.getAuthTime().getTime()));
            Map<String,Object> paramMap=new HashMap<String, Object>();
        	paramMap.put("authToken",authToken);
        	paramMap.put("state", 1);
        	paramMap.put("userId", uuid);
            sysAuthMapper.setAuthState(paramMap);
            
            hashMap.put("state", 1);
        } else { // token状态为1或3，失效
            hashMap.put("state", 0);
        }
        return hashMap;
    }
    
}
