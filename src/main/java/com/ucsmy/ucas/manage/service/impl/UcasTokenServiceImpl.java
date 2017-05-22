package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.manage.dao.UcasTokenMapper;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientToken;
import com.ucsmy.ucas.manage.service.UcasTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ucs_leijinming on 2017/4/27.
 */
@Service
public class UcasTokenServiceImpl implements UcasTokenService{

    @Autowired
    UcasTokenMapper ucasTokenMapper;

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasAccountGroup> query(String clientiId, int pageNum, int pageSize) {
        return ucasTokenMapper.query(clientiId,new PageRequest(pageNum,pageSize));
    }

    @Override
    @Logger(printSQL = true)
    public UcasAccountGroup getToken(String id) {
        return ucasTokenMapper.getToken(id);
    }

    @Override
    @Logger(printSQL = true)
    @Transactional(rollbackFor = Exception.class)
    public int editToken(UcasClientToken ucasClientToken) {
        return ucasTokenMapper.editToken(ucasClientToken);
    }
}
