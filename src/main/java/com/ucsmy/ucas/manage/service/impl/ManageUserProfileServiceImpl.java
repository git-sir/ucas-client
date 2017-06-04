package com.ucsmy.ucas.manage.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import com.ucsmy.commons.utils.EncryptUtils;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.BusinessException;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.entity.ManageUserAccount;
import com.ucsmy.ucas.manage.entity.ManageUserRole;
import com.ucsmy.ucas.manage.ext.UserProfilePojo;
import com.ucsmy.ucas.manage.service.ManageRoleService;
import com.ucsmy.ucas.manage.service.ManageUserAccountService;
import com.ucsmy.ucas.manage.service.ManageUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.manage.dao.ManageUserProfileMapper;
import com.ucsmy.ucas.manage.entity.ManageUserProfile;
import com.ucsmy.ucas.manage.service.ManageUserProfileService;
import org.springframework.stereotype.Service;

@Service
public class ManageUserProfileServiceImpl implements ManageUserProfileService {
	@Autowired
	private ManageUserProfileMapper manageUserProfileMapper;
	@Autowired
	private ManageUserAccountService manageUserAccountService;
	@Autowired
	private ManageRoleService manageRoleService;
	@Autowired
	private ManageUserRoleService manageUserRoleService;

	@Override
	@Logger(printSQL = true)
	public PageInfo<ManageUserProfile> queryUserProfile(String name, int pageNum, int pageSize) {
		return manageUserProfileMapper.queryUserProfile(name,new PageRequest(pageNum,pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public UserProfilePojo queryUserProfileByUserId(String userId) {
		return manageUserProfileMapper.queryUserProfileByUserId(userId);
	}

	@Override
	@Logger(printSQL = true)
	public ManageUserProfile queryUserProfileByLoginName(String loginName) {
		return manageUserProfileMapper.queryUserProfileByLoginName(loginName);
	}

	@Override
	@Logger(printSQL = true)
	public int chenckUserProfileByMobilephone(String mobilephone) {
		return manageUserProfileMapper.chenckUserProfileByMobilephone(mobilephone);
	}

	@Override
	@Logger(printSQL = true)
	public int chenckUserProfileByEmail(String email) {
		return manageUserProfileMapper.chenckUserProfileByEmail(email);
	}

	@Override
	@Logger(printSQL = true)
	public int saveUserProfile(ManageUserProfile manageUserProfile) {
		return manageUserProfileMapper.saveUserProfile(manageUserProfile);
	}

	@Override
	@Logger(printSQL = true)
	public int updateUserProfile(ManageUserProfile manageUserProfile) {
		return manageUserProfileMapper.updateUserProfile(manageUserProfile);
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserProfile(String userId) {
		return manageUserProfileMapper.deleteUserProfile(userId);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult add(ManageUserProfile profile, ManageUserAccount userAccount) {
		profile.setBirthday("1990-01-01");
		profile.setType("register");
		profile.setCreateDate(new Timestamp(System.currentTimeMillis()));
		profile.setUpdateDate(profile.getCreateDate());

		userAccount.setCreateTime(profile.getCreateDate());
		userAccount.setStatus((byte) 0);
		userAccount.setSalt(StringAndNumberUtil.getRandom(5));
		profile.setUserAccount(userAccount);
		String id = UUIDGenerator.generate();
		profile.setUserId(id);
		userAccount.setUserId(id);

		// 加盐加密
		String password = "ucsmy123456";
		password = EncryptUtils.MD5(userAccount.getAccount().concat(password).concat(userAccount.getSalt()));
		userAccount.setPassword(password);

		String errorMsg = HibernateValidateUtils.getErrors(userAccount, profile);
		if(!StringAndNumberUtil.isNull(errorMsg)) {
			throw new BusinessException(errorMsg);
		}

		if(manageUserAccountService.chenckUserAccountByAccount(userAccount.getAccount()) > 0) {
			return AosResult.retFailureMsg("帐号已存在");
		}
		if(chenckUserProfileByMobilephone(profile.getMobilephone()) > 0) {
			return AosResult.retFailureMsg("手机号码已存在");
		}
		if(chenckUserProfileByMobilephone(profile.getEmail()) > 0) {
			return AosResult.retFailureMsg("手机号码已存在");
		}

		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return AosResult.retFailureMsg("角色不存在");
		}
		saveUserProfile(profile);
		manageUserAccountService.saveUserAccount(userAccount);
		ManageUserRole userRole = new ManageUserRole();
		userRole.setId( UUID.randomUUID().toString());
		userRole.setUserId(profile.getUserId());
		userRole.setRole( role);
		manageUserRoleService.insertUserRole(userRole);
		return AosResult.retSuccessMsg("新增成功");
	}

	@Override
	@Logger(printSQL = true)
	public AosResult update(ManageUserProfile profile, ManageUserAccount userAccount) {
		if(manageUserAccountService.queryUserAccountByUserId(userAccount.getUserId()) == null) {
			return AosResult.retFailureMsg("不存在该用户数据");
		}
		String errorMsg = HibernateValidateUtils.getErrors(userAccount, profile);
		if(!StringAndNumberUtil.isNull(errorMsg)) {
			return AosResult.retFailureMsg(errorMsg);
		}
		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return AosResult.retFailureMsg("角色不存在");
		}
		manageUserAccountService.updateUserInfo(profile,userAccount,role);
		return AosResult.retSuccessMsg("修改成功");
	}

	@Override
	@Logger(printSQL = true)
	public AosResult delete(String userId) {
		ManageUserProfile up = queryUserProfileByUserId(userId);
		if(up == null) {
			return AosResult.retFailureMsg("不存在该用户数据");

		}
		deleteUserProfile(up.getUserId());
		manageUserAccountService.deleteUserAccount(up.getUserAccount().getUserId());
		return AosResult.retSuccessMsg("删除成功");
	}
}
