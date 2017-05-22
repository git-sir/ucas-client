package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;

import java.util.List;

/**
 * Created by ucs_xiaokailin on 2017/4/24.
 */
public interface UcasClientInfoService {
    PageInfo<UcasClientInfo> queryUcasClientInfo(String name, String clientId, String clientGroupName, String status, String grantType, int pageNum, int pageSize);

    /**
     * 根据应用组id查询应用分页列表
     * @param cligUuid
     * @return
     */
    PageInfo<UcasClientInfo> queryClientInfoByCligUuid(String cligUuid, String clientName, String grantType, int pageNum, int pageSize);

    /**
     * 检查应用名称在状态为'可用'的应用中是否已存在
     * @param ucasClientInfo
     * @return
     */
	boolean checkClientNameExist(UcasClientInfo ucasClientInfo);
    int addClientInfo(UcasClientInfo ucasClientInfo);
    int updateClientInfo(UcasClientInfo ucasClientInfo);

    /**
     * 删除应用组:并不物理删除,只是逻辑删除
     * @param clientId
     * @return
     */
    int deleteClientInfo(String clientId);

    /**
     * 批量更新应用所属应用组
     * @param cligUuid 应用组id
     * @param clientIds 应用id，多个用逗号分隔
     * @return
     */
    int updateClientInfoCligUuid(String cligUuid, String clientIds);

    /**
     * 添加应用，根据需求添加自定义token策略
     * @param ucasClientInfo
     * @param ucasTokenStrategy
     * @param hasTokenStrategy 是否需要添加自定义token策略
     * @return
     * Created by chenqilin on 2017/5/10.
     */
    AosResult addClientInfoWithTokenStrategy(UcasClientInfo ucasClientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy);

    /**
     * 更新应用，根据需求添加自定义token策略
     * @param ucasClientInfo
     * @param ucasTokenStrategy
     * @param hasTokenStrategy 是否需要添加自定义token策略
     * @return
     */
    AosResult updateClientInfoWithTokenStrategy(UcasClientInfo ucasClientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy);

    List<String> queryAllGrantType();
}
