package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasUserResRel implements Serializable {
    /**
     * UUID
     */
    private String uuid;

    /**
     * 资源UUID
     */
    private String resUuid;

    /**
     * OPENID
     */
    private String openId;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResUuid() {
        return resUuid;
    }

    public void setResUuid(String resUuid) {
        this.resUuid = resUuid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}