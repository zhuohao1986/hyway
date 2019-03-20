package com.way.api;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysUserApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.exception.BusinessException;
import com.way.common.pojos.system.SysUser;
import com.way.common.pojos.system.SysUserRole;
import com.way.common.pojos.system.dto.UserDTO;
import com.way.common.pojos.system.dto.UserInfo;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.DEncryptionUtils;
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
	private SysUserRoleService sysUserRoleService;

	Result result = new Result(CodeConstants.RESULT_SUCCESS);

	@Override
	public String userinfo(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		UserVO userVo=JSONObject.parseObject(rw.getValue(),UserVO.class);
		UserInfo findUserInfo = sysUserService.findUserInfo(userVo);
		result.setMessage("查询成功");
		result.setValue(findUserInfo);
		return result.toJSONString();
	}

	@Override
	public String user(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		UserVO userVO = sysUserService.selectUserVoById(id);
		result.setMessage("查询成功");
		result.setValue(userVO);
		return result.toJSONString();
	}

	@Override
	public String userDelete(String param) throws BusinessException {
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
	public String userInsert(String param) {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		UserDTO userDto=JSONObject.parseObject(rw.getValue(), UserDTO.class);
		SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelState(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(DEncryptionUtils.aesDecoder(userDto.getNewpassword1()));
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
	public String userUpdate(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String slectUserByName(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectSysUserPage(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sysUser(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sysUserList(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
