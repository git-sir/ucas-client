package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.ext.UserAccountInfo;

import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/4/25.
 */
public interface UcasUserAccountService {

    /****token封装**/
//    Map getTokenInfo(String ucasAccount);

    /****获取用户**/
    UcasPageInfo<UserAccountInfo> getUsers(UserAccountInfo userAccountInfo, int pageNum, int pageSize);

    /***添加用户***/
    AosResult addUserAccount(String ucasAccount, String accgUuid, String emailAccount,
                             String mobileAccount, String password, String realName,
                             String mobilePhone, String email, String sex,
                             String headImgUrl, String orgName);

    /****冻结**/
    AosResult freeze(String ucasAccount);

    /****解冻**/
    AosResult unfreeze(String ucasAccount);

    /***修改邮箱**/
    AosResult upEmail(String ucasAccount,String emailAccount);

    /****修改手机号**/
    AosResult upPhone(String ucasAccount,String mobileAccount);

    /****修改密码**/
    AosResult upPassword(String ucasAccount,String password);

    /***删除**/
    AosResult delete(String ucasAccount);

    /***获取详情**/
    AosResult getUser(String ucasAccount);

    /****修改用户**/
    AosResult upInfo(String ucasAccount,
                      String accgUuid,
                     String realName,
                     String mobilePhone,
                     String email,
                     String sex,
                     String headImgUrl,
                     String orgName);
}
