package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.manage.entity.ManageRolePermission;

/**
 * Created by chenqilin on 2017/4/14.
 */
public interface ManageRolePermissionService {

	String queryAllModulePermission(String role_id) throws Exception;
	
	ManageRolePermission queryRolePermissionByRoleID();
	
	int insertRolePermissionByBatch();
	
	int insertRoleModuleByBatch();
	
	int deleteRolePermissionByRoleID();
	
	int deleteRoleModuleByRoleID();
	
	String addRolePermission(String role_id,String permissions_id,String name);

}
