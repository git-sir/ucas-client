package com.ucsmy.ucas.config.oauth2Authorize;

public interface Oauth2Http {

	public String getAccessToken(String accessToken) throws Exception; // 获取
																		// access
																		// token

	boolean checkAccessToken(String accessToken); // 验证access token是否有效

	String getUsernameByAccessToken(String accessToken);// 根据access token获取用户信息

	long getExpireIn();// auth code / access token 过期时间

	public Boolean userLogin(String token);

	// public boolean checkClientSecret(String clientSecret);// 坚持客户端安全KEY是否存在
}