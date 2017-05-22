package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.dao.ManageRoleMapper;
import com.ucsmy.ucas.manage.dao.ManageUserRoleMapper;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.entity.ManageUserRole;
import com.ucsmy.ucas.manage.service.ManageUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ManageUserRoleServiceImpl implements ManageUserRoleService {

	@Autowired
	private ManageUserRoleMapper userRoleMapper;
	@Autowired
	private ManageRoleMapper manageRoleMapper;

	@Override
	@Logger(printSQL = true)
	public PageInfo<ManageUserRole> queryUserRoleList(String roleId, String account, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return userRoleMapper.queryUserRoleList(roleId, account, new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public PageInfo<ManageUserRole> queryUnbindUserList(String roleId, String account, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return userRoleMapper.queryUnbindUserList(roleId, account, new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public int queryUserCountByUserIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		// return userRoleMapper.queryUserCountByUserIds(map);
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public AosResult insertUserRole(String roleId, String userIds) {

		ManageRole role = manageRoleMapper.queryRoleById(roleId);
		if (role == null) {
			return AosResult.retFailureMsg("操作失败，该角色不存在");
		} else {
			String[] userIdArr = userIds.split(","); // 获得userId数组

			Long userCount = userRoleMapper.queryUserCountByUserIds(userIdArr);
			if (userCount != userIdArr.length) { // 查询的数目和id数目不一致
				return AosResult.retFailureMsg("操作失败，部分用户不存在");
			} else {
				// 先删除原先的绑定数据
				int delResult = userRoleMapper.deleteUserRoleByUserIds(userIdArr);
				ManageUserRole manageUserRole = new ManageUserRole();
				manageUserRole.setRole(role);
				for (String userId : userIdArr) {
					manageUserRole.setId(UUIDGenerator.generate());
					manageUserRole.setUserId(userId);
					int insertResult = userRoleMapper.insertUserRole(manageUserRole);
				}
				return AosResult.retSuccessMsg("操作成功");
			}
		}
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserRoleByUserIds(String[] ids) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteUserRoleByUserIds(ids);
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserRoleByIds(String id) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteUserRoleByIds(id);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult deleteUserRoles(String ids) {
		if (StringAndNumberUtil.isNullAfterTrim(ids)) { // 传进来的参数为空
			return AosResult.retFailureMsg("参数错误");
		} else {
			String[] userIdArr = ids.split(",");
			int result = userRoleMapper.deleteUserRoles(userIdArr);
			return AosResult.retSuccessMsg("操作成功");
		}
	}

	@Override
	@Logger(printSQL = true)
	public int insertUserRole(ManageUserRole manageUserRole) {
		// TODO Auto-generated method stub
		return userRoleMapper.insertUserRole(manageUserRole);
	}

}
