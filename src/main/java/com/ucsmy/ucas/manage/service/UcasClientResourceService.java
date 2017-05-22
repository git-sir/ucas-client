package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientResource;
import com.ucsmy.ucas.manage.ext.UcasClientResourcePojo;

public interface UcasClientResourceService {

	PageInfo<UcasClientResourcePojo> queryResource(String resGroupUuid, String clientId, int pageNum, int pageSize);

	UcasClientResourcePojo queryResourceById(String resUuid);

	int addResource(UcasClientResource ucasClientResource);

	int editResource(UcasClientResource ucasClientResource);

	/**
	 * 逻辑删除
	 * 
	 * @param resUuid
	 * @return
	 */
	int deleteResource(String resUuid);

	int deleteResourcePhysical(String resUuid);

	boolean isResUriExist(String resGroupUuid, String resUri);
}
