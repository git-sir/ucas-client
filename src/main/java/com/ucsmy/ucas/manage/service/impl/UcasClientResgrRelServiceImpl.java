package com.ucsmy.ucas.manage.service.impl;

import java.util.List;

import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.manage.dao.UcasClientResgrRelMapper;
import com.ucsmy.ucas.manage.entity.UcasClientResgrRel;
import com.ucsmy.ucas.manage.ext.UcasClientResgrRelPojo;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;
import com.ucsmy.ucas.manage.service.UcasClientResgrRelService;

@Service
public class UcasClientResgrRelServiceImpl implements UcasClientResgrRelService {

	@Autowired
	UcasClientResgrRelMapper ucasClientResgrRelMapper;

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasClientResgrRelPojo> queryResgrRel(String clientId, String resGroupUuid, String searchClientId,
			int pageNum, int pageSize) {
		return ucasClientResgrRelMapper.queryResgrRel(null, clientId, resGroupUuid, searchClientId,
				CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasResourceGroupPojo> queryResgrRels(String clientId, String clientName, int pageNum, int pageSize) {
		return ucasClientResgrRelMapper.queryResgrRels(clientId,clientName,new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(operationName = "更新ticket", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int updateTicket(String uuid, String maxTimes, String expiryTime) {
		return ucasClientResgrRelMapper.updateTicket(uuid,maxTimes,expiryTime);
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasClientResgrRelPojo> queryResgrRelByClient(String clientId, int pageNum, int pageSize) {
		return ucasClientResgrRelMapper.queryResgrRel(null, clientId, null, null, CommStatusEnum.SYS_INUSE.getValue(),
				new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasClientResgrRelPojo> queryResgrRelByGroup(String resGroupUuid, int pageNum, int pageSize) {
		return ucasClientResgrRelMapper.queryResgrRel(null, null, resGroupUuid, null,
				CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasResourceGroupPojo> queryUnbindResgrRel(String clientId, String resGroupUuid,
			String filteringClientId, int pageNum, int pageSize) {
		return ucasClientResgrRelMapper.queryUnbindResgrRel(clientId, resGroupUuid, filteringClientId,
				CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public UcasClientResgrRelPojo queryResgrRelById(String uuid) {
		PageInfo<UcasClientResgrRelPojo> pageInfo = ucasClientResgrRelMapper.queryResgrRel(uuid, null, null, null,
				CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(1, 1));
		List<UcasClientResgrRelPojo> listData = pageInfo.getData();
		return listData.get(0);
	}

	@Override
	@Logger(operationName = "添加应用资源组授权", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int addResgrRel(UcasClientResgrRel ucasClientResgrRel) {
		ucasClientResgrRel.setUuid(UUIDGenerator.generate());
		return ucasClientResgrRelMapper.addResgrRel(ucasClientResgrRel);
	}

	@Override
	@Logger(operationName = "更新应用资源组授权", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int editResgrRel(UcasClientResgrRel ucasClientResgrRel) {
		return ucasClientResgrRelMapper.editResgrRel(ucasClientResgrRel);
	}

	@Override
	@Logger(operationName = "删除应用资源组授权", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	public int deleteResgrRel(String uuid) {
		return ucasClientResgrRelMapper.deleteResgrRel(uuid);
	}

	@Override
	@Logger(printSQL = true)
	public boolean isResgrRelExist(String clientId, String resGroupUuid) {
		if (ucasClientResgrRelMapper.isResgrRelExist(null, clientId, resGroupUuid) > 0)
			return true;
		else
			return false;
	}

}
