package com.way.common.pojos.system;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "t_sys_role_resources")
public class SysRoleResources {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "resources_id")
    private Integer resourcesId;

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
    public Integer getResourcesId() {
        return resourcesId;
    }

    /**
     * @param menuid
     */
    public void setResourcesId(Integer resourcesId) {
        this.resourcesId = resourcesId;
    }
}