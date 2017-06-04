package com.ucsmy.ucas.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.tree.TreeTool;
import com.ucsmy.commons.utils.JsonUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.manage.dao.ManageModuleMapper;
import com.ucsmy.ucas.manage.dao.ManageRolePermissionMapper;
import com.ucsmy.ucas.manage.entity.ManageRoleModule;
import com.ucsmy.ucas.manage.entity.ManageRolePermission;
import com.ucsmy.ucas.manage.ext.ModulePermissionPojo;
import com.ucsmy.ucas.manage.ext.ModuleTreePojo;
import com.ucsmy.ucas.manage.ext.PermissionPojo;
import com.ucsmy.ucas.manage.service.ManageRolePermissionService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageRolePermissionServiceImpl implements ManageRolePermissionService {
	
	@Autowired
	ManageRolePermissionMapper manageRolePermissionMapper;
	@Autowired
    ManageModuleMapper manageModuleMapper;

	@Override
	@Logger(printSQL = true)
	public String queryAllModulePermission(String roleId) throws JsonProcessingException {
		return getTreeTable(roleId);
	}

	@Logger(printSQL = true)
	@Transactional(rollbackFor = Exception.class)
	public String addRolePermission(String roleId,String permissionsId,String name){
		
		boolean success = false;
		StringBuilder msg = new StringBuilder();
		
		if(roleId != null && !"".equals(roleId.trim())){
			if(permissionsId != null && !"".equals(permissionsId.trim())){
				//删除角色功能				
				manageRolePermissionMapper.deleteRolePermissionByRoleID(roleId);
				//删除角色菜单
				manageRolePermissionMapper.deleteRoleModuleByRoleID(roleId);

				String[] module_permission_ID =  splitModuleIDAndPermissionID(permissionsId);
				//查询所有菜单				
				List<ModuleTreePojo> mudule_list = manageModuleMapper.getModuleList(null, null, null);
				//迭代所有菜单的父菜单编号

				String sModuleIdList = getParentIdByID(mudule_list,module_permission_ID[0].substring(0,module_permission_ID[0].length()-1));
				
				List<ManageRoleModule> roleModuleList = setRoleModule(roleId,sModuleIdList.substring(0,sModuleIdList.length()-1));
				
				List<ManageRolePermission> rolePermissionList = setRolePermission(roleId,module_permission_ID[1].substring(0,module_permission_ID[1].length()-1));
				
				manageRolePermissionMapper.insertRoleModuleByBatch(roleModuleList);
				int permission_count = manageRolePermissionMapper.insertRolePermissionByBatch(rolePermissionList);
				if(permission_count > 0 ){
					success = true;
					msg.append("权限分配成功！");
				}else{
					msg.append("权限分配失败！");
				}
			}
		}else{
			msg.append("请选择需要分配的权限！");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role_id", roleId);
		jsonObject.put("success", success);
		jsonObject.put("msg", msg);
		jsonObject.put("name", name);
		return jsonObject.toString();
	}

	@Logger(printSQL = true)
	public String getTreeTable(String roleId) throws JsonProcessingException {
		
		//查询菜单列表
		List<ModulePermissionPojo>  modulePermissionList = manageRolePermissionMapper.queryAllModulePermission();
		//查询角色已经分配的权限
		
		List<ManageRolePermission> rolePermissionList = manageRolePermissionMapper.queryRolePermissionByRoleID(roleId);
		
		String rolePermissions = JsonUtils.formatObjectToJson(TreeTool.getTreeList(setSortList(modulePermissionList,rolePermissionList)));
		
		return rolePermissions;
	}
		
	/**
	 * 判断角色所拥有的功能权限
	 * @param modulePermissionList  所有功能权限列表
	 * @param rolePermissionList	  角色功能列表
	 * @return
	 */
	@Logger(printSQL = true)
	public static List<ModulePermissionPojo> setSortList(List<ModulePermissionPojo> modulePermissionList, List<ManageRolePermission> rolePermissionList){
		for(ModulePermissionPojo modulePermissionBean: modulePermissionList){
			List<PermissionPojo> permissionList = modulePermissionBean.getPermissionList();
			if(permissionList != null && !permissionList.isEmpty()){
				for(PermissionPojo  permissionVo: permissionList){
					if(rolePermissionList != null && !rolePermissionList.isEmpty()){
						for(ManageRolePermission rolePermissionBean : rolePermissionList){
							if(permissionVo.getPermissionId().equals(rolePermissionBean.getPermissionId())){
								permissionVo.setCheched(true);
								break;
							}
						}
					}
				}
				LinkedList<PermissionPojo> linkedList = setLinkedList(permissionList);
				modulePermissionBean.getPermissionList().clear();
				modulePermissionBean.setPermissionList(linkedList);
			}
		}
		return modulePermissionList;
	}
	
	/**
	 * 安装{查询，新增，修改，删除}的方式排序
	 * @param permissionList
	 * @return
	 */
	@Logger(printSQL = true)
	public static LinkedList<PermissionPojo> setLinkedList(List<PermissionPojo> permissionList){
		
		LinkedList<PermissionPojo> linkedList = new LinkedList<>();
		
		WeakHashMap<String,PermissionPojo> map = new WeakHashMap<>();
		int count = 5;
		for(PermissionPojo bean: permissionList){
			if("新增".equals(bean.getPermissionName().trim())){
				map.put("2", bean);
			}else if("查询".equals(bean.getPermissionName().trim())){
//				bean.setCheched(true);
				map.put("1", bean);
			}else if("修改".equals(bean.getPermissionName().trim())){
				map.put("3", bean);
			}else if("删除".equals(bean.getPermissionName().trim())){
				map.put("4", bean);
			}else{
				map.put(String.valueOf(count), bean);
				count++;
			}
		}
		Object[] key =  map.keySet().toArray();    
		Arrays.sort(key);
		for(int i = 0; i<key.length; i++)  
		{    
			linkedList.add(map.get(key[i]));    
		}
		return linkedList;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 拆分菜单编号和功能编号
	 * @param permissionsId
	 * @return  [0]:菜单编号；[1]:功能编号
	 */
	@Logger(printSQL = true)
	protected String[] splitModuleIDAndPermissionID(String permissionsId){
		String[] permission_id = permissionsId.split(",");
		
		Set<String> moduleSet = new HashSet<>();
		Set<String> permissionSet = new HashSet<>();
		
		for(String mPID : permission_id){
			String moduleID = mPID.substring(0,mPID.indexOf('_'));
			String permissionID = mPID.substring(mPID.indexOf('_') + 1);
			
			permissionSet.add(permissionID);
			if(!moduleSet.contains(moduleID)) {
				moduleSet.add(moduleID);
			}
			
		}
		StringBuilder sModuleID = new StringBuilder();
		for(String moduleId : moduleSet) {
			sModuleID.append(moduleId);
			sModuleID.append(",");
		}
		StringBuilder sPermissionID = new StringBuilder();
		for(String permissionId : permissionSet) {
			sPermissionID.append(permissionId);
			sPermissionID.append(",");
		}
		
		return new String[]{sModuleID.toString(),sPermissionID.toString()};
	}
	/**
	 * 遍历页面流传来的所有菜单ID的父菜单
	 * @param muduleList		菜单集合
	 * @param sModuleID		菜单编号组合
	 * @return
	 */
	@Logger(printSQL = true)
	protected String getParentIdByID(List<ModuleTreePojo> muduleList,String sModuleID){
		String[] s_moduleIDs = sModuleID.split(",");
		Set<String> set = new HashSet<>();
		for(String moduleID : s_moduleIDs){
			set.add(moduleID);
			IterationModuleID(muduleList,moduleID,set);
		}
		
		StringBuilder str = new StringBuilder();
		for(String id: set) {
			str.append(id);
			str.append(",");
		}
		
		return str.toString();
	}
	/**
	 * 根据菜单ID迭代出该菜单的所有父菜单
	 * @param moduleList		菜单集合
	 * @param moduleID			菜单编号
	 * @param ids				返回数据
	 * @return
	 */
	@Logger(printSQL = true)
	public void IterationModuleID(List<ModuleTreePojo> moduleList,String moduleID,Set<String> ids){
		for(ModuleTreePojo bean : moduleList){
			if(moduleID.equals(bean.getId().trim())){
				if(bean.getParentId() != null) {
					String parentId = bean.getParentId().trim();
					if(!"".equals(parentId) && !"0".equals(parentId)) {
						if(!ids.contains(parentId)) {
							ids.add(parentId);
						} 
						IterationModuleID(moduleList,bean.getParentId().trim(),ids);
					}
				}
			}
		}
	}
	
	/**
	 * 封装角色添加的功能权限
	 * @param roleId      角色编号
	 * @param permissionsId	功能编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRolePermission> setRolePermission(String roleId, String permissionsId){
		String[] permissionId = permissionsId.split(",");
		List<ManageRolePermission> list = new ArrayList<ManageRolePermission>();
		for(String str : permissionId){
			ManageRolePermission bean = new ManageRolePermission();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(roleId);
			bean.setPermissionId(str);
			list.add(bean);
		}
		return list;
	}
	/**
	 * 封装角色菜单对于关系
	 * @param roleId 角色编号
	 * @param moduleIdList 菜单编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String roleId, List<String> moduleIdList){
		List<ManageRoleModule> list = new ArrayList<>();
		for(String moduleIds : moduleIdList){
			String[] moduleId = moduleIds.substring(0, moduleIds.length()-1).split(",");
			for(String str : moduleId){
				ManageRoleModule bean = new ManageRoleModule();
				bean.setId(UUIDGenerator.generate());
				bean.setRoleId(roleId);
				bean.setModuleId(str);
				list.add(bean);
			}
		}
		return list;
	}

	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String roleId, String moduleIdList){
		List<ManageRoleModule> list = new ArrayList<>();
//		for(String module_ids : moduleId_list){
		String[] moduleId = moduleIdList.split(",");
		for(String str : moduleId){
			ManageRoleModule bean = new ManageRoleModule();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(roleId);
			bean.setModuleId(str);
			list.add(bean);
		}
//		}
		return list;
	}

}
