package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.ext.UserRelManageListPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户认证信息mapper
 * Created by chenqilin on 2017/4/25.
 */
@Mapper
public interface UcasUserAccountMapper {

    /**
     * 查询用户列表信息，账号授权管理列表展示
     * @param ucasAccount 查询条件，网金账号
     * @param status 过滤查询的状态
     * @param accgUuid 查询条件，帐号组UUID
     * @return
     */
    UcasPageInfo<UserRelManageListPojo> queryUserAccountForRelManage(@Param("ucasAccount") String ucasAccount
            , @Param("status")String status, @Param("accgUuid") String accgUuid, PageRequest pageRequest);

    UcasPageInfo<UserRelManageListPojo> queryAccountInfoByAccgUuid(@Param("accgUuid")String accgUuid,
                                                                   @Param("ucasAccount") String ucasAccount,
                                                                   @Param("emailAccount")  String emailAccount,
                                                                   @Param("mobileAccount")String mobileAccount,
                                                                   @Param("realName") String realName,
                                                                   @Param("status")String status, PageRequest pageRequest);
}
