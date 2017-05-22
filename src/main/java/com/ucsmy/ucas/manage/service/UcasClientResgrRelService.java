package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientResgrRel;
import com.ucsmy.ucas.manage.ext.UcasClientResgrRelPojo;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;

public interface UcasClientResgrRelService {

	PageInfo<UcasClientResgrRelPojo> queryResgrRelByClient(String clientId, int pageNum, int pageSize);

	PageInfo<UcasClientResgrRelPojo> queryResgrRelByGroup(String resGroupUuid, int pageNum, int pageSize);

	PageInfo<UcasResourceGroupPojo> queryUnbindResgrRel(String clientId, String resGroupUuid, String filteringClientId,
			int pageNum, int pageSize);

	UcasClientResgrRelPojo queryResgrRelById(String uuid);

	int addResgrRel(UcasClientResgrRel ucasClientResgrRel);

	int editResgrRel(UcasClientResgrRel ucasClientResgrRel);

	int deleteResgrRel(String uuid);

	boolean isResgrRelExist(String clientId, String resGroupUuid);

	PageInfo<UcasClientResgrRelPojo> queryResgrRel(String clientId, String resGroupUuid, String searchClientId,
			int pageNum, int pageSize);

	PageInfo<UcasResourceGroupPojo> queryResgrRels(String clientId, String clientName, int pageNum, int pageSize);

	int updateTicket(String uuid, String maxTimes, String expiryTime);
}
