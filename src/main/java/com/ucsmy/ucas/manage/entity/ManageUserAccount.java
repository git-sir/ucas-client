package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ManageUserAccount implements Serializable {
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 1:激活 ;  0:初始;  2：停用
     */
    private Byte status;

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobilephone;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
}