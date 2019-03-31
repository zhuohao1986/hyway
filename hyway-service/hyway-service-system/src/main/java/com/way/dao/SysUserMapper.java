package com.way.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.ibatis.annotations.Param;

import com.way.common.context.IBaseMapper;
import com.way.common.pojos.system.SysUser;
import com.way.common.vo.UserVO;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-29
 */
public interface SysUserMapper extends IBaseMapper<SysUser> {
    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO selectUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param query     查询条件
     * @param username  用户名
     * @param dataScope 数据权限
     * @return list
     */
    List selectUserVoPageDataScope(Query query, @Param("username") Object username);

    /**
     * 通过手机号查询用户信息（含有角色信息）
     *
     * @param mobile 用户名
     * @return userVo
     */
    UserVO selectUserVoByMobile(String mobile);

    /**
     * 通过openId查询用户信息
     *
     * @param openId openid
     * @return userVo
     */
    UserVO selectUserVoByOpenId(String openId);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserVO selectUserVoById(Integer id);

	List<UserVO> selectWithRolePage(Map<String, Object> paramMap);

	List<SysUser> selectList(Map<String, Object> paramMap);
}
