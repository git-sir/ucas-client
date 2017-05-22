package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientResgrRel;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;
import com.ucsmy.ucas.manage.ext.UcasClientResgrRelPojo;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UcasClientResgrRelMapper {
	UcasPageInfo<UcasClientResgrRelPojo> queryResgrRel(@Param("uuid") String uuid, @Param("clientId") String clientId,
			@Param("resGroupUuid") String resGroupUuid, @Param("searchClientId") String searchClientId,
			@Param("status") String status, PageRequest pageRequest);

	// UcasClientResgrRelPojo queryResgrRel(@Param("resUuid") String
	// resUuid,@Param("resGroupUuid") String resGroupUuid);

	UcasPageInfo<UcasResourceGroupPojo> queryUnbindResgrRel(@Param("clientId") String clientId,
			@Param("resGroupUuid") String resGroupUuid, @Param("filteringClientId") String filteringClientId,
			@Param("status") String status, PageRequest pageRequest);

	int addResgrRel(UcasClientResgrRel ucasClientResgrRel);

	int editResgrRel(UcasClientResgrRel ucasClientResgrRel);

	int deleteResgrRel(String uuid);

	int isResgrRelExist(@Param("uuid") String uuid, @Param("clientId") String clientId,
			@Param("resGroupUuid") String resGroupUuid);

	UcasPageInfo<UcasResourceGroupPojo> queryResgrRels(@Param("clientId")String clientId,
												   @Param("clientName")String clientName, PageRequest pageRequest);

	int updateTicket(@Param("uuid")String uuid,@Param("maxTimes") String maxTimes, @Param("expiryTime")String expiryTime);
}
