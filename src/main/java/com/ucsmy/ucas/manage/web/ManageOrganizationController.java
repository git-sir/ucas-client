package com.ucsmy.ucas.manage.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.tree.TreeTool;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.manage.entity.ManageOrganization;
import com.ucsmy.ucas.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.ucas.manage.ext.UcasClientUserProfileWithOrganization;
import com.ucsmy.ucas.manage.service.ManageOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@Controller
@RequestMapping("organization")
public class ManageOrganizationController {
    @Autowired
    ManageOrganizationService manageOrganizationService;

    private final static String MESSAGE_ZJ_ID_NULL = "组织 ID 为空";
    private final static String MESSAGE_EXISTS_CHILDREN = "该节点存在下级子节点";
    private final static String MESSAGE_BIND_SUCCESS = "成功绑定";
    private final static String MESSAGE_ORGANIZATION_ID_NULL = "组织 ID 为空";
    private final static String MESSAGE_UESR_IDS = "没有选择用户";
    private final static String MESSAGE_SAME_NAME = "该父节点下存在相同名称的节点";
    private final static String MESSAGE_ID_NULL = "ID 为空";


    //组织列表业务逻辑
    @RequestMapping("queryOrganization")
    @ResponseBody
    public AosResult queryOrganization() throws JsonProcessingException {
        List<ManageOrganization> manageOrganizationList = manageOrganizationService.getOrganizationList();
        if (null == manageOrganizationList) {
            return AosResult.retFailureMsg(CommMessage.COMMON_FAILURE);
        } else if (manageOrganizationList.isEmpty()) {
            return AosResult.retSuccessMsg(CommMessage.DATA_NOT_EXIST, TreeTool.getTreeList(manageOrganizationList));
        } else {
            return AosResult.retSuccessMsg(CommMessage.COMMON_SUCCESS, TreeTool.getTreeList(manageOrganizationList));
        }
    }

    //组织详细业务逻辑
    @RequestMapping("getOrganization")
    @ResponseBody
    public AosResult getOrganization(String id) throws JsonProcessingException {
        if (StringAndNumberUtil.isNullAfterTrim(id)) {
            return AosResult.retFailureMsg(MESSAGE_ID_NULL, null);
        } else {
            ManageOrganization organization = manageOrganizationService.getOrganizationById(id);
            if (null == organization) {
                return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE, null);
            } else {
                return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, organization);
            }
        }
    }

    //组织更新业务逻辑
    @RequestMapping("editOrganization")
    @ResponseBody
    public AosResult editOrganization(ManageOrganization manageOrganization) {
        String errorMsg = HibernateValidateUtils.getErrors(manageOrganization);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        }
        if (StringAndNumberUtil.isNullAfterTrim(manageOrganization.getId())) {
            return AosResult.retFailureMsg(MESSAGE_ZJ_ID_NULL);
        }
        List<ManageOrganization> sameNameList = manageOrganizationService.queryOrganizationByCondition(manageOrganization);
        if (!sameNameList.isEmpty()) {
            return AosResult.retFailureMsg(MESSAGE_SAME_NAME);
        }
        int updateCount = manageOrganizationService.updateOrganization(manageOrganization);
        if (updateCount > 0) {
            return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS, null);
        } else {
            return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
        }
    }

    //组织添加业务逻辑
    @RequestMapping("addOrganization")
    @ResponseBody
    public AosResult addOrganization(ManageOrganization manageOrganization) {
        String errorMsg = HibernateValidateUtils.getErrors(manageOrganization);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        }
        manageOrganization.setId(UUIDGenerator.generate());
        List<ManageOrganization> sameNameList = manageOrganizationService.queryOrganizationByCondition(manageOrganization);
        if (!sameNameList.isEmpty()) {
            return AosResult.retFailureMsg(MESSAGE_SAME_NAME);
        }
        int insertCount = manageOrganizationService.insertOrganization(manageOrganization);
        if (insertCount > 0) {
            return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS, null);
        } else {
            return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
        }
    }

    //组织删除业务逻辑
    @RequestMapping("deleteOrganization")
    @ResponseBody
    public AosResult deleteOrganization(String id) {
        if (this.isExistChildren(id)) {
            return AosResult.retFailureMsg(MESSAGE_EXISTS_CHILDREN);
        } else {
            int deleteCount = manageOrganizationService.deleteOrganization(id);
            if (deleteCount > 0) {
                return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
            } else {
                return AosResult.retSuccessMsg(CommMessage.DELETE_FAILURE, null);
            }
        }
    }

    //组织绑定业务逻辑
    @RequestMapping("bindOrganization")
    @ResponseBody
    public AosResult bindOrganization(String organizationId, String userId) {
        if (StringAndNumberUtil.isNullAfterTrim(organizationId)) {
            return AosResult.retFailureMsg(MESSAGE_ORGANIZATION_ID_NULL);
        } else {
            if (StringAndNumberUtil.isNullAfterTrim(userId)) {
                return AosResult.retFailureMsg(MESSAGE_UESR_IDS);
            } else {
                this.deleteBatchByUserIds(userId);
                this.insertBatch(userId, organizationId);
                return AosResult.retSuccessMsg(MESSAGE_BIND_SUCCESS, null);
            }
        }
    }

    //组织解绑业务逻辑
    @RequestMapping("unbindOrganization")
    @ResponseBody
    public AosResult unbindOrganization(String organizationId, String userId) {
        if (StringAndNumberUtil.isNullAfterTrim(organizationId)) {
            return AosResult.retFailureMsg("组织 ID 为空");

        } else {
            if (StringAndNumberUtil.isNullAfterTrim(userId)) {
                return AosResult.retFailureMsg("没有选择用户");
            } else {
                this.deleteBatch(userId, organizationId);
                return AosResult.retSuccessMsg("成功解绑", null);
            }
        }
    }

    //绑定组织的用户业务逻辑
    @RequestMapping("queryUserWithOrganization")
    @ResponseBody
    public PageInfo<UcasClientUserProfileWithOrganization> queryUserWithOrganization(@RequestParam(required = true) String id
            , String name, String account
            , @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        return manageOrganizationService.queryUserWithOrganization(id, name, account, pageNum, pageSize);
    }

    //没有绑定组织的用户业务逻辑
    @RequestMapping("queryUserWithoutOrganization")
    @ResponseBody
    public PageInfo<UcasClientUserProfileWithOrganization> queryUserWithoutOrganization(@RequestParam(required = true) String id
            , String name, String account
            , @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        return manageOrganizationService.queryUserWithoutOrganization(id, name, account, pageNum, pageSize);
    }

    private void deleteBatch(String userIds, String organizationId) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            organizationUser.setOrganizationId(organizationId);
            list.add(organizationUser);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("organizationId", organizationId);
        manageOrganizationService.deleteBatch(map);
    }

    private void deleteBatchByUserIds(String userIds) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            list.add(organizationUser);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        manageOrganizationService.deleteBatchByUserIds(map);
    }

    private void insertBatch(String userIds, String organizationId) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            organizationUser.setOrganizationId(organizationId);
            list.add(organizationUser);
        }
        manageOrganizationService.insertBatch(list);
    }

    private boolean isExistChildren(String id) {
        Integer count = manageOrganizationService.countChildren(id);
        if (null == count || count <= 0) {
            return false;
        }
        return true;
    }
}
