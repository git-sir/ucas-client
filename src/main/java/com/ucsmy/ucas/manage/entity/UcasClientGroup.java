package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasClientGroup implements Serializable {
    /**
     * 应用组UUID
     */
    private String cligUuid;

    /**
     * 应用组名称
     */
    private String groupName;

    /**
     * 应用组描述
     */
    private String descRibe;

    /**
     * 是否单点登录组
     */
    private String isSso;

    private static final long serialVersionUID = 1L;

    public String getCligUuid() {
        return cligUuid;
    }

    public void setCligUuid(String cligUuid) {
        this.cligUuid = cligUuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescRibe() {
        return descRibe;
    }

    public void setDescRibe(String descRibe) {
        this.descRibe = descRibe;
    }

    public String getIsSso() {
        return isSso;
    }

    public void setIsSso(String isSso) {
        this.isSso = isSso;
    }
}