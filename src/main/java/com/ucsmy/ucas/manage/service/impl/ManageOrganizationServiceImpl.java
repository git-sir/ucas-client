package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.manage.dao.ManageOrganizationMapper;
import com.ucsmy.ucas.manage.entity.ManageOrganization;
import com.ucsmy.ucas.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.ucas.manage.ext.UcasClientUserProfileWithOrganization;
import com.ucsmy.ucas.manage.service.ManageOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@Service
public class ManageOrganizationServiceImpl implements ManageOrganizationService {
    @Autowired
    ManageOrganizationMapper manageOrganizationMapper;

    @Override
    @Logger(printSQL = true)
    public List<ManageOrganization> getOrganizationList() {
        return manageOrganizationMapper.getOrganizationList();
    }

    @Override
    @Logger(printSQL = true)
    public ManageOrganization getOrganizationById(String id) {
        return manageOrganizationMapper.getOrganizationById(id);
    }

    @Override
    @Logger(printSQL = true)
    public int updateOrganization(ManageOrganization manageOrganization) {
        return manageOrganizationMapper.updateOrganization(manageOrganization);
    }

    @Override
    @Logger(printSQL = true)
    public int insertOrganization(ManageOrganization manageOrganization) {
        return manageOrganizationMapper.insertOrganization(manageOrganization);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteOrganization(String id) {
        return manageOrganizationMapper.deleteOrganization(id);
    }

    @Override
    @Logger(printSQL = true)
    public int countChildren(String id) {
        return manageOrganizationMapper.countChildren(id);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteBatchByUserIds(Map<String, Object> map) {
        return manageOrganizationMapper.deleteBatchByUserIds(map);
    }

    @Override
    @Logger(printSQL = true)
    public int insertBatch(List<UcasClientOrganizationUser> list) {
        return manageOrganizationMapper.insertBatch(list);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteBatch(Map<String, Object> map) {
        return manageOrganizationMapper.deleteBatch(map);
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientUserProfileWithOrganization> queryUserWithOrganization(String id, int page, int size) {
        return manageOrganizationMapper.queryUserWithOrganization(id, new PageRequest(page,size));
    }

    @Override
    @Logger(printSQL = true)
    public PageInfo<UcasClientUserProfileWithOrganization> queryUserWithoutOrganization(String id, int page, int size) {
        return manageOrganizationMapper.queryUserWithoutOrganization(id, new PageRequest(page,size));
    }
}
