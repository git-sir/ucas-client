package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasClientInfo implements Serializable {
    /**
     * 应用clientid
     */
    private String clientId;

    /**
     * 应用组UUID
     */
    private String cligUuid;

    /**
     * 应用口令
     */
    private String clientSecret;
    /**
     * 应用简称
     */
    private String clientName;

    /**
     * 应用描述
     */
    private String descRibe;

    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 应用主页
     */
    private String clientUrl;

    /**
     * 应用状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最近修改时间
     */
    private Date modifyDate;

    private UcasClientGroup ucasClientGroup;

    private static final long serialVersionUID = 1L;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCligUuid() {
        return cligUuid;
    }

    public void setCligUuid(String cligUuid) {
        this.cligUuid = cligUuid;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDescRibe() {
        return descRibe;
    }

    public void setDescRibe(String descRibe) {
        this.descRibe = descRibe;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public UcasClientGroup getUcasClientGroup() {
        return ucasClientGroup;
    }

    public void setUcasClientGroup(UcasClientGroup ucasClientGroup) {
        this.ucasClientGroup = ucasClientGroup;
    }
}