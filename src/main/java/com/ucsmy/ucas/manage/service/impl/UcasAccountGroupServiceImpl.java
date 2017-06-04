package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.dao.ManageConfigMapper;
import com.ucsmy.ucas.manage.dao.UcasAccountGroupMapper;
import com.ucsmy.ucas.manage.dao.UcasClientGroupMapper;
import com.ucsmy.ucas.manage.dao.UcasUserAccountMapper;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.entity.UcasAcccliGroupRel;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.ext.UserRelManageListPojo;
import com.ucsmy.ucas.manage.service.UcasAccountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@Service
public class UcasAccountGroupServiceImpl implements UcasAccountGroupService {
    private static final String MANAGER_CLIENT_GROUP_BIND = "bind";
    private static final String MANAGER_CLIENT_GROUP_UNBIND = "unbind";

    private static final String  DEFAULT_ACCOUNT_GROUP_CONFIG = "DEFAULT_ACCOUNT_GROUP";

    private static final String DEFAULT_CLIENT_GROUP_CONFIG = "DEFAULT_CLIENT_GROUP";

    private static final String DEFAULT_ACCOUNT_GROUP_NAME = "默认用户组";
    private static final String  DEFAULT_ACCOUNT_GROUP_ID = "0";
    private static final String DEFAULT_CLIENT_GROUP_ISSSO = "0";


    @Autowired
    private UcasAccountGroupMapper ucasAccountGroupMapper;
    @Autowired
    private ManageConfigMapper manageConfigMapper;
    @Autowired
    private UcasClientGroupMapper ucasClientGroupMapper;
    @Autowired
    private UcasUserAccountMapper ucasUserAccountMapper;

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasAccountGroup> query(String paramKey, String clientGroupName, int page, int size) {
        return ucasAccountGroupMapper.query(paramKey, clientGroupName, new PageRequest(page,size));
    }
    //通过ID获取用户群组信息
    @Override
    @Logger(printSQL = true)
    public UcasAccountGroup getAccountGroup(String id) {
        return ucasAccountGroupMapper.getConfig(id);
    }



    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientGroup> queryClientGroupInfoByCligUuid(List listId, String groupName,int pageNum, int pageSize) {

        return ucasClientGroupMapper.queryClientGroupInfoByCligUuid(listId, groupName, new PageRequest(pageNum, pageSize));


    }

    @Override
    @Logger(printSQL = true)
    public List<String> getUnBindID(String accgUuid) {
        List<String> listun = ucasAccountGroupMapper.getUnBindID(accgUuid);
        List<String> list = ucasAccountGroupMapper.getBindID(accgUuid);
        listun.removeAll(list);
        return listun;
    }

