package com.way.common.pojos.system;

import javax.persistence.*;

@Table(name = "t_sys_role_resources")
public class SysRoleResources {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * @return roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleid
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return menuId
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuid
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}