package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasCallTicket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UcasTicketMapper {
    PageInfo<UcasAccountGroup> query(String clientiId, PageRequest pageRequest);

    int editTicket(UcasCallTicket ucasCallTicket);

    UcasAccountGroup getTicket(String id);
}
