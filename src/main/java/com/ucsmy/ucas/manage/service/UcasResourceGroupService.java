package com.ucsmy.ucas.manage.service;

import java.util.List;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;

public interface UcasResourceGroupService {

	List<UcasResourceGroup> queryAllResourceGroup();

    /**
     * 资源组管理页面分页查询
     * @param clientId
     * @param groupName
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UcasResourceGroupPojo> queryResourceGroupByClient(String clientId, String groupName, String clientName, int pageNum, int pageSize);

    /**
     * 添加资源组
     * @param ucasResourceGroup
     * @return
     */
    AosResult addResourceGroup(UcasResourceGroup ucasResourceGroup);

    /**
     * 更新资源组
     * @param ucasResourceGroup
     * @return
     */
    AosResult editResourceGroup(UcasResourceGroup ucasResourceGroup);

    /**
     * 删除资源组
     * @param resGroupUuid
     * @return
     */
    AosResult deleteResourceGroup(String resGroupUuid);

    /**
     * 校验数据：
     * 1. 字段非空、长度；
     * 2. 资源组名称在同个应用下是否重名
     * @param ucasResourceGroup
     * @return
     */
    AosResult validateResourceGroup(UcasResourceGroup ucasResourceGroup);

    /**
     * 根据主键查询
     * @param resGroupUuid
     * @return
     */
    UcasResourceGroup getResourceGroupById(String resGroupUuid);
}


