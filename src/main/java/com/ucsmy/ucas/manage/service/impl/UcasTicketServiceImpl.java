package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.manage.dao.UcasTicketMapper;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasCallTicket;
import com.ucsmy.ucas.manage.service.UcasTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ucs_leijinming on 2017/4/27.
 */

@Service
public class UcasTicketServiceImpl implements UcasTicketService {
    @Autowired
    UcasTicketMapper ucasTicketMapper;

    @Override
    @Logger(printSQL = true)
    public UcasAccountGroup getTicket(String id) {
        return ucasTicketMapper.getTicket(id);
    }

    @Override
    @Logger(printSQL = true)
    @Transactional(rollbackFor = Exception.class)
    public int editTicket(UcasCallTicket ucasCallTicket) {
        return ucasTicketMapper.editTicket(ucasCallTicket);
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasAccountGroup> query(String clientiId, int pageNum, int pageSize) {
        return ucasTicketMapper.query(clientiId,new PageRequest(pageNum,pageSize));
    }
}
