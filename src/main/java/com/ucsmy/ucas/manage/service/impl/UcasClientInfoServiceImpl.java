package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.manage.dao.ManageConfigMapper;
import com.ucsmy.ucas.manage.dao.UcasClientInfoMapper;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import com.ucsmy.ucas.manage.service.UcasClientInfoService;
import com.ucsmy.ucas.manage.service.UcasTokenStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucs_xiaokailin on 2017/4/24.
 */
@Service
public class UcasClientInfoServiceImpl implements UcasClientInfoService {

    private final String ADD_FAIL = "添加失败";
    private final String ADD_SUCCESS = "添加成功";

    private final String UPDATE_FAIL = "更新失败";
    private final String UPDATE_SUCCESS = "更新成功";

    /**
     * 应用clientid的长度
     */
    private static final int CLIENT_ID_LENGTH = 32;
    /**
     * 应用口令的长度
     */
    private static final int CLIENT_SECRET_LENGTH = 18;
    /**
     * 所有授权类型
     */
    private static final String GRANT_TYPE_CONFIG = "ALL_GRANT_TYPE";

    @Autowired
    private UcasTokenStrategyService ucasTokenStrategyService;

    @Autowired
    private UcasClientInfoMapper ucasClientInfoMapper;

    @Autowired
    private ManageConfigMapper manageConfigMapper;

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientInfo> queryUcasClientInfo(String name, String clientId, String clientGroupName, String status, String grantType, int pageNum, int pageSize) {
        return ucasClientInfoMapper.queryUcasClientInfo(name,clientId,clientGroupName,status,grantType,new PageRequest(pageNum,pageSize));
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientInfo> queryClientInfoByCligUuid(String cligUuid, String clientName, String grantType, int pageNum, int pageSize) {
        return ucasClientInfoMapper.queryClientInfoByCligUuid(cligUuid, CommStatusEnum.SYS_INUSE.getValue(), clientName, grantType, new PageRequest(pageNum, pageSize));
    }

    @Override
    @Logger(printSQL = true)
    @Transactional(rollbackFor = Exception.class)
    public int updateClientInfoCligUuid(String cligUuid, String clientIds) {
        String[] clientIdList = clientIds.split(",");
        for (String clientId : clientIdList) {
            UcasClientInfo clientInfo = ucasClientInfoMapper.getClientInfoByClientId(clientId, CommStatusEnum.SYS_INUSE.getValue());
            if (clientInfo == null) {
                continue;
            }
            clientInfo.setCligUuid(cligUuid);
            ucasClientInfoMapper.updateClientInfo(clientInfo);
        }
        return 0;
    }

    @Override
    @Logger(printSQL = true)
    public boolean checkClientNameExist(UcasClientInfo ucasClientInfo) {
        String clientName = ucasClientInfo.getClientName();
        if(StringUtils.isEmpty(ucasClientInfo.getClientId())){  //clientId为空,属于添加新记录的情形
            return isClientNameExist(clientName);
        }else{      //clientId不为空,属于更新记录的情形

            UcasClientInfo oldClientInfo = ucasClientInfoMapper.getClientInfoByClientId(
                    ucasClientInfo.getClientId(),CommStatusEnum.SYS_INUSE.getValue());
            if(!ucasClientInfo.getClientName().equals(oldClientInfo.getClientName())){
                //应用名称被改变,判断新的应用名称在数据库中是否已存在
                return isClientNameExist(clientName);
            }
            //应用名称未被改变
            return false;
        }
    }

    private boolean isClientNameExist(String clientName){
        Integer count = ucasClientInfoMapper.checkClientNameExist(clientName,CommStatusEnum.SYS_INUSE.getValue());
        if(count == null){  //数据库中不存在
            return false;
        }
        return true;
    }
    @Override
    @Logger(printSQL = true)
    public int addClientInfo(UcasClientInfo ucasClientInfo) {
        ucasClientInfo.setClientId(UUIDGenerator.generate(CLIENT_ID_LENGTH));
        ucasClientInfo.setClientSecret(UUIDGenerator.generateFromEnd(CLIENT_SECRET_LENGTH));
        ucasClientInfo.setStatus(CommStatusEnum.SYS_INUSE.getValue());
        ucasClientInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
        ucasClientInfo.setModifyDate(ucasClientInfo.getCreateDate());
        return ucasClientInfoMapper.addClientInfo(ucasClientInfo);
    }

    @Override
    @Logger(printSQL = true)
    public int updateClientInfo(UcasClientInfo ucasClientInfo) {
        ucasClientInfo.setModifyDate(new Timestamp(System.currentTimeMillis()));
        return ucasClientInfoMapper.updateClientInfo(ucasClientInfo);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteClientInfo(String clientId) {
        UcasClientInfo ucasClientInfo = ucasClientInfoMapper.queryByClientId(clientId);
        ucasClientInfo.setStatus(CommStatusEnum.SYS_UNUSE.getValue());
        return updateClientInfo(ucasClientInfo);
    }

    @Override
    @Logger(printSQL = true)
    @Transactional(rollbackFor = Exception.class)
    public AosResult addClientInfoWithTokenStrategy(UcasClientInfo ucasClientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy) {
        int result = addClientInfo(ucasClientInfo);
        if (result > 0) {
            if (hasTokenStrategy) {
                ucasTokenStrategy.setClientId(ucasClientInfo.getClientId());
                AosResult validateTokenStrategy = ucasTokenStrategyService.validateTokenStrategy(ucasTokenStrategy);
                if (!validateTokenStrategy.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))) {
                    return validateTokenStrategy;
                }
                ucasTokenStrategyService.addTokenStrategy(ucasTokenStrategy);
            }
            return AosResult.retSuccessMsg(ADD_SUCCESS);
        }
        return AosResult.retFailureMsg(ADD_FAIL);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult updateClientInfoWithTokenStrategy(UcasClientInfo ucasClientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy) {
        int result = updateClientInfo(ucasClientInfo);
        if (result > 0) {
            if (!hasTokenStrategy && StringUtils.isNotEmpty(ucasTokenStrategy.getUuid())) {
                // 更新为失效状态
                ucasTokenStrategy.setStatus(Integer.parseInt(CommStatusEnum.SYS_UNUSE.getValue()));
                ucasTokenStrategyService.updateTokenStrategy(ucasTokenStrategy);
            } else if (hasTokenStrategy) {
                AosResult validateTokenStrategy = ucasTokenStrategyService.validateTokenStrategy(ucasTokenStrategy);
                if (!validateTokenStrategy.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))) {
                    return validateTokenStrategy;
                }
                AosResult check = ucasTokenStrategyService.checkTokenStrategyExist(ucasTokenStrategy);
                if (check.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))) {
                    // 状态设置为正常
                    ucasTokenStrategy.setStatus(Integer.parseInt(CommStatusEnum.SYS_INUSE.getValue()));
                    if (StringUtils.isNotEmpty(ucasTokenStrategy.getUuid())) {
                        ucasTokenStrategyService.updateTokenStrategy(ucasTokenStrategy);
                    } else {
                        ucasTokenStrategyService.addTokenStrategy(ucasTokenStrategy);
                    }
                }
            }
            return AosResult.retSuccessMsg(UPDATE_SUCCESS);
        }
        return AosResult.retFailureMsg(UPDATE_FAIL);
    }

    @Override
    @Logger(printSQL = true)
    public List<String> queryAllGrantType() {
        ManageConfig grantTypeConfig = manageConfigMapper.queryByName(GRANT_TYPE_CONFIG);
        String defaultValue = "authorization_code,password,client_credentials,proxy_authorization_code";
        if(grantTypeConfig == null){
            grantTypeConfig = new ManageConfig();
            grantTypeConfig.setId(UUIDGenerator.generate(CLIENT_ID_LENGTH));
            grantTypeConfig.setParamKey(GRANT_TYPE_CONFIG);
            grantTypeConfig.setParamValue(defaultValue);
            grantTypeConfig.setParamDesc("统一认证的所有授权类型,若有多个则用\",\"分开");
            manageConfigMapper.addConfig(grantTypeConfig);
        }
        String value = grantTypeConfig.getParamValue();
        String[] arr = value.split(",");
        List<String> grantTypeList = new ArrayList<String>();
        for(String gt :  arr){
            grantTypeList.add(gt);
        }
        return grantTypeList;
    }
}
