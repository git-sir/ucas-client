package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasResourceGroup implements Serializable {
    /**
     * 资源组UUID
     */
    private String resGroupUuid;

    /**
     * 应用clientid
     */
    private String clientId;

    /**
     * 资源组名称
     */
    private String groupName;

    /**
     * 资源组描述
     */
    private String descRibe;

    private static final long serialVersionUID = 1L;

    public String getResGroupUuid() {
        return resGroupUuid;
    }

    public void setResGroupUuid(String resGroupUuid) {
        this.resGroupUuid = resGroupUuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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