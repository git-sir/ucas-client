package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;

import java.util.List;
import java.util.Map;

/**
 * 应用组service
 * Created by chenqilin on 2017/4/21.
 */
public interface UcasClientGroupService {

    /**
     * 查询应用组列表
     *
     * @param groupName 应用组名称，非必传参数
     * @param isSso 是否单点登录，非必传参数
     * @param accountGroupName 账号组名称，非必传参数
     * @param pageNum 第几页，必传参数
     * @param pageSize 每页大小，必传参数
     * @return
     */
    UcasPageInfo<UcasClientGroup> queryClientGroup(String groupName, String isSso, String accountGroupName, int pageNum, int pageSize);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    UcasClientGroup getClientGroupById(String id);

    /**
     * 查询所有
     * @return defClientGroup 默认应用组实体; list 查询结果列表
     */
    Map<String, Object> getAllClientGroup();

    /**
     * 添加，校验入参不在此方法里进行
     *
     * @param ucasClientGroup
     * @return
     */
    AosResult addClientGroup(UcasClientGroup ucasClientGroup);

    /**
     * 更新，校验入参不在此方法里进行
     *
     * @param ucasClientGroup
     * @return
     */
    AosResult updateClientGroup(UcasClientGroup ucasClientGroup);

    /**
     * 删除应用组，删除前检查应用组下是否有其他应用
     *
     * @param cligUuid
     * @return
     */
    AosResult deleteClientGroup(String cligUuid);

    /**
     * 校验数据：
     * 1. 非空和长度判断
     * 2. 应用组名称是否已经存在
     * @param ucasClientGroup
     * @return
     */
    AosResult validateClientGroup(UcasClientGroup ucasClientGroup);

    /**
     * 获取默认应用组，如果没有相关配置时自动补充信息
     * @return
     */
    UcasClientGroup getDefaultClientGroup();
}
