package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.ManageLogInfo;
import com.ucsmy.ucas.manage.service.ManageLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_mojiazhou on 2017/5/27.
 */
@Controller
@RequestMapping("manageLog")
public class ManageLogInfoController {

    @Autowired
    private ManageLogInfoService manageLogInfoService;


    @RequestMapping("query")
    @ResponseBody
    public PageInfo<ManageLogInfo> query(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        PageInfo<ManageLogInfo> pageInfo = manageLogInfoService.query(startDate,endDate, pageNum, pageSize);
        return  pageInfo;
    }


}
