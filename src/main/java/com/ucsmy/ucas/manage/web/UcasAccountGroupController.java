package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.ext.UserRelManageListPojo;
import com.ucsmy.ucas.manage.service.UcasAccountGroupService;
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
 * Created by ucs_panwenbo on 2017/4/13.
 */
@Controller
@RequestMapping("accountGroup")
public class UcasAccountGroupController {
    private final String MESSAGE_DUPLICATE = "已存在相同名称的用户组";
    private final String MESSAGE_ADD_SUCCESS = "保存成功";
    private final String MESSAGE_ADD_FAIL = "保存失败";

    private final String MESSAGE_EDIT_SUCCESS = "修改成功";
    private final String MESSAGE_EDIT_FAIL = "修改失败";
    private final String MESSAGE_ID_NOT_EXIST = "不存在";
    private final String MESSAGE_NAME_EXIST = "修改后的群组名称已存在";

    private final String MESSAGE_ID_NULL = " ID 为空";
    private final String MESSAGE_SUCCESS = "删除成功";
    private final String MESSAGE_DEL_FAIL = "删除失败";
    private final String MESSAGE_ACCOUNT = "用户组下存在用户";
    private final String MESSAGE_CLIENT = "用户组存在关联应用组";
    @Autowired
    private UcasAccountGroupService ucasAccountGroupService;




    @RequestMapping("query")
    @ResponseBody
    public PageInfo<UcasAccountGroup> query(@RequestParam(required = false) String groupName, @RequestParam(required = false) String clientGroupName, @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize){
        return ucasAccountGroupService.query(groupName, clientGroupName, pageNum, pageSize);
    }

    @RequestMapping("getAccountGroup")
    @ResponseBody
    public UcasAccountGroup getAccountGroup(String id){
        return ucasAccountGroupService.getAccountGroup(id);
    }

    @RequestMapping("addAccountGroup")
    @ResponseBody
    public AosResult addAccountGroup(UcasAccountGroup ucasAccountGroup){
        ucasAccountGroup.setAccgUuid(UUIDGenerator.generate(32));
        String errorMsg = HibernateValidateUtils.getErrors(ucasAccountGroup);
        if(!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        } else {
            if (isParamKeyExist(ucasAccountGroup.getGroupName())) {
                return AosResult. retFailureMsg(  MESSAGE_DUPLICATE);
            } else {
                int insertCount =  ucasAccountGroupService.addAccountGroup(ucasAccountGroup);
                if (insertCount > 0) {
                    return AosResult.retSuccessMsg(MESSAGE_ADD_SUCCESS, null);
                } else {
                    return AosResult. retFailureMsg(  MESSAGE_ADD_FAIL);
                }
            }
        }
    }

    @RequestMapping("editAccountGroup")
    @ResponseBody
    public AosResult editConfig(UcasAccountGroup ucasAccountGroup){
        String errorMsg = HibernateValidateUtils.getErrors(ucasAccountGroup);
        if(!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult. retFailureMsg(  errorMsg);
        } else {
            if(!isIdExist(ucasAccountGroup.getAccgUuid())) {
                return AosResult. retFailureMsg(  MESSAGE_ID_NOT_EXIST);
            } else {
                if(isKeyExist(ucasAccountGroup.getAccgUuid(), ucasAccountGroup.getGroupName())) {
                    return AosResult. retFailureMsg(  MESSAGE_NAME_EXIST);
                } else {
                    int updateCount =  ucasAccountGroupService.editAccountGroup(ucasAccountGroup);
                    if(updateCount > 0) {
                        return AosResult.retSuccessMsg(MESSAGE_EDIT_SUCCESS, null);
                    } else {
                        return AosResult. retFailureMsg(  MESSAGE_EDIT_FAIL);
                    }
                }
            }
        }
    }

