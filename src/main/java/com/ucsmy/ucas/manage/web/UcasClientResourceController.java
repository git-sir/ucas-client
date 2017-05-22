package com.ucsmy.ucas.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasClientResource;
import com.ucsmy.ucas.manage.ext.UcasClientResourcePojo;
import com.ucsmy.ucas.manage.service.UcasClientResourceService;

@Controller
@RequestMapping("clientResource")
public class UcasClientResourceController {

	@Autowired
	UcasClientResourceService ucasClientResourceService;

	@RequestMapping("queryResource")
	@ResponseBody
	public PageInfo<UcasClientResourcePojo> queryResource(@RequestParam(required = false) String resGroupUuid,
			@RequestParam(required = false) String clientId, @RequestParam(required = true) int pageNum,
			@RequestParam(required = true) int pageSize) {
		return ucasClientResourceService.queryResource(resGroupUuid, clientId, pageNum, pageSize);
	}

	@RequestMapping("queryResourceById")
	@ResponseBody
	public UcasClientResource queryResourceById(@RequestParam(required = false) String resUuid) {
		return ucasClientResourceService.queryResourceById(resUuid);
	}

	@RequestMapping("addResource")
	@ResponseBody
	public AosResult addResource(UcasClientResource ucasClientResource) {
		if(ucasClientResourceService.isResUriExist(ucasClientResource.getResGroupUuid(),ucasClientResource.getResUri())){
			return AosResult.retFailureMsg("该资源组所属应用下已存在相同的资源URI");
		}else{
			if(ucasClientResourceService.addResource(ucasClientResource) > 0)
				return AosResult.retSuccessMsg("添加成功");
			else
				return AosResult.retFailureMsg("添加失败");
		}
	}

	@RequestMapping("editResource")
	@ResponseBody
	public AosResult editResource(UcasClientResource ucasClientResource) {
		UcasClientResourcePojo ucasClientResourcePojo = ucasClientResourceService.queryResourceById(ucasClientResource.getResUuid());
		if(!ucasClientResourcePojo.getResUri().equals(ucasClientResource.getResUri())){
			if(ucasClientResourceService.isResUriExist(ucasClientResource.getResGroupUuid(),ucasClientResource.getResUri())){
				return AosResult.retFailureMsg("该资源组所属应用下已存在相同的资源URI");
			}
		}
		
		if(ucasClientResourceService.editResource(ucasClientResource) > 0)
			return AosResult.retSuccessMsg("修改成功");
		else
			return AosResult.retFailureMsg("修改失败");
		
	}

	@RequestMapping("deleteResource")
	@ResponseBody
	public AosResult deleteResource(String resUuid) {
		int resultRow = ucasClientResourceService.deleteResource(resUuid);
		if (resultRow > 0) {
			return AosResult.retSuccessMsg("删除成功");
		} else if (resultRow == -1) {
			return AosResult.retFailureMsg("该资源有用户在使用不能删除");
		} else {
			return AosResult.retFailureMsg("删除失败");
		}
	}

}
