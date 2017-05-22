package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.HibernateValidateUtils;
import com.ucsmy.commons.utils.StringAndNumberUtil;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.UcasAccountGroup;
import com.ucsmy.ucas.manage.entity.UcasCallTicket;
import com.ucsmy.ucas.manage.entity.UcasClientToken;
import com.ucsmy.ucas.manage.service.UcasTicketService;
import com.ucsmy.ucas.manage.service.UcasTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_panwenbo on 2017/4/13.
 */
@Controller
@RequestMapping("ticket")
public class UcasTicketController {
    private final String MESSAGE_EDIT_SUCCESS = "修改成功";
    private final String MESSAGE_EDIT_FAIL = "修改失败";

    @Autowired
    private UcasTicketService ucasTicketService;
    @Autowired
    private UcasTokenService ucasTokenService;


    @RequestMapping("queryTicket")
    @ResponseBody
    public PageInfo<UcasAccountGroup> queryTicket(@RequestParam(required = false) String clientiId, @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize){
        return ucasTicketService.query(clientiId,pageNum,pageSize);
    }

    @RequestMapping("getTicket")
    @ResponseBody
    public UcasAccountGroup getTicket(String id){
        return ucasTicketService.getTicket(id);
    }


    @RequestMapping("editTicket")
    @ResponseBody
    public AosResult editTicket(UcasCallTicket ucasCallTicket) {
        String errorMsg = HibernateValidateUtils.getErrors(ucasCallTicket);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        } else {
            int updateCount = ucasTicketService.editTicket(ucasCallTicket);
            if (updateCount > 0) {
                return AosResult.retSuccessMsg(MESSAGE_EDIT_SUCCESS, null);
            } else {
                return AosResult.retFailureMsg(MESSAGE_EDIT_FAIL);
            }
        }
    }

    @RequestMapping("queryToken")
    @ResponseBody
    public PageInfo<UcasAccountGroup> queryToken(@RequestParam(required = false) String clientiId, @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize){
        return ucasTokenService.query(clientiId,pageNum,pageSize);
    }

    @RequestMapping("getToken")
    @ResponseBody
    public UcasAccountGroup getToken(String id){
        return ucasTokenService.getToken(id);
    }


    @RequestMapping("editAccountGroup")
    @ResponseBody
    public AosResult editConfig(UcasClientToken ucasClientToken) {
        String errorMsg = HibernateValidateUtils.getErrors(ucasClientToken);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        } else {
            int updateCount = ucasTokenService.editToken(ucasClientToken);
            if (updateCount > 0) {
                return AosResult.retSuccessMsg(MESSAGE_EDIT_SUCCESS, null);
            } else {
                return AosResult.retFailureMsg(MESSAGE_EDIT_FAIL);
            }
        }
    }
}
