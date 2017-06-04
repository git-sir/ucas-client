package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.service.UcasClientGroupService;
import com.ucsmy.ucas.manage.service.UcasClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用组Controller
 * Created by chenqilin on 2017/4/21.
 */
@RestController
@RequestMapping("clientGroup")
public class UcasClientGroupController {

    @Autowired
    private UcasClientGroupService ucasClientGroupService;
    @Autowired
    private UcasClientInfoService ucasClientInfoService;

    /**
     * 列表查询
     * @param groupName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list")
    public PageInfo<UcasClientGroup> queryClientGroup(@RequestParam(required = false)String groupName
            , @RequestParam(required = false) String isSso, String accountGroupName
            , int pageNum, int pageSize) {
        return ucasClientGroupService.queryClientGroup(groupName, isSso, accountGroupName, pageNum, pageSize);
    }

    /**
     * 添加应用组
     * @param ucasClientGroup
     * @return
     */
    @RequestMapping("add")
    public AosResult addClientGroup(UcasClientGroup ucasClientGroup) {
        AosResult validate = ucasClientGroupService.validateClientGroup(ucasClientGroup);
        if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
            return validate;
        }
        return ucasClientGroupService.addClientGroup(ucasClientGroup);
    }

    /**
     * 更新应用组
     * @param ucasClientGroup
     * @return
     */
    @RequestMapping("update")
    public AosResult updateClientGroup(UcasClientGroup ucasClientGroup) {
        AosResult validate = ucasClientGroupService.validateClientGroup(ucasClientGroup);
        if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
            return validate;
        }
        if (StringUtils.isEmpty(ucasClientGroup.getCligUuid())) {
            return AosResult.retFailureMsg("应用组id不能为空");
        }
        return ucasClientGroupService.updateClientGroup(ucasClientGroup);
    }

    /**
     * 删除应用组
     * @param cligUuid
     * @return
     */
    @RequestMapping("delete")
    public AosResult deleteClientGroup(String cligUuid) {
        if (StringUtils.isEmpty(cligUuid)) {
            return AosResult.retFailureMsg("应用组id不能为空");
        }
        return ucasClientGroupService.deleteClientGroup(cligUuid);
    }

    /**
     * 根据应用组id查询应用列表
     * @param cligUuid 非必传，空值时查默认组下的应用
     * @return
     */
    @RequestMapping("queryClientInfo")
    public PageInfo<UcasClientInfo> queryClient(String cligUuid, String clientName, String grantType, int pageNum, int pageSize) {
        String resultId;
        if (StringUtils.isEmpty(cligUuid)) {
            resultId = ucasClientGroupService.getDefaultClientGroup().getCligUuid();
        } else {
            resultId = cligUuid;
        }
        return ucasClientInfoService.queryClientInfoByCligUuid(resultId, clientName, grantType, pageNum, pageSize);
    }

    /**
     * 管理应用组下的应用
     * @param cligUuid 非必传字段，如果有表示为绑定应用；没有表示解绑应用
     * @param clientIds 应用id，多个用逗号分隔
     * @return
     */
    @RequestMapping("manageClient")
    public AosResult manageClient(String cligUuid, String clientIds) {
        if (StringUtils.isEmpty(clientIds)) {
            return AosResult.retFailureMsg("请选择应用");
        }
        String resultId;
        if (StringUtils.isEmpty(cligUuid)) {
            // 解绑，先查默认应用组
            UcasClientGroup defClientGroup = ucasClientGroupService.getDefaultClientGroup();
            resultId = defClientGroup.getCligUuid();
        } else {
            resultId = cligUuid;
        }
        ucasClientInfoService.updateClientInfoCligUuid(resultId, clientIds);
        return AosResult.retSuccessMsg("操作成功");
    }

    /**
     * 获取默认应用组
     * @return
     */
    @RequestMapping("default")
    public AosResult getDefaultClientGroup() {
        UcasClientGroup defClientGroup = ucasClientGroupService.getDefaultClientGroup();
        return AosResult.retSuccessMsg("success", defClientGroup);
    }

}
