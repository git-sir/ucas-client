package com.ucsmy.ucas.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucsmy.ucas.manage.entity.ManageRolePermission;

/**
 * Created by chenqilin on 2017/4/14.
 */
public interface ManageRolePermissionService {

	String queryAllModulePermission(String roleId) throws JsonProcessingException;
	
	String addRolePermission(String roleId,String permissionsId,String name);

}
