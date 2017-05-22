package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasCallTicket;

/**
 * Created by ucs_leijinming on 2017/4/26.
 */
public interface UcasTicketService {
    UcasAccountGroup getTicket(String id);

    int editTicket(UcasCallTicket ucasCallTicket);

    PageInfo<UcasAccountGroup> query(String clientiId, int pageNum, int pageSize);

}
