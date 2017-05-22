package com.ucsmy.ucas.manage.ext;

import java.io.Serializable;
 /**
 * @Description  暂无描述
 *
 * @Author xiongs
 * 
 * @Version 1.0
 * 
 * @Since 2017-2-9
 */
public class PermissionPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String permissionId;
	private String permissionName;
	private String permissionSn;
	private boolean cheched = false;
	
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getPermissionSn() {
		return permissionSn;
	}
	public void setPermissionSn(String permissionSn) {
		this.permissionSn = permissionSn;
	}
	public boolean isCheched() {
		return cheched;
	}
	public void setCheched(boolean cheched) {
		this.cheched = cheched;
	}
}
