package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageModule implements Serializable {
    private String moduleId;

    private String description;

    private Integer priority;

    /**
     * 判断标志
     */
    private String sn;

    private String url;

    private String parentId;

    private String name;

    /**
     * 是否是叶子节点。0：不是叶子节点；1：是叶子节点
     */
    private String leaf;

    //图标
    private String image;

    private static final long serialVersionUID = 1L;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}