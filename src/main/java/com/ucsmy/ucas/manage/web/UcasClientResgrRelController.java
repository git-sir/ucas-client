package com.ucsmy.ucas.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasClientResgrRel;
import com.ucsmy.ucas.manage.entity.UcasResourceGroup;
import com.ucsmy.ucas.manage.ext.UcasClientResgrRelPojo;
import com.ucsmy.ucas.manage.ext.UcasResourceGroupPojo;
import com.ucsmy.ucas.manage.service.UcasClientResgrRelService;

@Controller
@RequestMapping("clientResgrRel")
public class UcasClientResgrRelController {

	@Autowired
	UcasClientResgrRelService ucasClientResgrRelService;

	@RequestMapping("queryResgrRel")
	@ResponseBody
	public PageInfo<UcasClientResgrRelPojo> queryResgrRel(@RequestParam(required = false) String clientId,
			@RequestParam(required = false) String resGroupUuid, @RequestParam(required = false) String searchClientId,
			@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
		return ucasClientResgrRelService.queryResgrRel(clientId, resGroupUuid, searchClientId, pageNum, pageSize);
	}

	@RequestMapping("queryResgrRelByClient")
	@ResponseBody
	public PageInfo<UcasClientResgrRelPojo> queryResgrRelByClient(@RequestParam(required = false) String clientId,
			@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
		return ucasClientResgrRelService.queryResgrRelByClient(clientId, pageNum, pageSize);
	}

	@RequestMapping("queryResgrRelByGroup")
	@ResponseBody
	public PageInfo<UcasClientResgrRelPojo> queryResgrRelByGroup(@RequestParam(required = false) String resGroupUuid,
			@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
		return ucasClientResgrRelService.queryResgrRelByGroup(resGroupUuid, pageNum, pageSize);
	}

	@RequestMapping("queryUnbindResgrRel")
	@ResponseBody
	public PageInfo<UcasResourceGroupPojo> queryUnbindResgrRel(
			@RequestParam(value = "searchClientId", required = false) String clientId,
			@RequestParam(required = false) String resGroupUuid,
			@RequestParam(required = true) String filteringClientId, @RequestParam(required = true) int pageNum,
			@RequestParam(required = true) int pageSize) {
		return ucasClientResgrRelService.queryUnbindResgrRel(clientId, resGroupUuid, filteringClientId, pageNum,
				pageSize);
	}

	@RequestMapping("queryResgrRelById")
	@ResponseBody
	public UcasClientResgrRelPojo queryResgrRelById(@RequestParam(required = false) String uuid) {
		return ucasClientResgrRelService.queryResgrRelById(uuid);
	}

	@RequestMapping("addResgrRel")
	@ResponseBody
	public AosResult addResgrRel(@RequestParam(required = false) String clientId,
			@RequestParam(required = false) String resGroupUuid,
	        @RequestParam(required = false)Integer maxTimes,
			@RequestParam(required = false)Integer expiryTime
								 ) {
		String[] resGroupUuids = resGroupUuid.split(",");

		UcasClientResgrRel ucasClientResgrRel = new UcasClientResgrRel();
		ucasClientResgrRel.setClientId(clientId);
		ucasClientResgrRel.setMaxTimes(maxTimes);
		ucasClientResgrRel.setExpiryTime(expiryTime);
		for (String groupUuid : resGroupUuids) {
			ucasClientResgrRel.setResGroupUuid(groupUuid);
			ucasClientResgrRelService.addResgrRel(ucasClientResgrRel);
		}

		return AosResult.retSuccessMsg("绑定成功");
	}

	@RequestMapping("editResgrRel")
	@ResponseBody
	public AosResult editResgrRel(UcasClientResgrRel ucasClientResgrRel) {
		ucasClientResgrRelService.editResgrRel(ucasClientResgrRel);
		return AosResult.retSuccessMsg("修改成功");
	}

	@RequestMapping("deleteResgrRel")
	@ResponseBody
	public AosResult deleteResgrRel(String uuid) {
		String[] uuids = uuid.split(",");

		for (String deleteUuid : uuids) {
			ucasClientResgrRelService.deleteResgrRel(deleteUuid);
		}

		return AosResult.retSuccessMsg("解绑成功");
	}

	@RequestMapping("isResgrRelExist")
	@ResponseBody
	public AosResult isResgrRelExist(String clientId, String resGroupUuid) {
		if (ucasClientResgrRelService.isResgrRelExist(clientId, resGroupUuid)) {
			return AosResult.retSuccessMsg("应用授权对象存在", true);
		} else {
			return AosResult.retSuccessMsg("应用授权对象不存在", false);
		}

	}

    /****
	 * 查找授权列表
	 * */
	@RequestMapping("queryResgrRels")
	@ResponseBody
	public AosResult queryResgrRels(
			@RequestParam(value = "clientId", required = false) String clientId,
			@RequestParam(required = false) String clientName,
			@RequestParam(required = true) int pageNum,
			@RequestParam(required = true) int pageSize) {
		PageInfo<UcasResourceGroupPojo> pageInfo = ucasClientResgrRelService.queryResgrRels(clientId, clientName, pageNum,
				pageSize);
		return AosResult.retSuccessMsg("查询成功", pageInfo);
	}


	/**
	 * 修改ticket调用次数、有效时间
	 * @return
     */
	@RequestMapping("updateTicket")
	@ResponseBody
	public AosResult updateTicket(@RequestParam(required = true) String uuid,
								  @RequestParam(required = false) String maxTimes,
								  @RequestParam(required = false) String expiryTime)
	{
		ucasClientResgrRelService.updateTicket(uuid,maxTimes,expiryTime);
		return AosResult.retSuccessMsg("修改成功");
	}
}
