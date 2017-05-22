package com.ucsmy.ucas.config.shiro.csrf;

import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CsrfTokenRepository {
	public CsrfToken generateToken(HttpServletRequest request);

	public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response);

	public CsrfToken loadToken(HttpServletRequest request);
	
	public String getoauth2Token( String accessToken);
//
//	public void matchs(HttpServletRequest request);

	/***
	 * 判断是否由要绑定帐号
	 * */
	 boolean isOauthLoginSuccess( HttpServletRequest request);

	/***绑定网金帐号跟本地帐号***/
	void addUserOauthRel(Subject subject);
}
