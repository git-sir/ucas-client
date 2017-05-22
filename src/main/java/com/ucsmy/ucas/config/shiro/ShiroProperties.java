package com.ucsmy.ucas.config.shiro;

public class ShiroProperties {
	private boolean hasPassword = true;
	private boolean hasCaptcha = true;
	private boolean openFilters = true;

	public boolean isHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(boolean hasPassword) {
		this.hasPassword = hasPassword;
	}

	public boolean isHasCaptcha() {
		return hasCaptcha;
	}

	public void setHasCaptcha(boolean hasCaptcha) {
		this.hasCaptcha = hasCaptcha;
	}

	public boolean isOpenFilters() {
		return openFilters;
	}

	public void setOpenFilters(boolean openFilters) {
		this.openFilters = openFilters;
	}
}
