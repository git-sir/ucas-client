package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasClientToken implements Serializable {
    /**
     * UUID
     */
    private String uuid;

    /**
     * 应用clientid
     */
    private String clientId;

    /**
     * OPENID
     */
    private String openId;

    /**
     * 授权码
     */
    private String code;

    /**
     * 令牌token
     */
    private String accessToken;

    /**
     * 最大使用次数
     */
    private Integer maxTimes;

    /**
     * 当前使用次数
     */
    private Integer curTimes;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 失效时间
     */
    private Date invalidDate;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 刷新令牌时效
     */
    private Date refreshTokenDate;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Integer getCurTimes() {
        return curTimes;
    }

    public void setCurTimes(Integer curTimes) {
        this.curTimes = curTimes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshTokenDate() {
        return refreshTokenDate;
    }

    public void setRefreshTokenDate(Date refreshTokenDate) {
        this.refreshTokenDate = refreshTokenDate;
    }
}