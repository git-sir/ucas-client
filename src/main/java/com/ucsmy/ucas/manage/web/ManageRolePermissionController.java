package com.ucsmy.ucas.manage.web;

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
	public String queryRolePermission(String role_id) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rolePermissions", JSONObject.parse(manageRolePermissionService.queryAllModulePermission(role_id)));
		jsonObject.put("success", true);
		jsonObject.put("msg", "");
		return jsonObject.toString();
	}
	
	@RequestMapping("addRolePermission")
	@ResponseBody
	public String addRolePermission(String role_id,String permissions_id,String name) {
		return manageRolePermissionService.addRolePermission(role_id, permissions_id, name);
	}

}
