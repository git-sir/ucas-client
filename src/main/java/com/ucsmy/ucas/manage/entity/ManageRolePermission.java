package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageRolePermission implements Serializable {
    private String id;

    /**
     * 权限Id
     */
    private String permissionId;

    /**
     * 角色id
     */
    private String roleId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}