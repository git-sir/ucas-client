package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasClientResource implements Serializable {
    /**
     * 资源UUID
     */
    private String resUuid;

    /**
     * 资源组UUID
     */
    private String resGroupUuid;

    /**
     * 资源URI
     */
    private String resUri;

    /**
     * 资源描述
     */
    private String descRibe;

    /**
     * 是否涉及用户隐私
     */
    private String isPrivacy;

    /**
     * 是否资源组必须授权
     */
    private String isDefault;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最近修改时间
     */
    private Date modifyDate;

    /**
     * 资源状态
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public String getResUuid() {
        return resUuid;
    }

    public void setResUuid(String resUuid) {
        this.resUuid = resUuid;
    }

    public String getResGroupUuid() {
        return resGroupUuid;
    }

    public void setResGroupUuid(String resGroupUuid) {
        this.resGroupUuid = resGroupUuid;
    }

    public String getResUri() {
        return resUri;
    }

    public void setResUri(String resUri) {
        this.resUri = resUri;
    }

    public String getDescRibe() {
        return descRibe;
    }

    public void setDescRibe(String descRibe) {
        this.descRibe = descRibe;
    }

    public String getIsPrivacy() {
        return isPrivacy;
    }

    public void setIsPrivacy(String isPrivacy) {
        this.isPrivacy = isPrivacy;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}