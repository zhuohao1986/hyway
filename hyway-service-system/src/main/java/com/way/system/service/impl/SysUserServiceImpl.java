package com.way.system.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.SecurityConstants;
import com.way.common.pojos.system.SysDeptRelation;
import com.way.common.pojos.system.SysUser;
import com.way.common.pojos.system.SysUserRole;
import com.way.common.pojos.system.dto.UserDTO;
import com.way.common.pojos.system.dto.UserInfo;
import com.way.common.stdo.Result;
import com.way.common.utils.LogUtil;
import com.way.common.utils.LogUtils;
import com.way.common.utils.RandomUtil;
import com.way.common.vo.MenuVO;
import com.way.common.vo.SysRole;
import com.way.common.vo.UserVO;
import com.way.dao.SysUserMapper;
import com.way.system.api.SysDeptRelationService;
import com.way.system.api.SysResourcesService;
import com.way.system.api.SysUserRoleService;
import com.way.system.api.SysUserService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author 
 * @date 2017/10/31
 */
@Service
public class SysUserServiceImpl  implements SysUserService {
	
	private  static Logger logger;
	
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysResourcesService sysMenuService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;

    @Override
    public UserInfo findUserInfo(UserVO userVo) {
        SysUser condition = new SysUser();
        condition.setUsername(userVo.getUsername());
        Example example=new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", userVo.getUsername());
        
        SysUser sysUser = sysUserMapper.selectOneByExample(example);

        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表
        List<SysRole> roleList = userVo.getRoleList();
        List<String> roleNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleList)) {
            for (SysRole sysRole : roleList) {
                if (!StringUtils.equals(SecurityConstants.BASE_ROLE, sysRole.getRoleName())) {
                    roleNames.add(sysRole.getRoleName());
                }
            }
        }
        String[] roles = roleNames.toArray(new String[roleNames.size()]);
        userInfo.setRoles(roles);

        //设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roles) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleName(role);
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(permissions.toArray(new String[permissions.size()]));
        return userInfo;
    }

    @Override
    public UserVO findUserByUsername(String username) {
        return sysUserMapper.selectUserVoByUsername(username);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserVO findUserByMobile(String mobile) {
        return sysUserMapper.selectUserVoByMobile(mobile);
    }

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    @Override
    public UserVO findUserByOpenId(String openId) {
        return sysUserMapper.selectUserVoByOpenId(openId);
    }

    @Override
    public PageInfo<UserVO> selectWithRolePage( Map<String,Object> paramMap, UserVO userVO) {
        /*DataScope dataScope = new DataScope();
        dataScope.setScopeName("deptId");
        dataScope.setIsOnly(true);
        dataScope.setDeptIds(getChildDepts(userVO));*/
    	Integer pageNum=Integer.valueOf(String.valueOf(paramMap.get("pageNum")));
    	Integer pageSize=Integer.valueOf(String.valueOf(paramMap.get("pageSize")));
    	PageHelper.startPage(pageNum, pageSize);
        List<UserVO> userList=sysUserMapper.selectWithRolePage(paramMap);
        PageInfo<UserVO> page=new PageInfo<>(userList);
        return page;
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        return sysUserMapper.selectUserVoById(id);
    }

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String randomStr, String imageCode) {
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 发送验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送
     * 2. 未发送： 判断手机号是否存 ? false :产生4位数字  手机号-验证码
     * 3. 发往消息中心-》发送信息
     * 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    @SuppressWarnings("unchecked")
	@Override
    public Result sendSmsCode(String mobile) {
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
        Object tempCode = redisTemplate.opsForValue().get(SecurityConstants.DEFAULT_CODE_KEY + mobile);
        if (tempCode != null) {
        	
        	LogUtils.error(logger, "用户:{}验证码未失效{}", mobile, tempCode);
            result.setMessage("验证码未失效，请失效后再次申请");
            result.setCode(CodeConstants.RESULT_FAIL);
            return result;
        }
        SysUser params = new SysUser();
        params.setPhone(mobile);
        Example example=new Example(SysUser.class);
        example.createCriteria().andEqualTo("phone", mobile);
        List<SysUser> userList=sysUserMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(userList)) {
        	LogUtils.error(logger,"根据用户手机号{}查询用户为空", mobile);
            result.setMessage("手机号不存在");
            result.setCode(CodeConstants.RESULT_FAIL);
            return result;
        }
        String code = RandomUtil.generateDigitalString(4);
        LogUtils.info(logger,"短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + mobile, code, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
        return result;
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        sysUserMapper.deleteByPrimaryKey(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    public Result updateUserInfo(UserDTO userDto, String username) {
    	Result result=new Result();
        UserVO userVo = this.findUserByUsername(username);
        SysUser sysUser = new SysUser();
        if (StringUtils.isNotBlank(userDto.getPassword())
                && org.apache.commons.lang3.StringUtils.isNotBlank(userDto.getNewpassword1())) {
            if (ENCODER.matches(userDto.getPassword(), userVo.getPassword())) {
                sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
            } else {
            	LogUtil.warnLogs("原密码错误，修改密码失败:{}", username);
            	result.setMessage("原密码错误，修改失败");
            	result.setCode(CodeConstants.RESULT_FAIL);
                return result;
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVo.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        result.setCode(CodeConstants.RESULT_SUCCESS);
        return result;
    }

    @Override
    public Boolean updateUser(UserDTO userDto, String username) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setModifyTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(sysUser);

        Example example=new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId",userDto.getUserId());
        
        sysUserRoleService.deleteByUserId(userDto.getUserId());
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleService.insert(userRole);
        });
        return Boolean.TRUE;
    }

    /**
     * 获取当前用户的子部门信息
     *
     * @param userVO 用户信息
     * @return 子部门列表
     */
    private List<Integer> getChildDepts(UserVO userVO) {
        UserVO userVo = findUserByUsername(userVO.getUsername());
        Integer deptId = userVo.getDeptId();

        //获取当前部门的子部门
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setAncestor(deptId);
        Example example=new Example(SysDeptRelation.class);
        example.createCriteria().andEqualTo("ancestor",deptId);
        List<SysDeptRelation> deptRelationList = sysDeptRelationService.selectList(example);
        List<Integer> deptIds = new ArrayList<>();
        for (SysDeptRelation sysDeptRelation : deptRelationList) {
            deptIds.add(sysDeptRelation.getDescendant());
        }
        return deptIds;
    }

	@Override
	public Page selectWithRolePage(Query query, UserVO userVO) {
		// TODO Auto-generated method stub
		return null;
	}
}
