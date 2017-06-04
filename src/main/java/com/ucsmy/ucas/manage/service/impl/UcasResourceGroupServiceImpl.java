package com.ucsmy.ucas.manage.service.impl;

import java.util.List;

import com.ucsmy.commons.utils.BeanUtil;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.dao.UcasClientResourceMapper;
import com.ucsmy.ucas.manage.entity.UcasClientResource;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.manage.dao.UcasResourceGroupMapper;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;
import com.ucsmy.ucas.manage.service.UcasResourceGroupService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UcasResourceGroupServiceImpl implements UcasResourceGroupService {

	private static final String GROUPNAME_EMPTY = "资源组名称不能为空";
	private static final String DESCRIBE_EMPTY = "资源组描述不能为空";
	private static final String CLIENTID_EMPTY = "应用ID不能为空";
	private static final String GROUPNAME_LENGTH_ILLEGAL = "资源组名称长度不能超过50";
	private static final String DESCRIBE_LENGTH_ILLEGAL = "资源组描述长度不能超过200";
	private static final String DUPLICATE_GROUP_NAME = "存在同名资源组";

	private static final String HAS_RESOURCES = "该资源组下有正常资源，不能删除";

	@Autowired
	private UcasResourceGroupMapper ucasResourceGroupMapper;

	@Autowired
	private UcasClientResourceMapper ucasClientResourceMapper;
	
	@Override
	@Logger(printSQL = true)
	public List<UcasResourceGroup> queryAllResourceGroup() {
		return ucasResourceGroupMapper.queryResourceGroup(null, null);
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<UcasResourceGroupPojo> queryResourceGroupByClient(String clientId, String groupName, String clientName, int pageNum, int pageSize) {
		return ucasResourceGroupMapper.queryResourceGroupList(null, clientId, groupName, clientName, CommStatusEnum.SYS_INUSE.getValue(), new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public UcasResourceGroup getResourceGroupById(String resGroupUuid) {
		return ucasResourceGroupMapper.getResourceGroupById(resGroupUuid);
	}

	@Override
	@Logger(operationName = "添加资源组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	@Transactional(rollbackFor = Exception.class)
	public AosResult addResourceGroup(UcasResourceGroup ucasResourceGroup) {
		//
		ucasResourceGroup.setResGroupUuid(UUIDGenerator.generate());
		int result = ucasResourceGroupMapper.addResourceGroup(ucasResourceGroup);
		if (result <= 0) {
			return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
		}
		return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS);
	}

	@Override
	@Logger(operationName = "更新资源组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	@Transactional(rollbackFor = Exception.class)
	public AosResult editResourceGroup(UcasResourceGroup ucasResourceGroup) {
		UcasResourceGroup oldResourceGroup = this.getResourceGroupById(ucasResourceGroup.getResGroupUuid());
		if (oldResourceGroup == null) {
			return AosResult.retFailureMsg(CommMessage.DATA_NOT_EXIST);
		}
		BeanUtil.copyPropertiesIgnoreNull(ucasResourceGroup, oldResourceGroup);
		int result = ucasResourceGroupMapper.editResourceGroup(oldResourceGroup);
		if (result <= 0) {
			return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
		}
		return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS);
	}

	@Override
	@Logger(operationName = "删除资源组", printSQL = true, outputTarget = LogOuputTarget.DATABASE)
	@Transactional(rollbackFor = Exception.class)
	public AosResult deleteResourceGroup(String resGroupUuid) {
		// 查看底下是否有资源，有的话就不能删除
		List<UcasClientResource> resources = ucasClientResourceMapper.getResourceList(resGroupUuid, CommStatusEnum.SYS_INUSE.getValue());
		if (resources != null && !resources.isEmpty()) {
			return AosResult.retFailureMsg(HAS_RESOURCES);
		}
		int result = ucasResourceGroupMapper.deleteResourceGroup(resGroupUuid);
		if (result <= 0) {
			return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
		}
		return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult validateResourceGroup(UcasResourceGroup ucasResourceGroup) {
		if (StringUtils.isEmpty(ucasResourceGroup.getGroupName())) {
			return AosResult.retFailureMsg(GROUPNAME_EMPTY);
		}
		if (StringUtils.isEmpty(ucasResourceGroup.getDescRibe())) {
			return AosResult.retFailureMsg(DESCRIBE_EMPTY);
		}
		if (StringUtils.isEmpty(ucasResourceGroup.getClientId())) {
			return AosResult.retFailureMsg(CLIENTID_EMPTY);
		}
		if (ucasResourceGroup.getGroupName().length() > 50) {
			return AosResult.retFailureMsg(GROUPNAME_LENGTH_ILLEGAL);
		}
		if (ucasResourceGroup.getDescRibe().length() > 200) {
			return AosResult.retFailureMsg(DESCRIBE_LENGTH_ILLEGAL);
		}
		// 检查资源组是否重名
		List<UcasResourceGroup> existList = ucasResourceGroupMapper.getResourceGroupByCondition(null, ucasResourceGroup.getGroupName(), ucasResourceGroup.getResGroupUuid());
		if (existList != null && !existList.isEmpty()) {
			return AosResult.retFailureMsg(DUPLICATE_GROUP_NAME);
		}
		return AosResult.retSuccessMsg("success");
	}
}
