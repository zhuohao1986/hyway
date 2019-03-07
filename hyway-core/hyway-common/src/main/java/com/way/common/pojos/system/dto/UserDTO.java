package com.way.common.pojos.system.dto;

import java.util.List;

import com.way.common.pojos.system.SysUser;


public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    private List<Integer> role;

    private Integer deptId;

    /**
     * 新密码
     */
    private String newpassword1;

	public List<Integer> getRole() {
		return role;
	}

	public void setRole(List<Integer> role) {
		this.role = role;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getNewpassword1() {
		return newpassword1;
	}

	public void setNewpassword1(String newpassword1) {
		this.newpassword1 = newpassword1;
	}
    
    
}
