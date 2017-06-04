package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.CommMessage;
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

    private final static String PARENT_ID_EMPTY = "菜单父级ID不能为空";
    private final static String ADD_DATA_EMPTY = "此菜单没有数据，不能添加子节点！";
    private final static String ID_EMPTY = "菜单ID不能为空";
    private final static String DELETE_DATA_EMPTY = "数据库中无菜单项！删除失败";
    private final static String HAS_CHILDREN = "此菜单存在子节点，不能删除";
    private final static String HAS_SAME_NAME = "该父节点下存在同名子节点";

    @Autowired
    private ManageModuleService manageModuleService;

    @RequestMapping("list")
    public AosResult getModuleList() {
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByName("");
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
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByCondition(module.getName()
                , module.getParentId(), module.getModuleId());
        if (!resultList.isEmpty()) {
            return AosResult.retFailureMsg(HAS_SAME_NAME);
        }
        int resultCode = manageModuleService.addModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS, null);
    }

    @RequestMapping("update")
    public AosResult updateModule(ManageModule module, String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(ID_EMPTY);
        }
        ModuleTreePojo oldModule = manageModuleService.getModuleDetail(id);
        if (oldModule == null) {
            return AosResult.retFailureMsg(CommMessage.DATA_NOT_EXIST);
        }
        module.setModuleId(id);
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByCondition(module.getName()
                , module.getParentId(), module.getModuleId());
        if (!resultList.isEmpty()) {
            return AosResult.retFailureMsg(HAS_SAME_NAME);
        }
        int resultCode = manageModuleService.updateModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS, null);
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
            return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
    }

}
