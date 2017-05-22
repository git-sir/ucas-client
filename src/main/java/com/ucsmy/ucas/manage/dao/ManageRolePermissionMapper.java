package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManageRoleModule;
import com.ucsmy.ucas.manage.entity.ManageRolePermission;
import com.ucsmy.ucas.manage.ext.ModulePermissionPojo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageRolePermissionMapper {

	List<ModulePermissionPojo> queryAllModulePermission();
	
	List<ManageRolePermission> queryRolePermissionByRoleID(@Param("roleId") String roleId);
	
	int insertRolePermissionByBatch(List<ManageRolePermission> rolePermission_list);
	
	int insertRoleModuleByBatch(List<ManageRoleModule> roleModule_list);
	
	int deleteRolePermissionByRoleID(String role_id);
	
	int deleteRoleModuleByRoleID(String role_id);
}
