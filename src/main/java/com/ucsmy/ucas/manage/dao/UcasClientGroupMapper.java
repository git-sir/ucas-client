package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用组mapper
 * Created by chenqilin on 2017/4/21.
 */
@Mapper
public interface UcasClientGroupMapper {

    /**
     * 分页查询
     * @param groupName 应用组名
     * @param isSso 是否单点登录
     * @param accountGroupName 账号组名
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasClientGroup> queryClientGroup(@Param("groupName") String groupName, @Param("isSso") String isSso
            , @Param("accountGroupName") String accountGroupName,PageRequest pageRequest);

    /**
     * 根据条件查询应用组
     * @param groupName 应用组名称
     * @return
     */
    List<UcasClientGroup> getClientGroupByCondition(@Param("groupName") String groupName, @Param("cligUuid") String cligUuid);

    /**
     * 根据主键查询
     * @param cligUuid
     * @return
     */
    UcasClientGroup getClientGroupById(@Param("cligUuid") String cligUuid);

    /**
     * 查询所有
     * @return
     */
    List<UcasClientGroup> getAllClientGroup();

    int addClientGroup(UcasClientGroup ucasClientGroup);

    int updateClientGroup(UcasClientGroup ucasClientGroup);

    int deleteClientGroup(@Param("cligUuid") String cligUuid);

    UcasPageInfo<UcasClientGroup> queryClientGroupInfoByCligUuid(@Param("list") List list, @Param("groupName") String groupName, @Param("pageRequest")PageRequest pageRequest);

}
