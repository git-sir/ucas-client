package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageRole implements Serializable {
    /**
     * 角色唯一标识
     */
    private String roleId;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    private String name;

    private static final long serialVersionUID = 1L;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}