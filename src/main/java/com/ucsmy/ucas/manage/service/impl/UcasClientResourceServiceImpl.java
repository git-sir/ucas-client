package com.ucsmy.ucas.manage.service.impl;

import java.util.Date;
import com.ucsmy.commons.utils.CommStatusEnum;

import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.manage.dao.UcasClientResourceMapper;
import com.ucsmy.ucas.manage.dao.UcasUserResRelMapper;
import com.ucsmy.ucas.manage.entity.UcasClientResource;
import com.ucsmy.ucas.manage.ext.UcasClientResourcePojo;
import com.ucsmy.ucas.manage.service.UcasClientResourceService;

@Service
public class UcasClientResourceServiceImpl implements UcasClientResourceService {

	@Autowired
	UcasClientResourceMapper ucasClientResourceMapper;

	@Autowired
	UcasUserResRelMapper ucasUserResRelMapper;

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasClientResourcePojo> queryResource(String resGroupUuid, String clientId, int pageNum,
			int pageSize) {
		return ucasClientResourceMapper.queryResource(null, resGroupUuid, clientId, CommStatusEnum.SYS_INUSE.getValue(),
				CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public UcasClientResourcePojo queryResourceById(String resUuid) {
		PageInfo<UcasClientResourcePojo> pageInfo = ucasClientResourceMapper.queryResource(resUuid, null, null,
				CommStatusEnum.SYS_INUSE.getValue(), CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(1, 1));
		return pageInfo.getData().get(0);
	}

	@Override
	@Logger(operationName = "添加资源", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int addResource(UcasClientResource ucasClientResource) {
		ucasClientResource.setResUuid(UUIDGenerator.generate());
		ucasClientResource.setStatus(CommStatusEnum.SYS_INUSE.getValue());
		ucasClientResource.setCreateDate(new Date());
		ucasClientResource.setModifyDate(new Date());
		return ucasClientResourceMapper.addResource(ucasClientResource);
	}

	@Override
	@Logger(operationName = "更新资源", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int editResource(UcasClientResource ucasClientResource) {
		ucasClientResource.setModifyDate(new Date());
		return ucasClientResourceMapper.editResource(ucasClientResource);
	}

	@Override
	@Logger(operationName = "删除资源", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int deleteResource(String resUuid) {
		if (ucasUserResRelMapper.getUserRelResourceByResUuid(resUuid) > 0) {
			return -1;
		} else {
			UcasClientResource ucasClientResource = queryResourceById(resUuid);
			ucasClientResource.setStatus(CommStatusEnum.SYS_DELETE.getValue());
			ucasClientResource.setModifyDate(new Date());
			return ucasClientResourceMapper.editResource(ucasClientResource);
		}
	}

	@Override
	@Logger(operationName = "删除资源", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int deleteResourcePhysical(String resUuid) {
		return ucasClientResourceMapper.deleteResource(resUuid);
	}

	@Override
	@Logger(printSQL = true)
	public boolean isResUriExist(String resGroupUuid, String resUri) {
		if (ucasClientResourceMapper.isResUriExist(resGroupUuid, resUri, CommStatusEnum.SYS_INUSE.getValue()) > 0)
			return true;
		else
			return false;
	}

}