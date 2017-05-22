package com.ucsmy.ucas.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class ShiroUtils {
	public static ShiroRealmImpl.LoginUser getContextUser() {
		return (ShiroRealmImpl.LoginUser) SecurityUtils.getSubject().getPrincipal();
	}


	public static boolean hasRolePermission(String permissionName) {
		return SecurityUtils.getSubject().hasRole(permissionName);
	}

	public static Boolean autoLogin(String userNameOauth2) {
		// TODO Auto-generated method stub
		 Subject subject = SecurityUtils.getSubject();  
		  subject.login( new MyUsernamePasswordToken(userNameOauth2, "", false, "", "",true));
		  return true;
	}

	/*public boolean login(Subject subject, CustomFormAuthenticationFilter filter, ServletRequest request, ServletResponse response) {
		try {
			subject.login(filter.createToken(request, response));
			return true;
		} catch (AuthenticationException ae) {
			filter.setFailureAttribute(request, ae);
			return false;
		} catch (Exception e) {
			request.setAttribute(filter.getFailureKeyAttribute(), "登录异常");
			return false;
		}
	}
	*//**
	 * 指定帐号登出操作；current：true，当前用户和userName匹配也一起退出
	 *//*
	public static void logout(String userName, boolean current) {
		// 处理session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// 获取当前已登录的用户session列表

		Session currentSession = SecurityUtils.getSubject().getSession();
		for (Session session : sessions) {
			Object key = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (key == null)
				continue;
			SimplePrincipalCollection principal = (SimplePrincipalCollection) key;
			CustomRealm.LoginUser loginUser = (CustomRealm.LoginUser) principal.getPrimaryPrincipal();
			if (!userName.equals(loginUser.getUserName()))
				continue;
			if (!current && session.getId() == currentSession.getId())
				continue;
			session.setTimeout(0);
			//sessionManager.getSessionDAO().delete(session);
		}
	}*/
}
