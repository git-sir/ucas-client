package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManagePermission implements Serializable {
    private String permissionId;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    private String name;

    /**
     * 判断标识
     */
    private String sn;

    /**
     * 模块id
     */
    private String moduleId;

    /**
     * 资源URL
     */
    private String urlAction;

    private static final long serialVersionUID = 1L;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getUrlAction() {
        return urlAction;
    }

    public void setUrlAction(String urlAction) {
        this.urlAction = urlAction;
    }
}