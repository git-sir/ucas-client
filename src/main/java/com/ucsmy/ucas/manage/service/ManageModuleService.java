package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.config.shiro.ShiroRealmImpl;
import com.ucsmy.ucas.manage.entity.ManageModule;
import com.ucsmy.ucas.manage.ext.MainModulePojo;
import com.ucsmy.ucas.manage.ext.ModuleTreePojo;

import java.util.List;

/**
 * Created by chenqilin on 2017/4/13.
 */
public interface ManageModuleService {

    /**
     * 根据条件查询
     * @param name 菜单名称
     * @param parentId 父节点ID
     * @param excludeId 排除的节点
     * @return
     */
    List<ModuleTreePojo> getModuleListByCondition(String name, String parentId, String excludeId);

    List<ModuleTreePojo> getModuleListByName(String name);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    ModuleTreePojo getModuleDetail(String id);

    int addModule(ManageModule module);

    int updateModule(ManageModule module);

    int getChildrenNum(String id);

    int deleteModule(String id);

    List<MainModulePojo> queryMainModuleByUser(ShiroRealmImpl.LoginUser user);
}
