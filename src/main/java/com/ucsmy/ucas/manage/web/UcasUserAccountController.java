package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.ext.UserAccountInfo;
import com.ucsmy.ucas.manage.service.UcasUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ucs_mojiazhou on 2017/4/25.
 */
@RestController
@RequestMapping("account")
public class UcasUserAccountController {

    @Autowired
    private UcasUserAccountService service;






    @RequestMapping("queryUsers")
    @ResponseBody
    public PageInfo<UserAccountInfo> getUser(UserAccountInfo userAccountInfo,
                                             int pageNum, int pageSize) {
        PageInfo<UserAccountInfo> pageInfo = service.getUsers(userAccountInfo, pageNum, pageSize);
        return pageInfo;
    }

    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping("addUser")
    @ResponseBody
    public AosResult addUser(String ucasAccount,
                             @RequestParam(required = true) String accgUuid,
                             String emailAccount,
                             String mobileAccount,
                             @RequestParam(required = true) String password,
                             @RequestParam(required = true) String realName,
                             String mobilePhone, String email, String sex,
                             String headImgUrl,
                             @RequestParam(required = true) String orgName
    ) {
        AosResult aosResult = service.addUserAccount(ucasAccount,accgUuid,emailAccount,mobileAccount
                        ,password,realName,mobilePhone,email,sex,headImgUrl,orgName);
        return aosResult;

    }

    /**
     * 冻结帐号
     *
     * @return
     */
    @RequestMapping("freeze")
    @ResponseBody
    public AosResult freeze(@RequestParam(required = true) String ucasAccount) {
        return service.freeze(ucasAccount);

    }

    /**
     * 解冻帐号
     *
     * @return
     */
    @RequestMapping("unfreeze")
    @ResponseBody
    public AosResult unfreeze(@RequestParam(required = true) String ucasAccount) {


        return service.unfreeze(ucasAccount);

    }

    /**
     * 修改邮箱帐号
     *
     * @return
     */
    @RequestMapping("upEmail")
    @ResponseBody
    public AosResult upEmail(@RequestParam(required = true) String emailAccount, @RequestParam(required = true) String ucasAccount) {
        return service.upEmail(ucasAccount,emailAccount);
    }

    /**
     * 修改手机号
     *
     * @return
     */
    @RequestMapping("upPhone")
    @ResponseBody
    public AosResult upPhone(@RequestParam(required = true) String mobileAccount, @RequestParam(required = true) String ucasAccount) {

        return service.upPhone(ucasAccount,mobileAccount);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping("upPassword")
    @ResponseBody
    public AosResult upPassword(@RequestParam(required = true) String ucasAccount, @RequestParam(required = true) String password) {


        return service.upPassword(ucasAccount,password);
    }


    /**
     * 冻结帐号
     *
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public AosResult delete(@RequestParam(required = true) String ucasAccount) {
        return service.delete(ucasAccount);
    }

    /**
     * 获取帐号详情
     *
     * @return
     */
    @RequestMapping("getUser")
    @ResponseBody
    public AosResult getUser(@RequestParam(required = true) String ucasAccount) {

        return service.getUser(ucasAccount);

    }

    /**
     * 修改用户
     *
     * @return
     */
    @RequestMapping("upInfo")
    @ResponseBody
    public AosResult upInfo(String ucasAccount,
                            @RequestParam(required = true) String accgUuid,
                            @RequestParam(required = true) String realName,
                            String mobilePhone,
                            String email,
                            String sex,
                            String headImgUrl,
                            @RequestParam(required = true) String orgName
    ) {

        return service.upInfo(ucasAccount,accgUuid,realName,mobilePhone,email,sex,headImgUrl,orgName);
    }
}