    @Override
    @Logger(printSQL = true)
    public List<String> getBindID(String accgUuid) {

        return ucasAccountGroupMapper.getBindID(accgUuid);
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientGroup> getbindStatusClientGroup(String status, String accgUuid, String groupName, int pageNum, int pageSize ) {
        List<String> listId;

        if (StringUtils.isEmpty(status)) {
            listId = this.getBindID(accgUuid);
        }else {
            listId = this.getUnBindID(accgUuid);
        }

        return  this.queryClientGroupInfoByCligUuid(listId, groupName, pageNum, pageSize);
    }

    @Override
    @Logger(printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public AosResult manageClientGroup(String accgUuid, String clientGroupIds,String type) {
        String[] clientClentIdList = clientGroupIds.split(",");
        if (type.equals(MANAGER_CLIENT_GROUP_UNBIND)) {
            // 解绑
            ucasAccountGroupMapper.deleteClientGroupRel(clientClentIdList,accgUuid);
        }else if (type.equals(MANAGER_CLIENT_GROUP_BIND)) {
            // 绑定
                for (String clientId : clientClentIdList) {
                    UcasAcccliGroupRel ucasAcccliGroupRel = new UcasAcccliGroupRel();
                    ucasAcccliGroupRel.setAccgUuid(accgUuid);
                    ucasAcccliGroupRel.setUuid(UUIDGenerator.generate());
                    ucasAcccliGroupRel.setCligUuid(clientId);
                    ucasAccountGroupMapper.addClientGroupRel(ucasAcccliGroupRel);
                }
        }else {
            AosResult.retFailureMsg("操作类型未指定,操作失败");
        }

        return AosResult.retSuccessMsg("操作成功");
    }




    @Override
    @Logger(printSQL = true)
    public PageInfo<UserRelManageListPojo> queryAccountInfoByAccgUuid(String accgUuid,String ucasAccount,
                                                                      String emailAccount,String mobileAccount, String realName, int pageNum, int pageSize) {
        return ucasUserAccountMapper.queryAccountInfoByAccgUuid(accgUuid,ucasAccount,emailAccount,mobileAccount, realName, CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
    }

    @Override
    @Logger(printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public int updateAccountInfoAccgUuid(String accgUuid, String accountIds1) {
        String[] accountIdList = accountIds1.split(",");
        for (String accountId : accountIdList) {
            ucasAccountGroupMapper.updateAccountInfoByaccountId(accgUuid,accountId);
        }
        return 0;
    }

    @Override
    @Logger(printSQL = true)
    public UcasAccountGroup getDefaultAccountGroup() {
        ManageConfig defaultAccountGroupConfig = manageConfigMapper.queryByName(DEFAULT_ACCOUNT_GROUP_CONFIG);
        UcasAccountGroup defAccountGroup = null;
        if (defaultAccountGroupConfig != null) {
            String defAccountGroupId = defaultAccountGroupConfig.getParamValue();
            defAccountGroup = this.getAccountGroup(defAccountGroupId);
        } else {
            // 添加配置
            defaultAccountGroupConfig = new ManageConfig();
            defaultAccountGroupConfig.setParamDesc("默认用户组的主键");
            defaultAccountGroupConfig.setParamKey(DEFAULT_ACCOUNT_GROUP_CONFIG);
        }
        if (defAccountGroup == null) {
            // 添加默认应用组
            defAccountGroup = new UcasAccountGroup();
            defAccountGroup.setGroupName(DEFAULT_ACCOUNT_GROUP_NAME);
            defAccountGroup.setDescRibe(DEFAULT_ACCOUNT_GROUP_NAME);
            defAccountGroup.setAccgUuid(DEFAULT_ACCOUNT_GROUP_ID);
            this.addAccountGroup(defAccountGroup);
            defaultAccountGroupConfig.setParamValue(defAccountGroup.getAccgUuid());
            if (defaultAccountGroupConfig.getId() == null) {
                defaultAccountGroupConfig.setId(UUIDGenerator.generate());
                manageConfigMapper.addConfig(defaultAccountGroupConfig);
            } else {
                manageConfigMapper.editConfig(defaultAccountGroupConfig);
            }
        }
        return defAccountGroup;
    }

    @Override
    @Logger(printSQL = true)
    public List<UcasAccountGroup> getAllAccountGroup() {
        return ucasAccountGroupMapper.getAllAccountGroup();
    }

    @Override
    @Logger(printSQL = true)
    public int queryClientRelCount(String accgUuid) {
        return ucasAccountGroupMapper.queryClientRelCount(accgUuid);
    }

    @Override
    @Logger(printSQL = true)
    public int queryAccountCount(String accgUuid) {
        return ucasAccountGroupMapper.queryAccountCount(accgUuid);
    }


    @Override
    @Logger(operationName = "添加账号组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public int addAccountGroup(UcasAccountGroup ucasAccountGroup) {
        return ucasAccountGroupMapper.addAccountGroup(ucasAccountGroup);
    }

    @Override
    @Logger(operationName = "更新账号组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public int editAccountGroup(UcasAccountGroup ucasAccountGroup) {
        return ucasAccountGroupMapper.editAccountGroup(ucasAccountGroup);
    }

    @Override
    @Logger(operationName = "删除账号组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public int deleteAccountGroup(String id) {
        return ucasAccountGroupMapper.deleteAccountGroup(id);
    }


    @Override
    @Logger(printSQL = true)
    public UcasAccountGroup queryByName(String paramKey) {
        return ucasAccountGroupMapper.queryByName(paramKey);
    }

    @Override
    @Logger(printSQL = true)
    public int isKeyExist(Map<String, Object> map) {
        return ucasAccountGroupMapper.isKeyExist(map);
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasAccountGroup> queryAccountGroupByClientGroup(String cligUuid, String groupName, int pageNum, int pageSize) {
        return ucasAccountGroupMapper.queryAccountGroupByCligUuid(cligUuid, groupName, new PageRequest(pageNum, pageSize));
    }
}
