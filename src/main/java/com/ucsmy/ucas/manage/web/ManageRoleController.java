package com.ucsmy.ucas.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.entity.ManageUserRole;
import com.ucsmy.ucas.manage.service.ManageRoleService;
import com.ucsmy.ucas.manage.service.ManageUserRoleService;

@Controller
@RequestMapping("role")
public class ManageRoleController {

	@Autowired
	private ManageRoleService manageRoleService;
	@Autowired
	private ManageUserRoleService manageUserRoleService;

	@RequestMapping("queryRoleList")
	@ResponseBody
	public PageInfo<ManageRole> queryRoleList(@RequestParam(required = false) String name,
                                              @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
		return manageRoleService.queryRoleList(name, pageNum, pageSize);
	}

	@RequestMapping("queryAllRoleList")
	@ResponseBody
	public String queryAllRoleList() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roleList", manageRoleService.queryAllRoleList());
		jsonObject.put("success", true);
		jsonObject.put("errorMsg", "");
		return jsonObject.toString();
	}

	@RequestMapping("queryRoleByName")
	@ResponseBody
	public ManageRole queryRoleByName(@RequestParam(required = false) String name) {
		return manageRoleService.queryRoleByName(name);
	}

	@RequestMapping("queryRoleById")
	@ResponseBody
	public ManageRole queryRoleById(@RequestParam(required = false) String roleId) {
		return manageRoleService.queryRoleById(roleId);
	}

	@RequestMapping("addRole")
	@ResponseBody
	public AosResult addRole(ManageRole manageRole) {
		return manageRoleService.addRole(manageRole);
	}

	@RequestMapping("updateRole")
	@ResponseBody
	public AosResult updateRole(ManageRole manageRole) {
		return manageRoleService.updateRole(manageRole);
	}

	@RequestMapping("deleteRole")
	@ResponseBody
	public AosResult deleteRole(String roleId) {
		return manageRoleService.deleteRole(roleId);
	}

	@RequestMapping("queryUnbindUserList")
	@ResponseBody
	public PageInfo<ManageUserRole> queryUnbindUserList(@RequestParam(required = false) String roleId, String name,
														@RequestParam(required = false) String account, @RequestParam(required = true) int pageNum,
														@RequestParam(required = true) int pageSize) {
		return manageUserRoleService.queryUnbindUserList(roleId, account, name, pageNum, pageSize);
	}

	@RequestMapping("bindUser")
	@ResponseBody
	public AosResult bindUser(@RequestParam(required = true) String roleId,
			@RequestParam(required = true) String userIds) {
		return manageUserRoleService.insertUserRole(roleId, userIds);
	}
	
	@RequestMapping("queryBindUserList")
	@ResponseBody
	public PageInfo<ManageUserRole> queryBindUserList(@RequestParam(required = false) String roleId, String name,
													  @RequestParam(required = false) String account, @RequestParam(required = true) int pageNum,
													  @RequestParam(required = true) int pageSize) {
		return manageUserRoleService.queryUserRoleList(roleId, account, name, pageNum, pageSize);
	}
	
	@RequestMapping("unbindUser")
	@ResponseBody
	public AosResult unbindUser(@RequestParam(required = true) String ids) {
		return manageUserRoleService.deleteUserRoles(ids);
	}

}
