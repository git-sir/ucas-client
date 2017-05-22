package com.ucsmy.ucas.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.config.UserApiConfig;
import com.ucsmy.ucas.manage.dao.UcasUserInfoMapper;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.ext.UserAccountInfo;
import com.ucsmy.ucas.manage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/4/25.
 */
@Service
public class UcasUserAccountServiceImpl implements UcasUserAccountService {

    @Autowired
    private UcasUserInfoMapper  infoMapper;

    @Autowired
    private UserApiConfig userApiConfig;

    @Autowired
      private ManageCommonService manageCommonService;

    @Autowired
    private ManageHttpAosResultService manageHttpAosResultService;

    @Autowired
    private SysTokenManagerService sysTokenManagerService;

    @Autowired
    private UcasAccountGroupService ucasAccountGroupService;

    @Logger(printSQL = true)
    private Map getTokenInfo(String ucasAccount) throws Exception {

        Map map = new HashMap();
        map.put("access_token", sysTokenManagerService.getValidToken());
        Map userAccountMap = new HashMap();
        userAccountMap.put("ucasAccount", ucasAccount);
        map.put("userAccount", userAccountMap);
        return map;
    }

    @Override
    @Logger(printSQL = true)
    public UcasPageInfo<UserAccountInfo> getUsers(UserAccountInfo userAccountInfo, int pageNum, int pageSize) {
        return infoMapper.getUserInfoByName(userAccountInfo, CommStatusEnum.SYS_DELETE.getValue(), new PageRequest(pageNum,pageSize));
    }

    @Override
    @Logger(printSQL = true)
    public AosResult addUserAccount(String ucasAccount, String accgUuid, String emailAccount,
                                    String mobileAccount, String password, String realName,
                                    String mobilePhone, String email, String sex,
                                    String headImgUrl, String orgName) {

        String url = manageCommonService.concantRootUrl(userApiConfig.getAdd());

//        Map map = new HashMap();
//        map.put("access_token", sysTokenManagerService.getValidToken());
//        Map userAccountMap = new HashMap();
//
//        userAccountMap.put("ucasAccount", ucasAccount);
        Map map = null;
        try {
            map = this.getTokenInfo(ucasAccount);
        } catch (Exception e) {
           return AosResult.retFailureMsg("调用外部接口异常");
        }
        Map userAccountMap = (Map) map.get("userAccount");
        userAccountMap.put("userAccg", accgUuid);
        userAccountMap.put("emailAccount", emailAccount);
        userAccountMap.put("mobileAccount", mobileAccount);
        userAccountMap.put("password", password);
        map.put("userAccount", userAccountMap);

        Map userInfo = new HashMap();
        userInfo.put("realName", realName);
        userInfo.put("mobilePhone", mobilePhone);
        userInfo.put("email", email);
        userInfo.put("sex", sex);
        userInfo.put("headImgUrl", headImgUrl);
        userInfo.put("orgName", orgName);
        map.put("userInfo", userInfo);
        String strJson = JSONObject.toJSONString(map);

        AosResult aosResult =  manageHttpAosResultService.sendPostJson(url,strJson);
        if("success".equals(aosResult.getRetmsg()))
            aosResult.setRetmsg("插入成功");
        return aosResult;
    }

    @Logger(printSQL = true)
    private String mapToJsonStr(String ucasAccount) throws Exception {
        Map map = this.getTokenInfo(ucasAccount);
        String strJson = JSONObject.toJSONString(map);
        return strJson;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult freeze(String ucasAccount) {

        AosResult aosResult = null;
        try {
            aosResult = manageHttpAosResultService.sendPostJson( manageCommonService.concantRootUrl(userApiConfig.getFree()), this.mapToJsonStr(ucasAccount));
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult unfreeze(String ucasAccount) {

        AosResult aosResult = null;
        try {
            aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUnfree()),this.mapToJsonStr(ucasAccount));
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }

        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult upEmail(String ucasAccount, String emailAccount) {

        Map map = null;
        try {
            map = this.getTokenInfo(ucasAccount);
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        Map userAccountMap = (Map) map.get("userAccount");
        userAccountMap.put("emailAccount", emailAccount);
        map.put("userAccount", userAccountMap);
        String strJson = JSONObject.toJSONString(map);
        AosResult aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUpdateModeEmail()),strJson);
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult upPhone(String ucasAccount, String mobileAccount) {

        Map map = null;
        try {
            map = this.getTokenInfo(ucasAccount);
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        Map userAccountMap = (Map) map.get("userAccount");
        userAccountMap.put("mobileAccount", mobileAccount);
        map.put("userAccount", userAccountMap);
        String strJson = JSONObject.toJSONString(map);
        AosResult aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUpdateModeMobile()),strJson);
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult upPassword(String ucasAccount, String password) {


        Map map = null;
        try {
            map = this.getTokenInfo(ucasAccount);
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        Map userAccountMap = (Map) map.get("userAccount");
        userAccountMap.put("password", password);
        map.put("userAccount", userAccountMap);
        String strJson = JSONObject.toJSONString(map);
        AosResult aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUpdatePassword()),strJson);
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult delete(String ucasAccount) {

        AosResult aosResult = null;
        try {
            aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getDel()),this.mapToJsonStr(ucasAccount));
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult getUser(String ucasAccount) {

        AosResult aosResult = null;
        try {
            aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUser()),this.mapToJsonStr(ucasAccount));
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        if ("0".equals(aosResult.getRetcode()))
        {

            JSONObject json = (JSONObject) aosResult.getData();
            UcasAccountGroup ucasAccountGroup = ucasAccountGroupService.getAccountGroup((String) json.get("userAccg"));
            if (null!=ucasAccount)
            {
                json.put("groupName",ucasAccountGroup.getGroupName());
            }
            aosResult.setData(json);
        }
        return aosResult;
    }

    @Override
    @Logger(printSQL = true)
    public AosResult upInfo(String ucasAccount, String accgUuid, String realName, String mobilePhone, String email, String sex, String headImgUrl, String orgName) {

        Map map = null;
        try {
            map = this.getTokenInfo(ucasAccount);
        } catch (Exception e) {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
        Map userAccountMap = (Map) map.get("userAccount");
        userAccountMap.put("userAccg", accgUuid);
        map.put("userAccount", userAccountMap);

        Map userInfo = new HashMap();
        userInfo.put("realName", realName);
        userInfo.put("mobilePhone", mobilePhone);
        userInfo.put("email", email);
        userInfo.put("sex", sex);
        userInfo.put("headImgUrl", headImgUrl);
        userInfo.put("orgName", orgName);
        map.put("userInfo", userInfo);
        String strJson = JSONObject.toJSONString(map);
        AosResult aosResult = manageHttpAosResultService.sendPostJson(manageCommonService.concantRootUrl(userApiConfig.getUpdateInfo()),strJson);
        return aosResult;
    }


}
