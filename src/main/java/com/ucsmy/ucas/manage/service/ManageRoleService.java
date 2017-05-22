package com.ucsmy.ucas.manage.service;

import java.util.List;
import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageRole;

public interface ManageRoleService {
	
	PageInfo<ManageRole> queryRoleList(String name, int pageNum, int pageSize);
	
	List<ManageRole> queryAllRoleList();
	
	ManageRole queryRoleByName(String name);
	
	ManageRole queryRoleById(String roleId);
	
	Long queryRoleUserCountByRoleId(String roleId);
	
	String queryRoleMark(String roleId);
	
	AosResult addRole(ManageRole manageRole);
	
	AosResult updateRole(ManageRole manageRole);
	
	AosResult deleteRole(String roleId);
	
}
