package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.service.ManageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/13.
 */
@Controller
@RequestMapping("config")
public class ManageConfigController {
    private final static String MESSAGE_DUPLICATE = "已存在相同名称的参数";

    private final static String MESSAGE_ID_NOT_EXIST = "参数不存在";
    private final static String MESSAGE_NAME_EXIST = "修改后的参数名称已存在";

    private final static String MESSAGE_ID_NULL = "参数的 ID 为空";

    @Autowired
    private ManageConfigService manageConfigService;

    @RequestMapping("queryConfig")
    @ResponseBody
    public PageInfo<ManageConfig> queryConfig(@RequestParam(required = false) String paramKey, @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        return manageConfigService.queryConfig(paramKey, pageNum, pageSize);
    }

    @RequestMapping("getConfig")
    @ResponseBody
    public ManageConfig getConfig(String id) {
        return manageConfigService.getConfig(id);
    }

    @RequestMapping("addConfig")
    @ResponseBody
    public AosResult addConfig(ManageConfig manageConfig) {
        manageConfig.setId(UUIDGenerator.generate());
        String errorMsg = HibernateValidateUtils.getErrors(manageConfig);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        } else {
            if (isParamKeyExist(manageConfig.getParamKey())) {
                return AosResult.retFailureMsg(MESSAGE_DUPLICATE);
            } else {
                int insertCount = manageConfigService.addConfig(manageConfig);
                if (insertCount > 0) {
                    return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS, null);
                } else {
                    return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
                }
            }
        }
    }

    @RequestMapping("editConfig")
    @ResponseBody
    public AosResult editConfig(ManageConfig manageConfig) {
        String errorMsg = HibernateValidateUtils.getErrors(manageConfig);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        } else {
            if (!isIdExist(manageConfig.getId())) {
                return AosResult.retFailureMsg(MESSAGE_ID_NOT_EXIST);
            } else if (isKeyExist(manageConfig.getId(), manageConfig.getParamKey())) {
                return AosResult.retFailureMsg(MESSAGE_NAME_EXIST);
            } else {
                int updateCount = manageConfigService.editConfig(manageConfig);
                if (updateCount > 0) {
                    return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS, null);
                } else {
                    return AosResult.retFailureMsg(CommMessage.UPDATE_FAILURE);
                }
            }
        }
    }

    @RequestMapping("deleteConfig")
    @ResponseBody
    public AosResult deleteConfig(String id) {
        if (StringAndNumberUtil.isNullAfterTrim(id)) {
            return AosResult.retFailureMsg(MESSAGE_ID_NULL);
        } else {
            int deleteCount = manageConfigService.deleteConfig(id);
            if (deleteCount > 0) {
                return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
            } else {
                return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
            }
        }
    }

    private boolean isParamKeyExist(String paramKey) {
        ManageConfig config = manageConfigService.queryByName(paramKey);
        if (null != config) {
            return true;
        }
        return false;
    }

    private boolean isIdExist(String id) {
        ManageConfig config = manageConfigService.getConfig(id);
        if (null != config) {
            return true;
        }
        return false;
    }

    private boolean isKeyExist(String id, String paramKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("paramKey", paramKey);
        int count = manageConfigService.isKeyExist(map);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
