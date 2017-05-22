package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UcasAcccliGroupRel implements Serializable {
    /**
     * UUID
     */
    private String uuid;

    /**
     * 帐号组UUID
     */
    private String accgUuid;

    /**
     * 应用组UUID
     */
    private String cligUuid;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccgUuid() {
        return accgUuid;
    }

    public void setAccgUuid(String accgUuid) {
        this.accgUuid = accgUuid;
    }

    public String getCligUuid() {
        return cligUuid;
    }

    public void setCligUuid(String cligUuid) {
        this.cligUuid = cligUuid;
    }
}