package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageUserProfile;
import com.ucsmy.ucas.manage.ext.UserProfilePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageUserProfileMapper {
	
	UcasPageInfo<ManageUserProfile> queryUserProfile(@Param("name") String name, PageRequest pageRequest);
	
	UserProfilePojo queryUserProfileByUserId(@Param("userId") String userId);
	
	ManageUserProfile queryUserProfileByLoginName(@Param("loginName") String loginName);
	
	int chenckUserProfileByMobilephone(@Param("mobilephone") String mobilephone);
	
	int chenckUserProfileByEmail(@Param("email") String email);
	
	int saveUserProfile( ManageUserProfile manageUserProfile);
	
	int updateUserProfile(ManageUserProfile manageUserProfile);
	
	int deleteUserProfile(@Param("userId") String userId);
}
