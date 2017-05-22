package com.ucsmy.ucas.manage.ext;

import java.io.Serializable;

/**
 * Created by ucs_mojiazhou on 2017/4/26.
 */
public class UserAccountInfo implements Serializable {


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
     * 帐号状态 0-正常 1-冻结 2-删除
     */
    private String status;
    /**
     * 用户名称
     */
    private String realName;
    /**
     * 绑定手机
     */
    private String mobilePhone;

    /**
     * 绑定邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;

    /**
     * 所属组织名称
     */
    private String orgName;



    /**
     * 头像地址
     */
    private String headImgUrl;

    /****修改时间**/
    private String modifyDate;

    /****用户组名称**/
    private String groupName;
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
