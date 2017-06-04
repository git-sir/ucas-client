package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageUserRoleMapper {

	UcasPageInfo<ManageUserRole> queryUserRoleList(@Param("roleId") String roleId, @Param("account") String account,
												   @Param("name") String name, PageRequest pageRequest);

	UcasPageInfo<ManageUserRole> queryUnbindUserList(@Param("roleId") String roleId, @Param("account") String account,
													 @Param("name") String name, PageRequest pageRequest);

	Long queryUserCountByUserIds(@Param("ids") String[] ids);

	int insertUserRole(ManageUserRole manageUserRole);

	int deleteUserRoleByUserIds(@Param("ids") String[] ids);

	int deleteUserRoleByIds(String id);
	
	int deleteUserRoles(@Param("ids") String[] ids);
}
