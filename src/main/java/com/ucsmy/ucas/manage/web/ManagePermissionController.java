package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.manage.entity.ManagePermission;
import com.ucsmy.ucas.manage.service.ManagePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理
 * Created by chenqilin on 2017/4/14.
 */
@RestController
@RequestMapping("permission")
public class ManagePermissionController {

    private final static String MODULE_ID_EMPTY = "菜单ID不能为空";

    private final static String PERMISSION_ID_EMPTY = "权限ID不能为空";

    @Autowired
    private ManagePermissionService managePermissionService;

    @RequestMapping("list")
    public AosResult queryPermissionListByModuleId(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(MODULE_ID_EMPTY);
        }
        List<ManagePermission> permissions = managePermissionService.queryPermissionByModuleID(id);
        if (permissions == null || permissions.isEmpty()) {
            return AosResult.retFailureMsg(CommMessage.DATA_EMPTY);
        }
        return AosResult.retSuccessMsg(CommMessage.COMMON_SUCCESS, permissions);
    }

    @RequestMapping("add")
    public AosResult addPermission(ManagePermission permission) {
        AosResult validate = managePermissionService.validatePermission(permission);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        permission.setPermissionId(UUIDGenerator.generate());
        int resultCode = managePermissionService.addPermission(permission);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS, null);
    }

    @RequestMapping("update")
    public AosResult updatePermission(ManagePermission permission) {
        if (StringUtils.isEmpty(permission.getPermissionId())) {
            return AosResult.retFailureMsg(PERMISSION_ID_EMPTY);
        }
        AosResult validate = managePermissionService.validatePermission(permission);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        ManagePermission existPermission = managePermissionService.getPermissionById(permission.getPermissionId());
        if (existPermission == null) {
            return AosResult.retFailureMsg(CommMessage.DATA_NOT_EXIST);
        }
        int resultCode = managePermissionService.updatePermission(permission);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS, null);
    }

    @RequestMapping("delete")
    public AosResult deletePermission(String permissionId) {
        if (StringUtils.isEmpty(permissionId)) {
            return AosResult.retFailureMsg(PERMISSION_ID_EMPTY);
        }
        int resultCode = managePermissionService.deletePermissionByID(permissionId);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
    }
}
