package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageOrganization;
import com.ucsmy.ucas.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.ucas.manage.ext.UcasClientUserProfileWithOrganization;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
public interface ManageOrganizationService {

     List<ManageOrganization> getOrganizationList();

     ManageOrganization getOrganizationById(String id);

     int updateOrganization(ManageOrganization manageOrganization);

     int insertOrganization(ManageOrganization manageOrganization);

     int deleteOrganization(String id);

     int countChildren(String id);

     int deleteBatchByUserIds(Map<String,Object> map);

     int deleteBatch(Map<String,Object> map);

     int insertBatch( List<UcasClientOrganizationUser> list);

     PageInfo<UcasClientUserProfileWithOrganization> queryUserWithOrganization(String id, String name, String account, int page, int size);

     PageInfo<UcasClientUserProfileWithOrganization> queryUserWithoutOrganization(String id, String name, String account, int page, int size);

     List<ManageOrganization> queryOrganizationByCondition(ManageOrganization manageOrganization);
}
