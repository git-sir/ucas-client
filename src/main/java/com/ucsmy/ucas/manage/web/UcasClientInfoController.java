package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import com.ucsmy.ucas.manage.service.UcasClientGroupService;
import com.ucsmy.ucas.manage.service.UcasClientInfoService;
import com.ucsmy.ucas.manage.service.UcasClientResgrRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_xiaokailin on 2017/4/24.
 */
@RestController
@RequestMapping("client_info")
public class UcasClientInfoController {

    private Logger log = LoggerFactory.getLogger(UcasClientInfoController.class);
    @Autowired
    private UcasClientInfoService ucasClientInfoService;
    @Autowired
    private UcasClientGroupService ucasClientGroupService;
    @Autowired
    private UcasClientResgrRelService ucasClientResgrRelService;

    @PostMapping("query")
    public AosResult query(@RequestParam(required = false) String clientName,
                           @RequestParam(required = false) String clientId,
                           @RequestParam(required = false) String clientGroupName,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String grantType,
                           int pageNum, int pageSize) {
        PageInfo<UcasClientInfo> pageInfo;
        if(status == null || status.equals(CommStatusEnum.SYS_INUSE.getValue()) || "".equals(status)){
            pageInfo = ucasClientInfoService.queryUcasClientInfo(clientName,clientId,clientGroupName,CommStatusEnum.SYS_INUSE.getValue(),grantType,pageNum,pageSize);
        }else{
            pageInfo = ucasClientInfoService.queryUcasClientInfo(clientName,clientId,clientGroupName,CommStatusEnum.SYS_UNUSE.getValue(),grantType,pageNum,pageSize);
        }

        return AosResult.retSuccessMsg("查询成功", pageInfo);
    }

    /**
     * 添加应用，如果有需要，同时添加Token策略
     * @param clientInfo
     * @return
     */
    @PostMapping("add")
    public AosResult add(UcasClientInfo clientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy){
        AosResult validate = validateClientInfo(clientInfo);
        if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
            return validate;
        }
        if(ucasClientInfoService.checkClientNameExist(clientInfo)){
            return AosResult.retFailureMsg("应用名称已存在");
        }
        return ucasClientInfoService.addClientInfoWithTokenStrategy(clientInfo, ucasTokenStrategy, hasTokenStrategy);
    }

    @PostMapping("update")
    public AosResult update(UcasClientInfo clientInfo, UcasTokenStrategy ucasTokenStrategy, Boolean hasTokenStrategy) {
        AosResult validate = validateClientInfo(clientInfo);
        if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))){
            return validate;
        }
        if(ucasClientInfoService.checkClientNameExist(clientInfo)){
            return AosResult.retFailureMsg("应用名称已存在");
        }
        return ucasClientInfoService.updateClientInfoWithTokenStrategy(clientInfo, ucasTokenStrategy, hasTokenStrategy);
    }
    @PostMapping("delete")
    public AosResult delete(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            return AosResult.retFailureMsg("应用ID不能为空");
        }
        if(ucasClientResgrRelService.isResgrRelExist(clientId,null)){
            return AosResult.retFailureMsg("请先解绑资源组");
        }
        //逻辑删除
        ucasClientInfoService.deleteClientInfo(clientId);
        return AosResult.retSuccessMsg("删除应用成功");
    }
    @PostMapping("queryAllClientGroup")
    public AosResult queryAllClientGroup(){
        Map<String,Object> map = ucasClientGroupService.getAllClientGroup();
        List<Object> list = new ArrayList<>();
        list.addAll((List)map.get("list"));
        return AosResult.retSuccessMsg("查询所有应用组成功",list);
    }
    @PostMapping("queryAllGrantType")
    public AosResult queryAllGrantType(){
        List<String> list = ucasClientInfoService.queryAllGrantType();
        return AosResult.retSuccessMsg("查询所有授权类型成功",list);
    }

    private AosResult validateClientInfo(UcasClientInfo clientInfo){
        if (StringUtils.isEmpty(clientInfo.getClientName())) {
            return AosResult.retFailureMsg("应用简称不能为空");
        }
        if (StringUtils.isEmpty(clientInfo.getDescRibe())) {
            return AosResult.retFailureMsg("应用描述不能为空");
        }
        if (clientInfo.getClientName().length() > 100) {
            return AosResult.retFailureMsg("应用简称长度不能超过100");
        }
        if (clientInfo.getDescRibe().length() > 200) {
            return AosResult.retFailureMsg("应用描述长度不能超过200");
        }
        if (clientInfo.getClientUrl().length() > 200) {
            return AosResult.retFailureMsg("应用主页长度不能超过200");
        }
        //过滤掉空格
        clientInfo.setClientName(clientInfo.getClientName().replace(" ","").replace("　",""));
        clientInfo.setDescRibe(clientInfo.getDescRibe().replace(" ","").replace("　",""));
        return AosResult.retSuccessMsg("success");
    }
}
