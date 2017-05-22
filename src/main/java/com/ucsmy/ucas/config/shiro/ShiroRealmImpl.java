package com.ucsmy.ucas.config.shiro;


import com.ucsmy.commons.utils.RSAUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.config.LoginTypeConfig;
import com.ucsmy.ucas.manage.entity.ManagePermission;
import com.ucsmy.ucas.manage.entity.ManageUserAccount;
import com.ucsmy.ucas.manage.service.ManagePermissionService;
import com.ucsmy.ucas.manage.service.ManageUserAccountService;
import com.ucsmy.ucas.manage.service.impl.ManagePermissionServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by ucs_zhongtingyuan on 2017/1/17.
 */
public class ShiroRealmImpl extends AuthorizingRealm {
    @Autowired
    private ManageUserAccountService ucservice;

    @Autowired
    private LoginTypeConfig loginTypeConfig;
/*

    @Autowired
    private ManagePermissionService managePermissionService;
*/


    public ShiroRealmImpl() {
        super();
        this.setCredentialsMatcher(new MyCredentialsMatcher());
    }

    // 登录信息和用户验证信息验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取登录信息
        MyUsernamePasswordToken token = (MyUsernamePasswordToken) authenticationToken;
        Session session = SecurityUtils.getSubject().getSession();
        String sysCaptcha = (String) session.getAttribute(MyFormAuthenticationFilter.CAPTCHA);
        session.removeAttribute(MyFormAuthenticationFilter.CAPTCHA);

        if (token.getUsername() == null) {
            throw new AuthenticationException("用户名不能为空");
        }
        if (token.getPassword() == null) {
            throw new AuthenticationException("密码不能为空");
        }

//		if ( token.getCaptcha() == null) {
//			throw new AuthenticationException("验证码不能为空");
//		}
//
//		if ( !token.getCaptcha().equalsIgnoreCase(sysCaptcha)) {
//			throw new AuthenticationException("验证码不正确");
//		}
        ManageUserAccount user = ucservice.findByUserName(token.getUsername());


        if (user == null) {
            throw new AuthenticationException("用户名不存在");
        }
        if(!loginTypeConfig.getLocalType()
           && !token.isAutoLogin()
           && ucservice.isBindedOauthByUserId(token.getUsername()))
        {
            throw new AuthenticationException("该帐号已被绑定，不能再绑定");
        }
        String account = user.getAccount();
        String salt = user.getSalt();
        String password = String.valueOf(token.getPassword());
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAUtils.PRIVATE_KEY_ATTRIBUTE_NAME);
        if (!StringAndNumberUtil.isNull(password) && privateKey != null) {
            password = RSAUtils.decrypt(privateKey, password);
            token.setPassword(account.concat(password).concat(salt).toCharArray());
        }
        return new SimpleAuthenticationInfo(
                new LoginUser(user.getUserId(), token.getUsername(), user.getAccount())
                , user.getPassword()
                , getName());
    }

    // 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        LoginUser user = ShiroUtils.getContextUser();
        List<ManagePermission> ps;
        ManagePermissionService managePermissionService = new ManagePermissionServiceImpl();
        ps = managePermissionService.queryPermissionByUser(user);
        for (ManagePermission p : ps) {
            if (StringUtils.isEmpty(p.getSn()))
                continue;
            info.addRole(p.getSn());
        }
        return info;
    }

    public static class LoginUser implements Serializable {
        private static final long serialVersionUID = 1L;
        private String id;
        private String loginUserName;
        private String userName;
        private Timestamp loginTime;

        public LoginUser(String id, String loginUserName, String userName) {
            this.id = id;
            this.loginUserName = loginUserName;
            this.userName = userName;
            this.loginTime = new Timestamp(System.currentTimeMillis());
        }

        public String getId() {
            return id;
        }

        public String getLoginUserName() {
            return loginUserName;
        }

        public String getUserName() {
            return userName;
        }

        public Timestamp getLoginTime() {
            return loginTime;
        }
    }
}
