package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientResource;
import com.ucsmy.ucas.manage.ext.UcasClientResourcePojo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UcasClientResourceMapper {

	/**
	 * 分页查询
	 * 
	 * @param resUuid
	 * @param resGroupUuid
	 * @param filteringState
	 *            过滤的状态
	 * @param pageRequest
	 * @return
	 */
	UcasPageInfo<UcasClientResourcePojo> queryResource(@Param("resUuid") String resUuid,
			@Param("resGroupUuid") String resGroupUuid, @Param("clientId") String clientId,
			@Param("status") String status, @Param("clientStatus") String clientStatus, PageRequest pageRequest);

	/**
	 * 非分页查询资源列表
	 * @param resGroupUuid
	 * @param status
	 * @return
	 */
	List<UcasClientResource> getResourceList(@Param("resGroupUuid") String resGroupUuid, @Param("status") String status);

	int addResource(UcasClientResource ucasClientResource);

	int editResource(UcasClientResource ucasClientResource);

	int deleteResource(String resUuid);

	int isResUriExist(@Param("resGroupUuid") String resGroupUuid, @Param("resUri") String resUri,
			@Param("status") String status);
}
