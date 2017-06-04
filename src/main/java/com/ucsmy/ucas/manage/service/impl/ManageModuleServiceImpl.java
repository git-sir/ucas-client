package com.ucsmy.ucas.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.tree.TreeTool;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.config.shiro.ShiroRealmImpl;
import com.ucsmy.ucas.manage.dao.ManageModuleMapper;
import com.ucsmy.ucas.manage.dao.ManagePermissionMapper;
import com.ucsmy.ucas.manage.dao.ManageRoleModuleMapper;
import com.ucsmy.ucas.manage.entity.ManageModule;
import com.ucsmy.ucas.manage.ext.MainModulePojo;
import com.ucsmy.ucas.manage.ext.ModuleTreePojo;
import com.ucsmy.ucas.manage.service.ManageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * module service
 * Created by chenqilin on 2017/4/13.
 */
@Service
public class ManageModuleServiceImpl implements ManageModuleService {

    @Autowired
    private ManageModuleMapper manageModuleMapper;
    @Autowired
    private ManagePermissionMapper managePermissionMapper;
    @Autowired
    private ManageRoleModuleMapper manageRoleModuleMapper;

    @Override
    @SuppressWarnings("unchecked")
    @Logger(operationName="查询菜单", printSQL = true)
    public List<ModuleTreePojo> getModuleListByName(String name) {
        List<ModuleTreePojo> list = manageModuleMapper.getModuleList(name, null, null);
        return (List<ModuleTreePojo>) TreeTool.getTreeList(list);
    }

    @Override
    @Logger(operationName="查询重名菜单", printSQL = true)
    public List<ModuleTreePojo> getModuleListByCondition(String name, String parentId, String excludeId) {
        return manageModuleMapper.getModuleList(name, parentId, excludeId);
    }

    @Override
    @Logger(operationName="查询菜单详情", printSQL = true)
    public ModuleTreePojo getModuleDetail(String id) {
        return manageModuleMapper.getModuleDetail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName="添加菜单", printSQL = true)
    public int addModule(ManageModule module) {
        return manageModuleMapper.addModule(module);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName="更新菜单", printSQL = true)
    public int updateModule(ManageModule module) {
        return manageModuleMapper.updateModule(module);
    }

    @Override
    @Logger(operationName="查询子菜单", printSQL = true)
    public int getChildrenNum(String id) {
        return manageModuleMapper.getChildrenNum(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Logger(operationName="删除菜单", printSQL = true)
    public int deleteModule(String id) {
        managePermissionMapper.deletePermissionByModule(id);
        manageRoleModuleMapper.deleteRoleByModuleId(id);
        return manageModuleMapper.deleteModule(id);
    }

    @Override
    @Logger(operationName="根据用户查询菜单", printSQL = true)
    public List<MainModulePojo> queryMainModuleByUser(ShiroRealmImpl.LoginUser user) {
        List<MainModulePojo> rawList;
        if ("admin".equals(user.getUserName())) {
            rawList = manageModuleMapper.queryMainAllModule();
        } else {
            rawList = manageModuleMapper.queryMainModuleByUserId(user.getId());
        }
        List<MainModulePojo> nodeList = new ArrayList<>();
        for(MainModulePojo node1 : rawList){
            boolean mark = false;
            if (node1.getIcon()==null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("left", "iconfont menu-left-span " + node1.getImage());
                node1.setIcon(jsonObject);
            }
            node1.setHref("javascript:;");
            for(MainModulePojo node2 : rawList){
                if(node1.getParentId() !=null && node1.getParentId().equals(node2.getId())){
                    mark = true;
                    node2.setHref("");
                    node2.setChildShow(true);
                    JSONObject iconJson = new JSONObject();
                    iconJson.put("left", "iconfont menu-left-span "+ node2.getImage());
                    iconJson.put("right", "iconfont menu-right-span");
                    node2.setIcon(iconJson);
                    if(node2.getChildren() == null)
                        node2.setChildren(new ArrayList<MainModulePojo>());
                    node2.getChildren().add(node1);
                    node2.setClassName("icon-project");
                    break;
                }
            }
            if(!mark){
                if(node1.getParentId() == null || "".equals(node1.getParentId().trim()) ||  "0".equals(node1.getParentId().trim())){
                    nodeList.add(node1);
                }
            }
        }
        return nodeList.get(0).getChildren();
    }
}
