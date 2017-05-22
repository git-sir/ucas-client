package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.EncryptUtils;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.BusinessException;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.entity.ManageUserAccount;
import com.ucsmy.ucas.manage.entity.ManageUserProfile;
import com.ucsmy.ucas.manage.entity.ManageUserRole;
import com.ucsmy.ucas.manage.service.ManageRoleService;
import com.ucsmy.ucas.manage.service.ManageUserAccountService;
import com.ucsmy.ucas.manage.service.ManageUserProfileService;
import com.ucsmy.ucas.manage.service.ManageUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by ucs_zhongtingyuan on 2017/4/10.
 */
@Controller
@RequestMapping("user")
public class ManageUserController {

	@Autowired
	private ManageUserAccountService manageUserAccountService;
	@Autowired
	private ManageUserProfileService manageUserProfileService;
	@Autowired
	private ManageRoleService manageRoleService;
	@Autowired
	private ManageUserRoleService manageUserRoleService;

	@RequestMapping("query")
	@ResponseBody
	public AosResult query(@RequestParam(required = false) String name, int pageNum, int pageSize) {
		PageInfo<ManageUserProfile> pageInfo = manageUserProfileService.queryUserProfile(name,pageNum,pageSize);
		return AosResult.retSuccessMsg(null, pageInfo);
	}

	@RequestMapping("add")
	@ResponseBody
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
			return new AosResult(-1,null,"登录名已存在");
		}
		if(manageUserProfileService.chenckUserProfileByMobilephone(profile.getMobilephone()) > 0) {
			return new AosResult(-1,null,"手机号码已存在");
		}
		if(manageUserProfileService.chenckUserProfileByEmail(profile.getEmail()) > 0) {
			return new AosResult(-1,null,"邮箱已存在");
		}
		
		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return new AosResult(-1,null,"角色不存在");
		}
		manageUserProfileService.saveUserProfile(profile);
		manageUserAccountService.saveUserAccount(userAccount);
		ManageUserRole userRole = new ManageUserRole();
		userRole.setId( UUID.randomUUID().toString());
		userRole.setUserId(profile.getUserId());
		userRole.setRole( role);
		manageUserRoleService.insertUserRole(userRole);
		return AosResult.retSuccessMsg("新增成功",null);
	}

	@RequestMapping("update")
	@ResponseBody
	public AosResult update(ManageUserProfile profile, ManageUserAccount userAccount) {
		
		if(manageUserAccountService.queryUserAccountByUserId(userAccount.getUserId()) == null) {
			return  AosResult.retFailureMsg("不存在该用户数据");
		}
		String errorMsg = HibernateValidateUtils.getErrors(userAccount, profile);
		if(!StringAndNumberUtil.isNull(errorMsg)) {
			return AosResult.retFailureMsg(errorMsg);
		}
		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return  AosResult.retFailureMsg("角色不存在");
		}
		manageUserAccountService.updateUserInfo(profile,userAccount,role);
		return AosResult.retSuccessMsg("修改成功",null);
	}

	@RequestMapping("delete")
	@ResponseBody
	public AosResult delete(String userId) {		
		ManageUserProfile up = manageUserProfileService.queryUserProfileByUserId(userId);
		if(up == null) {
			return  AosResult.retFailureMsg("不存在该用户数据");
		}
		manageUserProfileService.deleteUserProfile(up.getUserId());
		manageUserAccountService.deleteUserAccount(up.getUserAccount().getUserId());
		return AosResult.retSuccessMsg("删除成功",null);
	}

	@RequestMapping("updateUserPassword")
	@ResponseBody
	public AosResult updateUserPassword(String userId, String password, String newPassword) {
		return manageUserAccountService.updateUserAccountPassword(userId, password, newPassword);
	}
}
