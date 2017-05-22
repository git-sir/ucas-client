package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasAccountGroup implements Serializable {
    /**
     * 帐号组UUID
     */
    private String accgUuid;

    /**
     * 帐号组名称
     */
    private String groupName;

    /**
     * 帐号组描述
     */
    private String descRibe;

    private static final long serialVersionUID = 1L;

    public String getAccgUuid() {
        return accgUuid;
    }

    public void setAccgUuid(String accgUuid) {
        this.accgUuid = accgUuid;
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
}