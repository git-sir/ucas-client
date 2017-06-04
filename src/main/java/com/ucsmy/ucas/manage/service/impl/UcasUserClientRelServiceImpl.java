package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.dao.UcasUserAccountMapper;
import com.ucsmy.ucas.manage.dao.UcasUserClientRelMapper;
import com.ucsmy.ucas.manage.dao.UcasUserResRelMapper;
import com.ucsmy.ucas.manage.ext.UserRelClientInfoPojo;
import com.ucsmy.ucas.manage.service.UcasUserClientRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenqilin on 2017/4/25.
 */
@Service
public class UcasUserClientRelServiceImpl implements UcasUserClientRelService {

    @Autowired
    private UcasUserClientRelMapper ucasUserClientRelMapper;

    @Autowired
    private UcasUserResRelMapper ucasUserResRelMapper;

    @Override
    @Logger(printSQL = true)
    public UcasPageInfo<UserRelClientInfoPojo> queryUserRelClientList(String clientName, String accUuid, String openId, String status, int pageNum, int pageSize) {
        return ucasUserClientRelMapper.queryUserRelClientInfo(clientName, accUuid, openId, status, new PageRequest(pageNum, pageSize));
    }

    @Override
    @Logger(operationName = "取消应用授权", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserRelClient(String openId) {
        int delResRel = ucasUserResRelMapper.deleteUserRelResource(openId);
        int delClientRel = ucasUserClientRelMapper.deleteUserRelClient(openId);
        if (delClientRel > 0 && delResRel > 0) {
            return Math.max(delClientRel, delResRel);
        } else {
            return -1;
        }
    }
}
