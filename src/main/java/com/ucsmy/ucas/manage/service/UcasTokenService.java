package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientToken;

/**
 * Created by ucs_leijinming on 2017/4/26.
 */
public interface UcasTokenService {
    PageInfo<UcasAccountGroup> query(String clientiId, int pageNum, int pageSize);

    UcasAccountGroup getToken(String id);

    int editToken(UcasClientToken ucasClientToken);


}
