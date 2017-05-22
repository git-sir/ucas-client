package com.ucsmy.ucas.manage.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageRole;

@Mapper
public interface ManageRoleMapper {
	
	UcasPageInfo<ManageRole> queryRoleList(@Param("name") String name, PageRequest pageRequest);
	
	List<ManageRole> queryAllRoleList();
	
	ManageRole queryRoleByName(@Param("name") String name);
	
	ManageRole queryRoleById(@Param("roleId") String roleId);
	
	Long queryRoleUserCountByRoleId(@Param("roleId") String roleId);
	
	String queryRoleMark(@Param("roleId") String roleId);
	
	int addRole(ManageRole manageRole);
	
	int updateRole(ManageRole manageRole);
	
	int deleteRole(@Param("roleId") String roleId);
	
}
