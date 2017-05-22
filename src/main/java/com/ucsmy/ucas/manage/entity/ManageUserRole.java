package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageUserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* 用户角色关系id */
	private String id;
	/* 用户id */
	private String userId;
	/* 用户名 */
	private String userName;
	/* 用户账号 */
	private String userAccount;
	/* 用户职位 */
	private String position;
	/* 对应角色 */
	private ManageRole role;
	
	public ManageUserRole(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public ManageRole getRole() {
		return role;
	}

	public void setRole(ManageRole role) {
		this.role = role;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}