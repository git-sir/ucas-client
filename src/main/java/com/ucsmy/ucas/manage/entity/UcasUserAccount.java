package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasUserAccount implements Serializable {
    /**
     * 帐号UUID
     */
    private String accUuid;

    /**
     * 帐号组UUID
     */
    private String accgUuid;

    /**
     * 网金帐号
     */
    private String ucasAccount;

    /**
     * 手机帐号
     */
    private String mobileAccount;

    /**
     * 邮箱帐号
     */
    private String emailAccount;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 指纹标识
     */
    private String fingerprint;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最近修改时间
     */
    private Date modifyDate;

    /**
     * 帐号状态 0-正常 1-冻结 2-删除
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public String getAccUuid() {
        return accUuid;
    }

    public void setAccUuid(String accUuid) {
        this.accUuid = accUuid;
    }

    public String getAccgUuid() {
        return accgUuid;
    }

    public void setAccgUuid(String accgUuid) {
        this.accgUuid = accgUuid;
    }

    public String getUcasAccount() {
        return ucasAccount;
    }

    public void setUcasAccount(String ucasAccount) {
        this.ucasAccount = ucasAccount;
    }

    public String getMobileAccount() {
        return mobileAccount;
    }

    public void setMobileAccount(String mobileAccount) {
        this.mobileAccount = mobileAccount;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}