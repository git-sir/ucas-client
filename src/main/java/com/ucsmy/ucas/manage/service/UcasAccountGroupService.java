package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.ext.UserRelManageListPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
public interface UcasAccountGroupService {


    int addAccountGroup(UcasAccountGroup manageConfig);

    int editAccountGroup(UcasAccountGroup manageConfig);

    int deleteAccountGroup(String id);

    UcasAccountGroup queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);

    /**
     * 账号组页面查询
     * @param groupName 账号组名称
     * @param clientGroupName 授权的应用组名称
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UcasAccountGroup> query(String groupName, String clientGroupName, int pageNum, int pageSize);

    UcasAccountGroup getAccountGroup(String id);

    PageInfo<UcasClientGroup> queryClientGroupInfoByCligUuid(List listId, String groupName, int pageNum, int pageSize);

    List<String> getUnBindID(String accgUuid);

    List<String> getBindID(String accgUuid);

    PageInfo<UcasClientGroup> getbindStatusClientGroup(String status, String accgUuid, String groupName, int pageNum, int pageSize);

    AosResult manageClientGroup(String accgUuid, String clientGroupIds,String type);


    PageInfo<UserRelManageListPojo> queryAccountInfoByAccgUuid(String accgUuid,String ucasAccount,
                                                               String emailAccount,String mobileAccount, String realName,int pageNum, int pageSize);

    int updateAccountInfoAccgUuid(String accountIds, String accountIds1);

    UcasAccountGroup getDefaultAccountGroup();

    List<UcasAccountGroup> getAllAccountGroup();

    int queryClientRelCount(String accgUuid);

    int queryAccountCount(String accgUuid);

    /**
     * 根据应用组id、应用组名称查询关联的账号组列表
     * @param cligUuid 应用组id
     * @param groupName 应用组名称
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UcasAccountGroup> queryAccountGroupByClientGroup(String cligUuid, String groupName, int pageNum, int pageSize);
}


