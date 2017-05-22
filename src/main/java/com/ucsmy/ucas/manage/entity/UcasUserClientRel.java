package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasUserClientRel implements Serializable {
    /**
     * OPENID
     */
    private String openId;

    /**
     * 帐号UUID
     */
    private String accUuid;

    /**
     * 应用clientid
     */
    private String clientId;

    private static final long serialVersionUID = 1L;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccUuid() {
        return accUuid;
    }

    public void setAccUuid(String accUuid) {
        this.accUuid = accUuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}