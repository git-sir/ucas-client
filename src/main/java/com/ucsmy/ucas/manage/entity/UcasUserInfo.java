package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasUserInfo implements Serializable {
    /**
     * 帐号UUID
     */
    private String accUuid;

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
     * 头像地址
     */
    private String headImgUrl;

    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最近修改时间
     */
    private Date modifyDate;

    private static final long serialVersionUID = 1L;

    public String getAccUuid() {
        return accUuid;
    }

    public void setAccUuid(String accUuid) {
        this.accUuid = accUuid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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