package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageUserRole;

import java.util.Map;

public interface ManageUserRoleService {
	
	PageInfo<ManageUserRole> queryUserRoleList(String roleId, String account, int pageNum, int pageSize);
	
	PageInfo<ManageUserRole> queryUnbindUserList(String roleId, String account, int pageNum, int pageSize);
	
	int queryUserCountByUserIds(Map<String, Object> map);	
	
	int insertUserRole(ManageUserRole manageUserRole);
	
	int deleteUserRoleByUserIds(String[] ids);
	
	int deleteUserRoleByIds(String id);

	AosResult insertUserRole(String roleId, String userIds);
	
	AosResult deleteUserRoles(String ids);
	
}
