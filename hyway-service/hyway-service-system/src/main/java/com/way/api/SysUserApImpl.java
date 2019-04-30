package com.way.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysUserApi;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.exception.BusinessException;
import com.way.common.pojos.system.SysUser;
import com.way.common.pojos.system.SysUserRole;
import com.way.common.pojos.system.dto.UserDTO;
import com.way.common.pojos.system.dto.UserInfo;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.DEncryptionUtils;
import com.way.common.utils.StringUtils;
import com.way.common.vo.UserVO;
import com.way.system.service.SysUserRoleService;
import com.way.system.service.SysUserService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@Service
public class SysUserApImpl implements SysUserApi {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public String userinfo(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		UserVO userVo=JSONObject.parseObject(rw.getValue(),UserVO.class);
		UserInfo findUserInfo = sysUserService.findUserInfo(userVo);
		result.setMessage("查询成功");
		result.setValue(findUserInfo);
		return result.toJSONString();
	}

	@Override
	public String user(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		UserVO userVO = sysUserService.selectUserVoById(id);
		result.setMessage("查询成功");
		result.setValue(userVO);
		return result.toJSONString();
	}

	@Override
	public String deleteUser(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		SysUser sysuser = sysUserService.selectUseById(id);
		if(CommonConstant.ADMIN_USER_NAME.equals(sysuser.getUsername())) {
			result.setMessage("不允许删除超级管理员");
			result.setCode(CodeConstants.RESULT_FAIL);
			return result.toJSONString();
		}
		Boolean flag = sysUserService.deleteUserById(sysuser);
		result.setValue(flag);
		return result.toJSONString();
	}

	@Override
	public String insertUser(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		UserDTO userDto=JSONObject.parseObject(rw.getValue(), UserDTO.class);
		SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelState(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(DEncryptionUtils.aesEncoder(userDto.getNewpassword1()));
        sysUser.setUuid(UUID.randomUUID().toString());
        sysUserService.insertSysUser(sysUser);
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleService.insert(userRole);
        });
        result.setMessage("添加成功");
		return result.toJSONString();
	}

	@Override
	public String updateUser(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectUserByName(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectSysUserPage(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Object>>(){});
		PageInfo<SysUser> sysUserpage = sysUserService.selectPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysUserpage);
		return result.toJSONString();
	}

	@Override
	public String sysUser(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		SysUser sysuser = sysUserService.selectUseById(id);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysuser);
		return result.toJSONString();
	}

	@Override
	public String sysUserList(String param) throws BusinessException {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		SysUser sysuser = sysUserService.selectUseById(id);
		if(CommonConstant.ADMIN_USER_NAME.equals(sysuser.getUsername())) {
			result.setMessage("不允许删除超级管理员");
			result.setCode(CodeConstants.RESULT_FAIL);
			return result.toJSONString();
		}
		Boolean flag = sysUserService.deleteUserById(sysuser);
		result.setValue(flag);
		return result.toJSONString();
	}

	@Override
	public String userSignIn(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS,"登陆成功");
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		String userName = obj.getString("username");
		String pwd = obj.getString("password");
		UserVO sysuser = sysUserService.findUserByUsername(userName);
		if(sysuser==null) {
			result.setMessage("用户不存在");
			result.setCode(CodeConstants.RESULT_FAIL);
			return result.toJSONString();
		}
		if(!StringUtils.equals(sysuser.getPassword(), DEncryptionUtils.aesEncoder(pwd))) {
			result.setMessage("密码不正确");
			result.setCode(CodeConstants.RESULT_FAIL);
			return result.toJSONString();
		}
		sysuser.setPassword(null);
		String hyway_admin_token=UUID.randomUUID().toString().replaceAll("-","");
		jedisClient.set(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+hyway_admin_token,JSONObject.toJSONString(sysuser));
		jedisClient.expire(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+hyway_admin_token, ConfigKeyConstant.REDIS_ADMIN_USER_EXPIRE);
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", sysuser.getUuid());
		map.put("name", sysuser.getUsername());
		map.put("token",hyway_admin_token);
		result.setValue(JSONObject.toJSONString(map));
		return result.toJSONString();
	}

	@Override
	public String userSignOut(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		String hyway_admin_token = obj.getString("hyway_admin_token");
		jedisClient.del(ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+":"+hyway_admin_token);
		return result.toJSONString();
	}
	/**
	 * 根据令牌取用户信息
	 */
	@Override
	public String getUserByToken(String param) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String hyway_admin_token = rw.getValue();
			JSONObject josn = JSONObject.parseObject(hyway_admin_token);
			String redisKey = ConfigKeyConstant.REDIS_ADMIN_USER_SESSION_KEY+ ":" + josn.getString("hyway_admin_token");
			// 取出用户信息json
			String userStr = jedisClient.get(redisKey);
			UserVO sysuser=JSONObject.parseObject(userStr, UserVO.class);
			result.setValue(JSONObject.toJSONString(sysuser));
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(CodeConstants.RESULT_FAIL, "004");
			return result.toString();
		}

		return result.toJSONString();
	}
}
