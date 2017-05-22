package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasAcccliGroupRel;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasUserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@org.apache.ibatis.annotations.Mapper
public interface UcasAccountGroupMapper {


    UcasAccountGroup queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);

    UcasAccountGroup getConfig(String id);

    /**
     * 账号组页面查询
     * @param groupName 账号组名称
     * @param clientGroupName 授权的应用组名称
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasAccountGroup> query(@Param("groupName") String groupName, @Param("clientGroupName") String clientGroupName, PageRequest pageRequest);

    int deleteAccountGroup(String id);

    int editAccountGroup(UcasAccountGroup ucasAccountGroup);

    int addAccountGroup(UcasAccountGroup ucasAccountGroup);

    /****获取所有的帐号组**/
    List<UcasAccountGroup> getAllAccountGroup();

    List<String> getUnBindID(String accgUuid);

    List<String> getBindID(String accgUuid);

    void deleteClientGroupRel(@Param("clientClentIdList") String[] clientClentIdList,@Param("accgUuid") String accgUuid);

    void addClientGroupRel(UcasAcccliGroupRel ucasAcccliGroupRel);

    /**
     * 分页查询，根据应用组id、应用组名称查询账号组
     * @param cligUuid 应用组id
     * @param groupName 账号组名称
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasAccountGroup> queryAccountGroupByCligUuid(@Param("cligUuid") String cligUuid, @Param("groupName") String groupName, PageRequest pageRequest);

    UcasUserAccount getAccountInfoByAccountId(String accountId, String value);

    void updateAccountInfoByaccountId(@Param("accgUuid")String accgUuid,@Param("accountId") String accountId);

    int queryAccountCount(String accgUuid);

    int queryClientRelCount(String accgUuid);
}
