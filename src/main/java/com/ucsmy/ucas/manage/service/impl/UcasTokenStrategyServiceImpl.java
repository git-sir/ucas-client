package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.BeanUtil;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.dao.UcasTokenStrategyMapper;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import com.ucsmy.ucas.manage.ext.TokenStrategyPojo;
import com.ucsmy.ucas.manage.service.UcasTokenStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenqilin on 2017/5/10.
 */
@Service
public class UcasTokenStrategyServiceImpl implements UcasTokenStrategyService {

    private static final String CLIENT_ID_EMPTY = "应用Id不能为空";
    private static final int DEFAULT_MAX_TIMES = 1;
    private static final String EXPIRY_DATE_EMPTY = "Token有效期不能为空";
    private static final String REFRESH_EXPIRY_DATE_EMPTY = "RefreshToken有效期不能为空";
    private static final String HAS_INUSE_DATA = "该应用下已有在用策略";

    @Autowired
    private UcasTokenStrategyMapper ucasTokenStrategyMapper;

    @Override
    @Logger(printSQL = true)
    public UcasPageInfo<TokenStrategyPojo> queryTokenStrategyList(String clientName, String clientId, String status, int pageNum, int pageSize) {
        return ucasTokenStrategyMapper.queryTokenStrategyList(clientName, clientId, status, new PageRequest(pageNum, pageSize));
    }

    @Override
    @Logger(printSQL = true)
    public List<UcasTokenStrategy> queryTokenStrategyListByClientId(String clientId, String uuid) {
        if (StringUtils.isEmpty(clientId)) {
            return new ArrayList<>();
        }
        List<UcasTokenStrategy> list = ucasTokenStrategyMapper.queryTokenStrategyListByClientId(clientId, uuid, CommStatusEnum.SYS_INUSE.getValue());
        autoFixedDuplicateTokenStrategy(list);
        return list;
    }

    @Override
    @Logger(printSQL = true)
    public UcasTokenStrategy queryTokenStrategyByUuid(String uuid) {
        return ucasTokenStrategyMapper.queryTokenStrategyByUuid(uuid);
    }

    @Override
    @Logger(operationName = "添加token策略", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    @Transactional(rollbackFor = Exception.class)
    public int addTokenStrategy(UcasTokenStrategy ucasTokenStrategy) {
        ucasTokenStrategy.setUuid(UUIDGenerator.generate());
        ucasTokenStrategy.setStatus(Integer.parseInt(CommStatusEnum.SYS_INUSE.getValue()));
        ucasTokenStrategy.setCreateDate(new Date());
        return ucasTokenStrategyMapper.addTokenStrategy(ucasTokenStrategy);
    }

    @Override
    @Logger(operationName = "更新token策略", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    @Transactional(rollbackFor = Exception.class)
    public int updateTokenStrategy(UcasTokenStrategy ucasTokenStrategy) {
        UcasTokenStrategy oldTokenStrategy = queryTokenStrategyByUuid(ucasTokenStrategy.getUuid());
        if (oldTokenStrategy == null) {
            return 0;
        }
        BeanUtil.copyPropertiesIgnoreNull(ucasTokenStrategy, oldTokenStrategy);
        return ucasTokenStrategyMapper.updateTokenStrategy(oldTokenStrategy);
    }

    @Override
    @Logger(operationName = "删除token策略", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    @Transactional(rollbackFor = Exception.class)
    public int deleteTokenStrategy(String uuid) {
        UcasTokenStrategy oldTokenStrategy = queryTokenStrategyByUuid(uuid);
        if (oldTokenStrategy == null) {
            return 0;
        }
        oldTokenStrategy.setStatus(Integer.parseInt(CommStatusEnum.SYS_UNUSE.getValue()));
        return ucasTokenStrategyMapper.updateTokenStrategy(oldTokenStrategy);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult validateTokenStrategy(UcasTokenStrategy ucasTokenStrategy) {
        if (StringUtils.isEmpty(ucasTokenStrategy.getClientId())) {
            return AosResult.retFailureMsg(CLIENT_ID_EMPTY);
        }
        if (StringUtils.isEmpty(ucasTokenStrategy.getMaxTimes())) {
            ucasTokenStrategy.setMaxTimes(DEFAULT_MAX_TIMES);
        }
        if (StringUtils.isEmpty(ucasTokenStrategy.getExpiryDate())) {
            return AosResult.retFailureMsg(EXPIRY_DATE_EMPTY);
        }
        if (StringUtils.isEmpty(ucasTokenStrategy.getRefreshExpiryDate())) {
            return AosResult.retFailureMsg(REFRESH_EXPIRY_DATE_EMPTY);
        }
        return AosResult.retSuccessMsg("success");
    }

    @Override
    @Logger(printSQL = true)
    public AosResult checkTokenStrategyExist(UcasTokenStrategy ucasTokenStrategy) {
        List<UcasTokenStrategy> inUseTokenStrategyList = queryTokenStrategyListByClientId(ucasTokenStrategy.getClientId(), ucasTokenStrategy.getUuid());
        if (inUseTokenStrategyList != null && !inUseTokenStrategyList.isEmpty()) {
            return AosResult.retFailureMsg(HAS_INUSE_DATA);
        }
        return AosResult.retSuccessMsg("success");
    }

    /**
     * 将列表里除第一个以外的数据状态设置为失效，用作数据修正
     * @param list
     */
    @Logger(printSQL = true)
    private void autoFixedDuplicateTokenStrategy(List<UcasTokenStrategy> list) {
        for (int i = 1; i < list.size(); i++) {
            UcasTokenStrategy ucasTokenStrategy = list.get(i);
            ucasTokenStrategy.setStatus(Integer.parseInt(CommStatusEnum.SYS_UNUSE.getValue()));
            updateTokenStrategy(ucasTokenStrategy);
        }
    }
}
