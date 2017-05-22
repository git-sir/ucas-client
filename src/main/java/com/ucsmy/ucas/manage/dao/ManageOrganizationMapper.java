package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageOrganization;
import com.ucsmy.ucas.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.ucas.manage.ext.UcasClientUserProfileWithOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@Mapper
public interface ManageOrganizationMapper {

    List<ManageOrganization> getOrganizationList();

    ManageOrganization getOrganizationById(String id);

    int updateOrganization(ManageOrganization manageOrganization);

    int insertOrganization(ManageOrganization manageOrganization);

    int deleteOrganization(String id);

    int countChildren(String id);

    int deleteBatchByUserIds(Map<String, Object> map);

    int insertBatch(List<UcasClientOrganizationUser> list);

    int deleteBatch(Map<String, Object> map);

    UcasPageInfo<UcasClientUserProfileWithOrganization> queryUserWithOrganization(@Param("id")String id, PageRequest pageRequest);

    UcasPageInfo<UcasClientUserProfileWithOrganization> queryUserWithoutOrganization(@Param("id")String id, PageRequest pageRequest);

}
