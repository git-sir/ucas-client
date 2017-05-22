package com.ucsmy.ucas.manage.entity;

import com.ucsmy.commons.tree.BaseTreeNode;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageOrganization extends BaseTreeNode {
    /**
     * 机构唯一标识
     */
    private String orgId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 父机构id
     */
    private String parentId;

    /**
     * 根机构id
     */
    private String rootId;

    private static final long serialVersionUID = 1L;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
}