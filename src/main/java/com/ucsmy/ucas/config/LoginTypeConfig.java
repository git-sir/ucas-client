package com.ucsmy.ucas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ucs_leijinming on 2017/5/2.
 */
@ConfigurationProperties(prefix = "loginType")
@PropertySource("classpath:config/common.yml")
@Component
public class LoginTypeConfig {
    private Boolean localType;
    private String oauth2Url;
    private String clientId;
    private String mainIndex;
    private String clientSecret;
    private String getTokenUrl;

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    private String logout;



    private static final String ROOT_URL_KEY = "USER_CORE_URL";


    public Boolean getLocalType() {
        return localType;
    }

    public void setLocalType(Boolean localType) {
        this.localType = localType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(String mainIndex) {
        this.mainIndex = mainIndex;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getOauth2Url() {
        return oauth2Url;
    }

    public void setOauth2Url(String oauth2Url) {
        this.oauth2Url = oauth2Url;
    }
}
