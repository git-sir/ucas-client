package com.ucsmy.ucas.manage.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.ucas.manage.service.ManageRolePermissionService;

@Controller
@RequestMapping("rolePermission")
public class ManageRolePermissionController {

	@Autowired
    ManageRolePermissionService manageRolePermissionService;
	
	@RequestMapping("queryRolePermission")
	@ResponseBody
	public String queryRolePermission(String roleId) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rolePermissions", JSONObject.parse(manageRolePermissionService.queryAllModulePermission(roleId)));
		jsonObject.put("success", true);
		jsonObject.put("msg", "");
		return jsonObject.toString();
	}
	
	@RequestMapping("addRolePermission")
	@ResponseBody
	public String addRolePermission(String roleId,String permissionsId,String name) {
		return manageRolePermissionService.addRolePermission(roleId, permissionsId, name);
	}

}
