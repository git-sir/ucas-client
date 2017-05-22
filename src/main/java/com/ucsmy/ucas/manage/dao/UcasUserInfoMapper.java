package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasUserInfo;
import com.ucsmy.ucas.manage.ext.UserAccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_mojiazhou on 2017/4/25.
 */
@Mapper
public interface UcasUserInfoMapper {

    /**
     *
     * @param name
     * @param status 过滤的状态
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UserAccountInfo> getUserInfoByName( @Param("userAccountInfo")UserAccountInfo userAccountInfo, @Param("status") String status,
                                                    PageRequest pageRequest);
}
