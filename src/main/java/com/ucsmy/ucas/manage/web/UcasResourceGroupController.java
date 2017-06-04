package com.ucsmy.ucas.manage.web;

import java.util.List;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;
import com.ucsmy.ucas.manage.service.UcasResourceGroupService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resourceGroup")
public class UcasResourceGroupController {

	private final static String ID_EMPTY = "资源组id不能为空";

	@Autowired
	private UcasResourceGroupService ucasResourceGroupService;

	/**
	 * 查所有资源组，不分页
	 * @return
	 */
	@RequestMapping("queryAllResourceGroup")
	public AosResult queryAllResourceGroup() {
		List<UcasResourceGroup> resourceGroupList = ucasResourceGroupService.queryAllResourceGroup();
		return AosResult.retSuccessMsg("查询成功",resourceGroupList);
	}

	/**
	 * 分页查询
	 * @param clientId
	 * @param groupName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("queryResourceGroupByClient")
	public PageInfo<UcasResourceGroupPojo> queryResourceGroupByClient(@RequestParam(required = false) String clientId
			, @RequestParam(required = false) String groupName, @RequestParam(required = false) String clientName
			, @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
		return ucasResourceGroupService.queryResourceGroupByClient(clientId, groupName, clientName, pageNum, pageSize);
	}
	
	@RequestMapping("queryResourceGroupById")
	public UcasResourceGroup queryResourceGroupById(@RequestParam(required = true) String resGroupUuid) {
		return ucasResourceGroupService.getResourceGroupById(resGroupUuid);
	}

	/**
	 * 添加资源组
	 * @param ucasResourceGroup
	 * @return
	 */
	@RequestMapping("add")
	public AosResult addResourceGroup(UcasResourceGroup ucasResourceGroup) {
		AosResult validate = ucasResourceGroupService.validateResourceGroup(ucasResourceGroup);
		if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
			return validate;
		}
		return ucasResourceGroupService.addResourceGroup(ucasResourceGroup);
	}

	@RequestMapping("update")
	public AosResult updateResourceGroup(UcasResourceGroup ucasResourceGroup) {
		AosResult validate = ucasResourceGroupService.validateResourceGroup(ucasResourceGroup);
		if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
			return validate;
		}
		if (StringUtils.isEmpty(ucasResourceGroup.getResGroupUuid())) {
			return AosResult.retFailureMsg(ID_EMPTY);
		}
		return ucasResourceGroupService.editResourceGroup(ucasResourceGroup);
	}

	@RequestMapping("delete")
	public AosResult deleteResourceGroup(String resGroupUuid) {
		if (StringUtils.isEmpty(resGroupUuid)) {
			return AosResult.retFailureMsg(ID_EMPTY);
		}
		return ucasResourceGroupService.deleteResourceGroup(resGroupUuid);
	}

}
