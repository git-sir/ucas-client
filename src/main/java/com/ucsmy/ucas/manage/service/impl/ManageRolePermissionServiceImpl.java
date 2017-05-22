package com.ucsmy.ucas.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

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
	public String queryAllModulePermission(String role_id) throws Exception {		
		return getTreeTable(role_id);
	}

	@Override
	@Logger(printSQL = true)
	public ManageRolePermission queryRolePermissionByRoleID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Logger(printSQL = true)
	public int insertRolePermissionByBatch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int insertRoleModuleByBatch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int deleteRolePermissionByRoleID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int deleteRoleModuleByRoleID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Logger(printSQL = true)
	@Transactional(rollbackFor = Exception.class)
	public String addRolePermission(String role_id,String permissions_id,String name){
		
		boolean success = false;
		StringBuilder msg = new StringBuilder();
		
		if(role_id != null && !"".equals(role_id.trim())){
			if(permissions_id != null && !"".equals(permissions_id.trim())){
				//删除角色功能				
				int del_permission = manageRolePermissionMapper.deleteRolePermissionByRoleID(role_id);
				//logger.debug("->AddRolePermissionStep", "删除角色："+role_id+"的权限数量：" + del_permission);
				//删除角色菜单				
				int del_module = manageRolePermissionMapper.deleteRoleModuleByRoleID(role_id);
				//logger.debug("->AddRolePermissionStep", "删除角色："+role_id+"的菜单数量：" + del_module);
				
				//[0]:module_id;[1]:permission_id
				String[] module_permission_ID =  splitModuleIDAndPermissionID(permissions_id);
				//查询所有菜单				
				List<ModuleTreePojo> mudule_list = manageModuleMapper.getModuleList(null, null);
				//迭代所有菜单的父菜单编号
//				List<String> moduleId_list = getParentIdByID(mudule_list,module_permission_ID[0].substring(0,module_permission_ID[0].length()-1));
				
				String s_moduleId_list = getParentIdByID(mudule_list,module_permission_ID[0].substring(0,module_permission_ID[0].length()-1));
				
				List<ManageRoleModule> roleModule_list = setRoleModule(role_id,s_moduleId_list.substring(0,s_moduleId_list.length()-1));
				
				List<ManageRolePermission> rolePermission_list = setRolePermission(role_id,module_permission_ID[1].substring(0,module_permission_ID[1].length()-1));
				
				int module_count = manageRolePermissionMapper.insertRoleModuleByBatch(roleModule_list);
				//logger.debug("->AddRolePermissionStep", "添加角色："+role_id+"的菜单数量：" + module_count);				
				int permission_count = manageRolePermissionMapper.insertRolePermissionByBatch(rolePermission_list);
				//logger.debug("->AddRolePermissionStep", "添加角色："+role_id+"的功能数量：" + permission_count);
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
		jsonObject.put("role_id", role_id);
		jsonObject.put("success", success);
		jsonObject.put("msg", msg);
		jsonObject.put("name", name);
		return jsonObject.toString();
	}

	@Logger(printSQL = true)
	public String getTreeTable(String role_id) throws Exception {
		
		//查询菜单列表
		List<ModulePermissionPojo>  modulePermission_list = manageRolePermissionMapper.queryAllModulePermission();
		//查询角色已经分配的权限
		
		List<ManageRolePermission> rolePermission_list = manageRolePermissionMapper.queryRolePermissionByRoleID(role_id);
		
		String rolePermissions = JsonUtils.formatObjectToJson(TreeTool.getTreeList(setSortList(modulePermission_list,rolePermission_list)));
		
		return rolePermissions;
	}
		
	/**
	 * 判断角色所拥有的功能权限
	 * @param modulePermission_list  所有功能权限列表
	 * @param rolePermission_list	  角色功能列表
	 * @return
	 */
	@Logger(printSQL = true)
	public static List<ModulePermissionPojo> setSortList(List<ModulePermissionPojo>  modulePermission_list,List<ManageRolePermission> rolePermission_list){
		for(ModulePermissionPojo modulePermissionBean: modulePermission_list){
			List<PermissionPojo> permissionList = modulePermissionBean.getPermissionList();
			if(permissionList != null && permissionList.size() > 0){
				for(PermissionPojo  permissionVo: permissionList){
					if(rolePermission_list != null && rolePermission_list.size() > 0){
						for(ManageRolePermission rolePermissionBean : rolePermission_list){
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
		return modulePermission_list;
	}
	
	/**
	 * 安装{查询，新增，修改，删除}的方式排序
	 * @param permissionList
	 * @return
	 */
	@Logger(printSQL = true)
	public static LinkedList<PermissionPojo> setLinkedList(List<PermissionPojo> permissionList){
		
		LinkedList<PermissionPojo> linkedList = new LinkedList<PermissionPojo>();
		
		WeakHashMap<String,PermissionPojo> map = new WeakHashMap<String,PermissionPojo>();
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
	 * @param permissions_id
	 * @return  [0]:菜单编号；[1]:功能编号
	 */
	@Logger(printSQL = true)
	protected String[] splitModuleIDAndPermissionID(String permissions_id){
		String[] permission_id = permissions_id.split(",");
		
		Set<String> moduleSet = new HashSet<>();
		Set<String> permissionSet = new HashSet<>();
		
		for(String m_p_ID : permission_id){
			String moduleID = m_p_ID.substring(0,m_p_ID.indexOf("_"));
			String permissionID = m_p_ID.substring(m_p_ID.indexOf("_") + 1);
			
			permissionSet.add(permissionID);
			if(!moduleSet.contains(moduleID)) {
				moduleSet.add(moduleID);
			}
			
		}
		StringBuilder s_moduleID = new StringBuilder();
		for(String moduleId: moduleSet) {
			s_moduleID.append(moduleId);
			s_moduleID.append(",");
		}
		StringBuilder s_permissionID = new StringBuilder();
		for(String permissionId: permissionSet) {
			s_permissionID.append(permissionId);
			s_permissionID.append(",");
		}
		
		return new String[]{s_moduleID.toString(),s_permissionID.toString()};
	}
	/**
	 * 遍历页面流传来的所有菜单ID的父菜单
	 * @param mudule_list		菜单集合
	 * @param s_moduleID		菜单编号组合
	 * @return
	 */
	@Logger(printSQL = true)
	protected String getParentIdByID(List<ModuleTreePojo> mudule_list,String s_moduleID){
		String[] s_moduleIDs = s_moduleID.split(",");
		Set<String> set = new HashSet<>();
		for(String moduleID : s_moduleIDs){
			set.add(moduleID);
			IterationModuleID(mudule_list,moduleID,set);
		}
		
		StringBuffer str = new StringBuffer();
		for(String id: set) {
			str.append(id);
			str.append(",");
		}
		
		return str.toString();
	}
	/**
	 * 根据菜单ID迭代出该菜单的所有父菜单
	 * @param module_list		菜单集合
	 * @param moduleID			菜单编号
	 * @param ids				返回数据
	 * @return
	 */
	@Logger(printSQL = true)
	public void IterationModuleID(List<ModuleTreePojo> module_list,String moduleID,Set<String> ids){
		for(ModuleTreePojo bean : module_list){
			if(moduleID.equals(bean.getId().trim())){
				if(bean.getParentId() != null) {
					String parentId = bean.getParentId().trim();
					if(!"".equals(parentId) && !"0".equals(parentId)) {
						if(!ids.contains(parentId)) {
							ids.add(parentId);
						} 
						IterationModuleID(module_list,bean.getParentId().trim(),ids);
					}
				}
			}
		}
	}
	
	/**
	 * 封装角色添加的功能权限
	 * @param role_id      角色编号
	 * @param permissions_id	功能编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRolePermission> setRolePermission(String role_id, String permissions_id){
		String[] permission_id = permissions_id.split(",");
		List<ManageRolePermission> list = new ArrayList<ManageRolePermission>();
		for(String str : permission_id){
			ManageRolePermission bean = new ManageRolePermission();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(role_id);
			bean.setPermissionId(str);
			list.add(bean);
		}
		return list;
	}
	/**
	 * 封装角色菜单对于关系
	 * @param moduleId_list 角色编号
	 * @param module_ids 菜单编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String role_id, List<String> moduleId_list){
		List<ManageRoleModule> list = new ArrayList<ManageRoleModule>();
		for(String module_ids : moduleId_list){
			String[] module_id = module_ids.substring(0, module_ids.length()-1).split(",");
			for(String str : module_id){
				ManageRoleModule bean = new ManageRoleModule();
				bean.setId(UUIDGenerator.generate());
				bean.setRoleId(role_id);
				bean.setModuleId(str);
				list.add(bean);
			}
		}
		return list;
	}

	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String role_id, String moduleId_list){
		List<ManageRoleModule> list = new ArrayList<ManageRoleModule>();
//		for(String module_ids : moduleId_list){
		String[] module_id = moduleId_list.split(",");
		for(String str : module_id){
			ManageRoleModule bean = new ManageRoleModule();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(role_id);
			bean.setModuleId(str);
			list.add(bean);
		}
//		}
		return list;
	}

}
