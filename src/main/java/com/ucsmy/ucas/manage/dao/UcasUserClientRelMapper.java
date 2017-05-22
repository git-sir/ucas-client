package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.ext.UserRelClientInfoPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户授权表mapper
 * Created by chenqilin on 2017/4/25.
 */
@Mapper
public interface UcasUserClientRelMapper {

    /**
     * 分页查询用户授权的应用列表
     *
     * @param clientName 查询条件，应用简称
     * @return
     */
    UcasPageInfo<UserRelClientInfoPojo> queryUserRelClientInfo(@Param("clientName") String clientName, @Param("accUuid") String accUuid, @Param("openId") String openId, @Param("status") String status, PageRequest pageRequest);

    /**
     * 取消openId对应的应用授权
     * @param openId
     * @return
     */
    int deleteUserRelClient(@Param("openId") String openId);
}
