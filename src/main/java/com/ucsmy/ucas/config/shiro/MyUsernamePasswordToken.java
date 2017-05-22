package com.ucsmy.ucas.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义UsernamePasswordToken，扩展例如验证码之类的参数
 * Created by ucs_zhongtingyuan on 2017/1/17.
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
	//验证码字符串
    private String captcha;
    private boolean autoLogin;
   
	public MyUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
    	this(username, password, rememberMe, host, captcha, false);
    }
    public MyUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha ,Boolean autoLogin) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.autoLogin = autoLogin;
    }
    
    public boolean isAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
