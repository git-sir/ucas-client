package com.ucsmy.ucas.config;

import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.service.ManageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ucs_mojiazhou on 2017/4/25.
 */

@ConfigurationProperties(prefix = "user")
@PropertySource("classpath:config/common.yml")
@Component
public class UserApiConfig
{

    private String add;
    private String free;
    private String del;

    private String unfree;
    private String updatePassword;
    private String updateModeEmail;
    private String updateModeMobile;
    private String user;
    private String updateInfo;

    private String token;

    @Autowired
    private LoginTypeConfig loginTypeConfig;

/*    @Autowired
    private ManageConfigService manageConfigService;

    private static final String ROOT_URL_KEY = "USER_CORE_URL";

    private String getRootUrl()
    {
        ManageConfig manageConfig = manageConfigService.queryByName(ROOT_URL_KEY);
        if (null!=manageConfig)
            return manageConfig.getParamValue();
        return null;
    }*/

    public String getFree() {
        return free;
    }

    public String getDel() {
        return del;
    }

    public String getAdd() {
        return add;
    }


    public String getUnfree() {
        return unfree;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public String getUpdateModeEmail() {
        return updateModeEmail;
    }

    public String getUpdateModeMobile() {
        return updateModeMobile;
    }

    public String getUser() {
        return user;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public String getToken() {

        return token+"&client_id="+loginTypeConfig.getClientId()+"&client_secret="+loginTypeConfig.getClientSecret();

    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public void setUnfree(String unfree) {
        this.unfree = unfree;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    public void setUpdateModeEmail(String updateModeEmail) {
        this.updateModeEmail = updateModeEmail;
    }

    public void setUpdateModeMobile(String updateModeMobile) {
        this.updateModeMobile = updateModeMobile;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
