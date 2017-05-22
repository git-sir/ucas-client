package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.entity.UcasClientResgrRel;

public class UcasClientResgrRelPojo extends UcasClientResgrRel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String clientName;
	private String groupName;
	private String descRibe;

	private UcasClientInfo ucasClientInfo;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescRibe() {
		return descRibe;
	}

	public void setDescRibe(String descRibe) {
		this.descRibe = descRibe;
	}

	public UcasClientInfo getUcasClientInfo() {
		return ucasClientInfo;
	}

	public void setUcasClientInfo(UcasClientInfo ucasClientInfo) {
		this.ucasClientInfo = ucasClientInfo;
	}
}
