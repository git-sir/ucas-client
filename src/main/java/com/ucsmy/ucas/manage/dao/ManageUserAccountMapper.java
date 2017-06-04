package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManageUserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface ManageUserAccountMapper {

	ManageUserAccount findByUserName(@Param("userName") String userName);
	
	ManageUserAccount queryUserAccount(@Param("map") Map<String, Object> map);
	
	ManageUserAccount queryUserAccountByUserId(@Param("userId") String userId);
	
	int chenckUserAccountByAccount(@Param("account") String account);
	
	int saveUserAccount(ManageUserAccount manageUserAccount);
	
	int updateUserAccount(ManageUserAccount manageUserAccount);
	
	int updateUserAccountPassword(ManageUserAccount manageUserAccount);
	
	int deleteUserAccount(@Param("userId") String userId);
}
