package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasClientResource;

public class UcasClientResourcePojo extends UcasClientResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String groupName;
	protected String clientName;
	protected String clientId;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
