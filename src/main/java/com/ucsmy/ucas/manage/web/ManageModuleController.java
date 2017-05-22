package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageModule;
import com.ucsmy.ucas.manage.ext.ModuleTreePojo;
import com.ucsmy.ucas.manage.service.ManageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenqilin on 2017/4/13.
 * 菜单管理
 */
@RestController
@RequestMapping("module")
public class ManageModuleController {

    private final String PARENT_ID_EMPTY = "菜单父级ID不能为空";
    private final String ADD_DATA_EMPTY = "此菜单没有数据，不能添加子节点！";
    private final String ADD_FAIL = "添加菜单失败，请检查网络";
    private final String ADD_SUCCESS = "添加成功";

    private final String ID_EMPTY = "菜单ID不能为空";
    private final String UPDATE_DATA_EMPTY = "数据库中无菜单项！修改失败";
    private final String UPDATE_FAIL = "更新菜单失败，请检查网络";
    private final String UPDATE_SUCCESS = "修改成功";

    private final String DELETE_DATA_EMPTY = "数据库中无菜单项！删除失败";
    private final String HAS_CHILDREN = "此菜单存在子节点，不能删除";
    private final String DELETE_FAIL = "删除菜单失败，请检查网络";
    private final String DELETE_SUCCESS = "删除成功";

    @Autowired
    private ManageModuleService manageModuleService;

    @RequestMapping("list")
    public AosResult getModuleList() {
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByCondition("");
        return AosResult.retSuccessMsg("success", resultList);
    }

    @RequestMapping("add")
    public AosResult addModule(ManageModule module) {
        if (StringUtils.isEmpty(module.getParentId())) {
            return AosResult.retFailureMsg(PARENT_ID_EMPTY);
        }
        ModuleTreePojo parentModule = manageModuleService.getModuleDetail(module.getParentId());
        if (parentModule == null) {
            return AosResult.retFailureMsg(ADD_DATA_EMPTY);
        }
        module.setModuleId(UUIDGenerator.generate());
        int resultCode = manageModuleService.addModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(ADD_FAIL);
        }
        return AosResult.retSuccessMsg(ADD_SUCCESS, null);
    }

    @RequestMapping("update")
    public AosResult updateModule(ManageModule module, String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(ID_EMPTY);
        }
        ModuleTreePojo oldModule = manageModuleService.getModuleDetail(id);
        if (oldModule == null) {
            return AosResult.retFailureMsg(UPDATE_DATA_EMPTY);
        }
        module.setModuleId(id);
        int resultCode = manageModuleService.updateModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(UPDATE_FAIL);
        }
        return AosResult.retSuccessMsg(UPDATE_SUCCESS, null);
    }

    @RequestMapping("delete")
    public AosResult deleteModule(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(ID_EMPTY);
        }
        ModuleTreePojo module = manageModuleService.getModuleDetail(id);
        if (module == null) {
            return AosResult.retFailureMsg(DELETE_DATA_EMPTY);
        }
        int childNum = manageModuleService.getChildrenNum(id);
        if (childNum > 0) {
            return AosResult.retFailureMsg(HAS_CHILDREN);
        }
        int resultCode = manageModuleService.deleteModule(id);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(DELETE_FAIL);
        }
        return AosResult.retSuccessMsg(DELETE_SUCCESS, null);
    }

}
