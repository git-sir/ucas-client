package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.service.UcasUserClientRelService;
import com.ucsmy.ucas.manage.service.UcasUserResRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号授权管理controller
 * Created by chenqilin on 2017/4/25.
 */
@RestController
@RequestMapping("userRel")
public class UcasUserRelManageController {

    private final String OPENID_EMPTY = "openId为空";
    private final String DELETE_SUCCESS = "取消授权成功";
    private final String DELETE_FAIL = "取消授权失败";

    @Autowired
    private UcasUserClientRelService ucasUserClientRelService;

    @Autowired
    private UcasUserResRelService ucasUserResRelService;

    /**
     * 获取账号授权应用列表
     *
     * @param clientName
     * @param accUuid
     * @return
     */
    @RequestMapping("clientList")
    public PageInfo queryUserClientList(String clientName, String accUuid, String openId, int pageNum, int pageSize) {
        return ucasUserClientRelService.queryUserRelClientList(clientName, accUuid, openId, CommStatusEnum.SYS_INUSE.getValue(), pageNum, pageSize);
    }

    /**
     * 根据openId获取授权资源
     *
     * @param openId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("resList")
    public PageInfo queryClientResources(String openId, int pageNum, int pageSize) {
        return ucasUserResRelService.queryUserRelResource(openId, pageNum, pageSize);
    }

    /**
     * 取消openId应用授权
     * @param openId
     * @return
     */
    @RequestMapping("deleteUserRel")
    public AosResult deleteUserRel(String openId) {
        if (StringUtils.isEmpty(openId.trim())) {
            return AosResult.retFailureMsg(OPENID_EMPTY);
        }
        int result = ucasUserClientRelService.deleteUserRelClient(openId);
        if (result > 0) {
            return AosResult.retSuccessMsg(DELETE_SUCCESS);
        }
        return AosResult.retFailureMsg(DELETE_FAIL);
    }

}
