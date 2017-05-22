package com.ucsmy.ucas.config.shiro.csrf;

import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.config.LoginTypeConfig;
import com.ucsmy.ucas.config.oauth2Authorize.Oauth2Http;
import com.ucsmy.ucas.config.shiro.ShiroRealmImpl;
import com.ucsmy.ucas.config.shiro.csrf.token.Token;
import com.ucsmy.ucas.manage.dao.ManageUserOauth2RelMapper;
import com.ucsmy.ucas.manage.entity.ManageUserOauth2Rel;
import com.ucsmy.ucas.manage.service.ManageUserAccountService;
import com.ucsmy.ucas.manage.service.SysTokenManagerService;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;



public class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
	private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    private static final String TOKEN_TYPE = "token_type";
    private static final String TYPE_ACCESS_TOKEN = "ACCESS";
    private static final String TYPE_LOCAL_TOKEN = "LOCAL";





    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;
    private String headerName = DEFAULT_CSRF_HEADER_NAME;
    private String token_type = TOKEN_TYPE;
    private String sessionAttributeName;

    public HttpSessionCsrfTokenRepository() {
        this.sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;
    }
    @Autowired
    Oauth2Http oauth2Http;
    @Autowired
    ManageUserAccountService manageUserAccountService;
    @Autowired
    SysTokenManagerService sysTokenManagerService;

    @Autowired
    private LoginTypeConfig loginTypeConfig;

    @Autowired
    ManageUserOauth2RelMapper manageUserOauth2RelMapper;

    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session;
        if(token == null) {
            session = request.getSession(false);
            if(session != null) {
                session.removeAttribute(this.sessionAttributeName);
            }
        } else {
            session = request.getSession();
            session.setAttribute(this.sessionAttributeName, token);
        }

    }

    public CsrfToken loadToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (CsrfToken)session.getAttribute(this.sessionAttributeName);
    }

	public CsrfToken generateToken(HttpServletRequest request) {

        String type = "";
        Token token = null;
        token = new Token(this.headerName, this.parameterName, TYPE_LOCAL_TOKEN, this.createNewToken());
        if (StringAndNumberUtil.isNull(request.getParameter(token_type))) {
            token =this.getAccessToken(token, request);
        } else {
            if (request.getParameter(token_type).equals(TYPE_LOCAL_TOKEN)) {

                token = new Token(this.headerName, this.parameterName, TYPE_LOCAL_TOKEN, this.createNewToken());

            }
            if (request.getParameter(token_type).equals(TYPE_ACCESS_TOKEN))
                token =this.getAccessToken(token, request);
        }

        return token;
	}


    public void setParameterName(String parameterName) {
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        this.headerName = headerName;
    }

    public void setSessionAttributeName(String sessionAttributeName) {
        Assert.hasLength(sessionAttributeName, "sessionAttributename cannot be null or empty");
        this.sessionAttributeName = sessionAttributeName;
    }

  
	private String createNewToken() {
        return UUID.randomUUID().toString();
    }

	public CsrfToken generateToken(HttpServletRequest request, String accessCode) {
		// TODO Auto-generated method stub
		 return new Token(this.headerName, this.parameterName,  TYPE_ACCESS_TOKEN,this.getoauth2Token(accessCode));
	}
    @Override
	public String getoauth2Token(String accessCode) {
		// TODO Auto-generated method stub

        String token="";
		try {
              token =  oauth2Http.getAccessToken(accessCode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return token;
	}

    /****
     * 是否由统一认证登录成功回调
     * 1.来源有统一认证标识
     * 2.请求带code参数
     * @return
     */
    @Override
    public boolean isOauthLoginSuccess(HttpServletRequest request) {

            if(loginTypeConfig.getLocalType())
                return false;
            String code = request.getParameter("code");
            if(code!= null )
                return true;
                return false;

    }

    @Override
    public void addUserOauthRel(Subject subject) {
        if(loginTypeConfig.getLocalType())
            return;
        ShiroRealmImpl.LoginUser user = (ShiroRealmImpl.LoginUser) subject.getPrincipal();
       String openId = sysTokenManagerService.getSysLoginToken("openId");
        ManageUserOauth2Rel manageUserOauth2Rel = manageUserOauth2RelMapper.selectByOpenid(openId);
        if (manageUserOauth2Rel==null)
        {
            manageUserOauth2Rel = new ManageUserOauth2Rel();
            manageUserOauth2Rel.setOpenId(openId);
            manageUserOauth2Rel.setUserId(user.getId());
            manageUserOauth2Rel.setUuid(UUIDGenerator.generate(32));
            manageUserOauth2RelMapper.insert(manageUserOauth2Rel);
        }
    }


    public Token getAccessToken(Token token, HttpServletRequest request) {
        String code = request.getParameter("code");
        if (!(StringAndNumberUtil.isNull(code))){
            Token accessToken = new Token(this.headerName, this.parameterName, TYPE_LOCAL_TOKEN, this.getoauth2Token(code));
            String account = manageUserAccountService.getAccountByOprenid(sysTokenManagerService.getSysLoginToken("openId"));
            if (accessToken!=null&& !(StringAndNumberUtil.isNull(account))) {
                if (!accessToken.getToken().equals("false")) {
                    Boolean bo = oauth2Http.userLogin(account);
                    if (bo) {
                        token = accessToken;
                    }
                }
            }
        }
        return token;
    }
}
