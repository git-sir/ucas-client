package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageUserOrganization implements Serializable {
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 所属机构根节点id
     */
    private String rootOrgId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRootOrgId() {
        return rootOrgId;
    }

    public void setRootOrgId(String rootOrgId) {
        this.rootOrgId = rootOrgId;
    }
}