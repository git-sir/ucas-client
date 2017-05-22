package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_xiaokailin on 2017/4/24.
 */
@Mapper
public interface UcasClientInfoMapper {
    /**
     *
     * @param name
     * @param clientGroupName
     * @param grantType
     * @param status
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasClientInfo> queryUcasClientInfo(@Param("name") String name, @Param("clientId")String clientId, @Param("clientGroupName")String clientGroupName, @Param("status") String status, @Param("grantType") String grantType, PageRequest pageRequest);

    /**
     * 根据应用组id查询应用个数
     * @param cligUuid 应用组id
     * @param status 应用状态
     * @return
     */
    int countClientInfoByCligUuid(@Param("cligUuid") String cligUuid, @Param("status") String status);

    /**
     * 根据应用组id查询应用分页列表
     * @param cligUuid 应用组id
     * @param status 应用状态
     * @param clientName 应用简称
     * @param grantType 授权类型
     * @return
     */
    UcasPageInfo<UcasClientInfo> queryClientInfoByCligUuid(@Param("cligUuid") String cligUuid
            , @Param("status") String status, @Param("clientName") String clientName
            , @Param("grantType") String grantType, PageRequest pageRequest);

    /**
     * 根据应用名称查询该应用在指定的状态下是否已存在
     * @param clientName 应用名称
     * @return 若存在，返回1；若不存在，返回null
     */
    Integer checkClientNameExist(@Param("clientName")String clientName,@Param("status")String status);

    /**
     * 添加
     * @param ucasClientInfo
     * @return
     */
    int addClientInfo(UcasClientInfo ucasClientInfo);

    /**
     * 更新
     * @param ucasClientInfo
     * @return
     */
    int updateClientInfo(UcasClientInfo ucasClientInfo);

    /**
     * 删除
     * @param clientId
     * @return
     */
    int deleteClientInfo(@Param("clientId") String clientId);

    /**
     * 只根据主键查询
     * @param clientId
     * @return
     */
    UcasClientInfo queryByClientId(String clientId);
	/**
     * 根据主键查询
     * @param clientId
     * @return
     */
    UcasClientInfo getClientInfoByClientId(@Param("clientId") String clientId, @Param("status") String status);
}
