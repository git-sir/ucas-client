package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.ext.UserRelClientInfoPojo;
import com.ucsmy.ucas.manage.ext.UserRelManageListPojo;

/**
 * 账号应用授权service
 * Created by chenqilin on 2017/4/25.
 */
public interface UcasUserClientRelService {


    /**
     * 查询账户授权应用列表
     * @param clientName 查询条件，应用名称
     * @param accUuid 账号UUID
     * @param pageNum 页号
     * @param pageSize 页大小
     * @return
     */
    UcasPageInfo<UserRelClientInfoPojo> queryUserRelClientList(String clientName, String accUuid, String openId, String status, int pageNum, int pageSize);

    /**
     * 取消应用授权，同时取消对应的资源授权
     * @param openId
     * @return
     */
    int deleteUserRelClient(String openId);
}
