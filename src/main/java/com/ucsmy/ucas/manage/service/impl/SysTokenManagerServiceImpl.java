package com.ucsmy.ucas.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.ServiceException;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.config.UserApiConfig;
import com.ucsmy.ucas.manage.service.ManageCommonService;
import com.ucsmy.ucas.manage.service.ManageHttpAosResultService;
import com.ucsmy.ucas.manage.service.SysCacheService;
import com.ucsmy.ucas.manage.service.SysTokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysTokenManagerServiceImpl implements SysTokenManagerService {
    private static final String TOKEN_MANAGER_PATH = "ucas:manager:";
    private static final String TOKEN_KEY = "tokeninfo";
    private static final String SYSTOKEN_KEY = "loginfo";
    @Autowired
    private SysCacheService sysCacheService;

    @Autowired
    private UserApiConfig userApiConfig;

    @Autowired
    private ManageHttpAosResultService manageHttpAosResultService;

    @Autowired
    private ManageCommonService manageCommonService;

    @Override
    @Logger(printSQL = true)
    public String getValidToken() throws Exception {
        Map tokenInfo = new HashMap();
        tokenInfo = this.getTokenByCache();
        if (tokenInfo != null) {

            return tokenInfo.get("token").toString();
        } else {
            tokenInfo = this.getTokenInfo();
            this.saveTokenInfo(tokenInfo, Long.valueOf(tokenInfo.get("time").toString()));
        }
        return tokenInfo.get("token").toString();
    }

    @Override
    @Logger(printSQL = true)
    public String getValidToken(String status) throws Exception {
        Map tokenInfo = new HashMap();
        tokenInfo = this.getTokenByCache();
        if (tokenInfo != null) {
            if (status != null) {
                this.cleanTokenCache();
            } else {

                return tokenInfo.get("token").toString();
            }
        }
        tokenInfo = this.getTokenInfo();
        this.saveTokenInfo(tokenInfo, Long.valueOf(tokenInfo.get("time").toString()));
        return tokenInfo.get("token").toString();
    }

    @Override
    @Logger(printSQL = true)
    public void saveTokenInfo(Map token, Long time) {
        sysCacheService.set(TOKEN_MANAGER_PATH.concat(TOKEN_KEY), token, time);
    }

    @Override
    @Logger(printSQL = true)
    public Map getTokenInfo() throws Exception {
        Map tokenInfo = new HashMap();


        String tokenUrl =  manageCommonService.concantRootUrl(userApiConfig.getToken());
        AosResult aosResult = manageHttpAosResultService.sendGet(tokenUrl);

        if ("0".equals(aosResult.getRetcode())) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(aosResult.getData());
//            JSONObject jsonObject = JSON.parseObject(aosResult.getData());
            if (null != jsonObject.get("access_token") && null != jsonObject.get("expires_in")) {
                tokenInfo.put("token", jsonObject.get("access_token"));
                tokenInfo.put("time", jsonObject.get("expires_in"));
            }
        } else {
            throw new ServiceException("调用外部接口异常");
        }
        return tokenInfo;
    }

    @Override
    @Logger(printSQL = true)
    public Map getTokenByCache() {
        return (Map) sysCacheService.get(TOKEN_MANAGER_PATH.concat(TOKEN_KEY));
    }

    @Override
    @Logger(printSQL = true)
    public void cleanTokenCache() {
        sysCacheService.delete(TOKEN_MANAGER_PATH.concat(TOKEN_KEY));
    }

    @Override
    @Logger(printSQL = true)
    public String setSysLoginToken(Map infoMap) {
        sysCacheService.set(TOKEN_MANAGER_PATH.concat(SYSTOKEN_KEY), infoMap, Long.valueOf(infoMap.get("expiresIn").toString()));
        return infoMap.get("accessToken").toString();
    }

    @Override
    @Logger(printSQL = true)
    public String getSysLoginToken(String key) {
        String returnValue = "";
        Map map = (Map) sysCacheService.get(TOKEN_MANAGER_PATH.concat(SYSTOKEN_KEY));
        if (map != null) {
            returnValue = map.get(key).toString();
        }
        return returnValue;
    }

}
