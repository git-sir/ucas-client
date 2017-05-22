package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;

import java.util.List;

import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UcasResourceGroupMapper {
	
	List<UcasResourceGroup> queryResourceGroup(@Param("resGroupUuid") String resGroupUuid,@Param("clientId") String clientId);

    /**
     * 资源组管理页面分页查询
     * @param resGroupUuid
     * @param clientId
     * @param groupName
     * @param clientName
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasResourceGroupPojo> queryResourceGroupList(@Param("resGroupUuid") String resGroupUuid
            , @Param("clientId") String clientId, @Param("groupName") String groupName
            , @Param("clientName") String clientName, @Param("status") String status, PageRequest pageRequest);

    /**
     * 根据条件查询资源组
     * @param clientId 应用ID
     * @param groupName 资源组名称
     * @param excludeUuid 排除的uuid
     * @return
     */
    List<UcasResourceGroup> getResourceGroupByCondition(@Param("clientId") String clientId, @Param("groupName") String groupName, @Param("excludeUuid") String excludeUuid);

    /**
     * 根据主键查询资源组
     * @param resGroupUuid
     * @return
     */
    UcasResourceGroup getResourceGroupById(@Param("resGroupUuid") String resGroupUuid);

    int addResourceGroup(UcasResourceGroup ucasResourceGroup);

    int editResourceGroup(UcasResourceGroup ucasResourceGroup);

    int deleteResourceGroup(String resGroupUuid);
}
