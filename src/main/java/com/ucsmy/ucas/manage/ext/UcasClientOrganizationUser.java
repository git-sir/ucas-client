package com.ucsmy.ucas.manage.ext;

import java.io.Serializable;

/** 
 * @ClassName: UcasClientOrganizationUser
 * @Description: 
 * @author zhengfucheng 
 * @date 2017-3-9 上午10:28:57 
 * @version V1.0 
 */
public class UcasClientOrganizationUser implements Serializable {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 3898765152378981616L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 用户 id
	 */
	private String userId;
	
	/**
	 * 组织 id
	 */
	private String organizationId;

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

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	
	
}