    @RequestMapping("deleteAccountGroup")
    @ResponseBody
    public AosResult deleteAccountGroup(String accgUuid){
        if (StringAndNumberUtil.isNullAfterTrim(accgUuid)) {
            return AosResult. retFailureMsg(  MESSAGE_ID_NULL);
        }else if(ucasAccountGroupService.queryClientRelCount(accgUuid)>0){
            return AosResult. retFailureMsg(  MESSAGE_CLIENT);
        }else if(ucasAccountGroupService.queryAccountCount(accgUuid)>0){
            return AosResult. retFailureMsg(  MESSAGE_ACCOUNT);
        }
        else {
            int deleteCount = ucasAccountGroupService.deleteAccountGroup(accgUuid);
            if (deleteCount > 0) {
                return AosResult.retSuccessMsg(MESSAGE_SUCCESS, null);
            } else {
                return AosResult. retFailureMsg(  MESSAGE_DEL_FAIL);
            }
        }
    }
    /**
     * 根据用户组id查询应用组
     * @param status 非必传，空值时查默认组下的应用    accgUuid必传
     * @return
     */
    @RequestMapping("queryClientGroupInfo")
    @ResponseBody
    public PageInfo<UcasClientGroup> queryClientGroupInfo(String status, String accgUuid, String groupName, int pageNum, int pageSize) {
        return   ucasAccountGroupService.getbindStatusClientGroup( status, accgUuid, groupName, pageNum, pageSize);
    }
    @RequestMapping("manageClientGroup")
    @ResponseBody
    public AosResult manageClientGroup(String accgUuid, String cligUuids ,String type) {
        if (StringUtils.isEmpty(cligUuids)) {
            return AosResult.retFailureMsg("请选择应用");
        }
        if (StringUtils.isEmpty(type)) {
            return AosResult.retFailureMsg("参数错误,未指定操作参数");
        }
        return ucasAccountGroupService.manageClientGroup(accgUuid,cligUuids,type);
    }
    /**
     * 查询用户组下的用户
     * @return
     */
    @RequestMapping("queryAccountInfo")
    @ResponseBody
    public PageInfo<UserRelManageListPojo> queryAccountInfo(String accgUuid,String ucasAccount,
                                                            String emailAccount,String mobileAccount,
                                                            String realName,
                                                            int pageNum, int pageSize) {
        if (StringUtils.isEmpty(accgUuid)) {
            accgUuid = ucasAccountGroupService.getDefaultAccountGroup().getAccgUuid();
        }

        return ucasAccountGroupService.queryAccountInfoByAccgUuid(accgUuid,ucasAccount,emailAccount,mobileAccount,realName, pageNum, pageSize);
    }

    /**
     * 管理用户组下的用户
     * @return
     */
    @RequestMapping("manageAccount")
    @ResponseBody
    public AosResult manageAccount(String accgUuid, String accountIds) {
        if (StringUtils.isEmpty(accountIds)) {
            return AosResult.retFailureMsg("请选择用户");
        }
        if (StringUtils.isEmpty(accgUuid)) {
            // 解绑，先查默认用户组
            UcasAccountGroup defAccountGroup = ucasAccountGroupService.getDefaultAccountGroup();
            accgUuid = defAccountGroup.getAccgUuid();
        }
        ucasAccountGroupService.updateAccountInfoAccgUuid(accgUuid, accountIds);
        return AosResult.retSuccessMsg("操作成功");
    }

    /**
     * 获取默认应用组
     * @return
     */
    @RequestMapping("default")
    @ResponseBody
    public AosResult getDefaultClientGroup() {
        UcasAccountGroup defAccountGroup = ucasAccountGroupService.getDefaultAccountGroup();
        return AosResult.retSuccessMsg("success", defAccountGroup);
    }
    private boolean isParamKeyExist(String paramKey) {
        UcasAccountGroup ucasAccountGroup = ucasAccountGroupService.queryByName(paramKey);
        if (null != ucasAccountGroup) {
            return true;
        }
        return false;
    }

    private boolean isIdExist( String id) {
        UcasAccountGroup ucasAccountGroup = ucasAccountGroupService.getAccountGroup(id);
        if(null != ucasAccountGroup) {
            return true;
        }
        return false;
    }

    private boolean isKeyExist(String accgUuid, String groupName) {
        Map<String, Object> map = new HashMap<>();
        map.put("accgUuid", accgUuid);
        map.put("groupName", groupName);
        int count = ucasAccountGroupService.isKeyExist(map);
        if(count > 0) {
            return true;
        }
        return false;
    }


    /**
     * 获取所有的帐号组
     * @return
     */
    @RequestMapping("getAll")
    @ResponseBody
    public AosResult getAllAccountGroup() {
        List<UcasAccountGroup> list = ucasAccountGroupService.getAllAccountGroup();

        List<Map> maps = new ArrayList<>();
        for (UcasAccountGroup uag : list) {
            Map map = new HashMap();
            map.put("option", uag.getGroupName());
            map.put("value", uag.getAccgUuid());
            maps.add(map);
        }

        return AosResult.retSuccessMsg("success", maps);
    }

    /**
     * 根据应用组id、应用组名称查询账号组
     * @param cligUuid 应用组id
     * @param groupName 应用组名称
     * @return
     */
    @RequestMapping("queryByClientGroup")
    @ResponseBody
    public PageInfo queryAccountGroupByClientGroup(String cligUuid, String groupName, int pageNum, int pageSize) {
        return ucasAccountGroupService.queryAccountGroupByClientGroup(cligUuid, groupName, pageNum, pageSize);
    }

}
