package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasClientResgrRel implements Serializable {
    /**
     * UUID
     */
    private String uuid;

    /**
     * 应用clientid
     */
    private String clientId;

    /**
     * 资源组UUID
     */
    private String resGroupUuid;

    private Integer maxTimes;//ticket使用次数

    private Integer expiryTime;//ticket有效期

    private Date createDate;//最近修改时间

    private Date modifyDate;//创建时间

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResGroupUuid() {
        return resGroupUuid;
    }

    public void setResGroupUuid(String resGroupUuid) {
        this.resGroupUuid = resGroupUuid;
    }

    public Integer getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
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
}