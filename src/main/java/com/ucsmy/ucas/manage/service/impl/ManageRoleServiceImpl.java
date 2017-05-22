package com.ucsmy.ucas.manage.service.impl;

import java.util.List;

import com.ucsmy.ucas.commons.aop.annotation.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.dao.ManageRoleMapper;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.service.ManageRoleService;

@Service
public class ManageRoleServiceImpl implements ManageRoleService {

	@Autowired
	private ManageRoleMapper roleMapper;

	@Override
	@Logger(printSQL = true)
	public PageInfo<ManageRole> queryRoleList(String name, int pageNum, int pageSize) {
		return roleMapper.queryRoleList(name, new PageRequest(pageNum, pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public List<ManageRole> queryAllRoleList() {
		return roleMapper.queryAllRoleList();
	}

	@Override
	@Logger(printSQL = true)
	public ManageRole queryRoleByName(String name) {
		return roleMapper.queryRoleByName(name);
	}

	@Override
	@Logger(printSQL = true)
	public ManageRole queryRoleById(String roleId) {
		return roleMapper.queryRoleById(roleId);
	}

	@Override
	@Logger(printSQL = true)
	public Long queryRoleUserCountByRoleId(String roleId) {
		return roleMapper.queryRoleUserCountByRoleId(roleId);
	}

	@Override
	@Logger(printSQL = true)
	public String queryRoleMark(String roleId) {
		return roleMapper.queryRoleMark(roleId);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult addRole(ManageRole manageRole) {
		manageRole.setRoleId(UUIDGenerator.generate());
		String errorMsg = HibernateValidateUtils.getErrors(manageRole);
		if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
			return AosResult.retFailureMsg(errorMsg);
		} else {
			ManageRole otherRole = roleMapper.queryRoleByName(manageRole.getName());
			if (otherRole != null) {
				return AosResult.retFailureMsg("已存在相同名称的角色");
			} else {
				int result = roleMapper.addRole(manageRole);
				if (result > 0) {
					return AosResult.retSuccessMsg("添加成功");
				} else {
					return AosResult.retFailureMsg("添加失败");
				}
			}
		}

	}

	@Override
	@Logger(printSQL = true)
	public AosResult updateRole(ManageRole manageRole) {
		String errorMsg = HibernateValidateUtils.getErrors(manageRole);
		if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
			return AosResult.retSuccessMsg(errorMsg, null);
		} else {
			ManageRole oldRole = roleMapper.queryRoleById(manageRole.getRoleId());
			if (oldRole == null) {
				return AosResult.retFailureMsg("该角色不存在");
			} else {
				if (!manageRole.getName().equals(oldRole.getName())) { // 角色名需要更改，要判断是否有相同角色名

					ManageRole otherRole = roleMapper.queryRoleByName(manageRole.getName());
					if (otherRole != null) { // 已存在相同角色名
						return AosResult.retFailureMsg("已存在相同名称的角色");
					}
				}
				
				int result = roleMapper.updateRole(manageRole);
				if (result > 0) {
					return AosResult.retSuccessMsg("修改成功");
				} else {
					return AosResult.retFailureMsg("修改失败");
				}

			}
		}
	}

	@Override
	@Logger(printSQL = true)
	public AosResult deleteRole(String roleId) {
		if (StringAndNumberUtil.isNullAfterTrim(roleId)) {
			return AosResult.retFailureMsg("参数错误");
		}else{			
			Long userRoleCount = roleMapper.queryRoleUserCountByRoleId(roleId);
			if(userRoleCount > 0){			//存在有已经绑定的用户，无法删除				
		        return AosResult.retFailureMsg("该角色已有绑定用户，无法删除");
			}
			else{
				int result = roleMapper.deleteRole(roleId);
				if(result == 0){		//未存在角色，返回结果为0
					return AosResult.retFailureMsg("删除失败");
				}
				else{
					return AosResult.retSuccessMsg("删除成功");
				}
			}
		}
		
		//return roleMapper.deleteRole(roleId);
	}

}
