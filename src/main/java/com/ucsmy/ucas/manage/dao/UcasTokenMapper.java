package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UcasTokenMapper {
    PageInfo<UcasAccountGroup> query(String clientiId, PageRequest pageRequest);

    UcasAccountGroup getToken(String id);

    int editToken(UcasClientToken ucasClientToken);
}
