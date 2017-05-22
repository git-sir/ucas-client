package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * TOKEN策略表(ucas_token_strategy)
 * 
 * @author
 */
public class UcasTokenStrategy implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5769660838915030686L;
    
    /** 授权码策略UUID */
    private String uuid;
    
    /** 策略所属的应用ID */
    private String clientId;
    
    /** 最大使用次数 */
    private Integer maxTimes;
    
    /** TOKEN有效期，秒为单位 */
    private Integer expiryDate;
    
    /** RefreshToken有效期，秒为单位 */
    private Integer refreshExpiryDate;
    
    /** 策略状态，0-正常，1-停用 */
    private Integer status;
    
    /** 创建时间 */
    private Date createDate;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getMaxTimes() {
        return this.maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Integer getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Integer expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getRefreshExpiryDate() {
        return this.refreshExpiryDate;
    }

    public void setRefreshExpiryDate(Integer refreshExpiryDate) {
        this.refreshExpiryDate = refreshExpiryDate;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}