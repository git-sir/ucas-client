package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.BeanUtil;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.dao.ManageConfigMapper;
import com.ucsmy.ucas.manage.dao.UcasClientGroupMapper;
import com.ucsmy.ucas.manage.dao.UcasClientInfoMapper;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.service.UcasClientGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenqilin on 2017/4/21.
 */
@Service
public class UcasClientGroupServiceImpl implements UcasClientGroupService {

    private static final String GROUP_NAME_EMPTY = "应用组名称不能为空";
    private static final String DESCRIBE_EMPTY = "应用组描述不能为空";
    private static final String ISSSO_EMPTY = "单点登录类型不能为空";
    private static final String GROUP_NAME_LENGTH_ILLEGAL = "应用组名称长度不能超过50";
    private static final String DESCRIBE_LENGTH_ILLEGAL = "应用组描述长度不能超过200";
    private static final String GROUP_NAME_DUPLICATE = "应用组名称已存在";

    private static final String HAS_CLIENTS = "有关联的应用存在，不能删除应用组";

    /**
     * 默认应用组配置名称
     */
    private static final String DEFAULT_CLIENT_GROUP_CONFIG = "DEFAULT_CLIENT_GROUP";

    /**
     * 默认应用组名称和描述
     */
    private static final String DEFAULT_CLIENT_GROUP_NAME = "默认应用组";

    /**
     * 默认应用组单点登录类型
     */
    private static final String DEFAULT_CLIENT_GROUP_ISSSO = "0";

    @Autowired
    private ManageConfigMapper manageConfigMapper;
    @Autowired
    private UcasClientGroupMapper ucasClientGroupMapper;
    @Autowired
    private UcasClientInfoMapper ucasClientInfoMapper;

    @Override
    @Logger(operationName = "应用组查询", printSQL = true)
    public UcasPageInfo<UcasClientGroup> queryClientGroup(String groupName, String isSso, String accountGroupName, int pageNum, int pageSize) {
        return ucasClientGroupMapper.queryClientGroup(groupName, isSso, accountGroupName, new PageRequest(pageNum, pageSize));
    }

    @Override
    @Logger(printSQL = true)
    public UcasClientGroup getClientGroupById(String id) {
        return ucasClientGroupMapper.getClientGroupById(id);
    }

    @Override
    @Logger(printSQL = true)
    public Map<String, Object> getAllClientGroup() {
        UcasClientGroup defClientGroup = this.getDefaultClientGroup();
        List<UcasClientGroup> list = ucasClientGroupMapper.getAllClientGroup();
        Map<String, Object> result = new HashMap<>();
        result.put("defClientGroup", defClientGroup);
        result.put("list", list);
        return result;
    }

    @Override
    @Logger(operationName = "应用组添加", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    @Transactional(rollbackFor = Exception.class)
    public AosResult addClientGroup(UcasClientGroup ucasClientGroup) {
        ucasClientGroup.setCligUuid(UUIDGenerator.generate());
        int result = ucasClientGroupMapper.addClientGroup(ucasClientGroup);
        if (result <= 0) {
            return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName = "应用组更新", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public AosResult updateClientGroup(UcasClientGroup ucasClientGroup) {
        UcasClientGroup oldClientGroup = this.getClientGroupById(ucasClientGroup.getCligUuid());
        if (oldClientGroup == null) {
            return AosResult.retFailureMsg(CommMessage.DATA_NOT_EXIST);
        }
        BeanUtil.copyPropertiesIgnoreNull(ucasClientGroup, oldClientGroup);
        int result = ucasClientGroupMapper.updateClientGroup(oldClientGroup);
        if (result <= 0) {
            return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName = "应用组删除", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    public AosResult deleteClientGroup(String cligUuid) {
        UcasClientGroup oldClientGroup = this.getClientGroupById(cligUuid);
        if (oldClientGroup == null) {
            return AosResult.retFailureMsg(CommMessage.DATA_NOT_EXIST);
        }
        // 检查关联的应用
        int clientCount = ucasClientInfoMapper.countClientInfoByCligUuid(cligUuid, CommStatusEnum.SYS_INUSE.getValue());
        if (clientCount != 0) {
            return AosResult.retFailureMsg(HAS_CLIENTS);
        }
        int resultCode = ucasClientGroupMapper.deleteClientGroup(cligUuid);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
    }

    @Override
    @Logger(operationName = "应用组数据校验", printSQL = true)
    public AosResult validateClientGroup(UcasClientGroup ucasClientGroup) {
        if (StringUtils.isEmpty(ucasClientGroup.getGroupName())) {
            return AosResult.retFailureMsg(GROUP_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(ucasClientGroup.getDescRibe())) {
            return AosResult.retFailureMsg(DESCRIBE_EMPTY);
        }
        if (ucasClientGroup.getGroupName().length() > 50) {
            return AosResult.retFailureMsg(GROUP_NAME_LENGTH_ILLEGAL);
        }
        if (ucasClientGroup.getDescRibe().length() > 200) {
            return AosResult.retFailureMsg(DESCRIBE_LENGTH_ILLEGAL);
        }
        if (StringUtils.isEmpty(ucasClientGroup.getIsSso())) {
            return AosResult.retFailureMsg(ISSSO_EMPTY);
        }
        // 应用组名称是否已经存在
        List<UcasClientGroup> existList = ucasClientGroupMapper.getClientGroupByCondition(ucasClientGroup.getGroupName(), ucasClientGroup.getCligUuid());
        if (existList != null && !existList.isEmpty()) {
            return AosResult.retFailureMsg(GROUP_NAME_DUPLICATE);
        }
        return AosResult.retSuccessMsg("success");
    }

    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName = "获取默认应用组", printSQL = true)
    public UcasClientGroup getDefaultClientGroup() {
        ManageConfig defaultClientGroupConfig = manageConfigMapper.queryByName(DEFAULT_CLIENT_GROUP_CONFIG);
        UcasClientGroup defClientGroup = null;
        if (defaultClientGroupConfig != null) {
            String defClientGroupId = defaultClientGroupConfig.getParamValue();
            defClientGroup = this.getClientGroupById(defClientGroupId);
        } else {
            // 添加配置
            defaultClientGroupConfig = new ManageConfig();
            defaultClientGroupConfig.setParamDesc(DEFAULT_CLIENT_GROUP_NAME);
            defaultClientGroupConfig.setParamKey(DEFAULT_CLIENT_GROUP_CONFIG);
        }
        if (defClientGroup == null) {
            // 添加默认应用组
            defClientGroup = new UcasClientGroup();
            defClientGroup.setGroupName(DEFAULT_CLIENT_GROUP_NAME);
            defClientGroup.setDescRibe(DEFAULT_CLIENT_GROUP_NAME);
            defClientGroup.setIsSso(DEFAULT_CLIENT_GROUP_ISSSO);
            this.addClientGroup(defClientGroup);
            defaultClientGroupConfig.setParamValue(defClientGroup.getCligUuid());
            if (defaultClientGroupConfig.getId() == null) {
                defaultClientGroupConfig.setId(UUIDGenerator.generate());
                manageConfigMapper.addConfig(defaultClientGroupConfig);
            } else {
                manageConfigMapper.editConfig(defaultClientGroupConfig);
            }
        }
        return defClientGroup;
    }

}
