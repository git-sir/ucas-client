package com.ucsmy.ucas.manage.ext;

import java.io.Serializable;

/**
 * 用作账号授权管理展示页面，包含一些账号信息
 * Created by chenqilin on 2017/4/25.
 */
public class UserRelManageListPojo implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 帐号UUID
     */
    private String accUuid;

    /**
     * 网金帐号
     */
    private String ucasAccount;

    /**
     * 帐号状态 0-正常 1-冻结 2-删除
     */
    private String status;
    /**
     * 用户组号
     */
    private String accgUuid;
    /**
     * 用户名称
     */
    private String realName;
    /**
     * 用户手机号
     */
    private String mobilePhone;

    /**
     * 邮箱帐号
     */
    private String emailAccount;

    /**
     * 手机帐号
     */
    private String mobileAccount;

    /**
     * 用户性别
     */
    private String sex;

    private String email;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAccUuid() {
        return accUuid;
    }

    public void setAccUuid(String accUuid) {
        this.accUuid = accUuid;
    }

    public String getUcasAccount() {
        return ucasAccount;
    }

    public void setUcasAccount(String ucasAccount) {
        this.ucasAccount = ucasAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAccgUuid() {
        return accgUuid;
    }

    public void setAccgUuid(String accgUuid) {
        this.accgUuid = accgUuid;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getMobileAccount() {
        return mobileAccount;
    }

    public void setMobileAccount(String mobileAccount) {
        this.mobileAccount = mobileAccount;
    }
}
