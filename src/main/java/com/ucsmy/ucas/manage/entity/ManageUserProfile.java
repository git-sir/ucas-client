package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ManageUserProfile implements Serializable {
    private String userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 个人介绍
     */
    private String introduce;

    /**
     * 工作电话
     */
    private String telephone;

    /**
     * 移动电话
     */
    private String mobilephone;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 员工号
     */
    private String starffId;

    /**
     * 职位
     */
    private String position;

    /**
     * 用户类型，import,ldap，register
     */
    private String type;

    /**
     * 0-正常 1-删除
     */
    private String isdelected;
    
    private ManageUserAccount userAccount;
	private ManageRole role;
	private ManageOrganization org;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStarffId() {
        return starffId;
    }

    public void setStarffId(String starffId) {
        this.starffId = starffId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsdelected() {
        return isdelected;
    }

    public void setIsdelected(String isdelected) {
        this.isdelected = isdelected;
    }

	public ManageUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(ManageUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public ManageRole getRole() {
		return role;
	}

	public void setRole(ManageRole role) {
		this.role = role;
	}

	public ManageOrganization getOrg() {
		return org;
	}

	public void setOrg(ManageOrganization org) {
		this.org = org;
	}
}