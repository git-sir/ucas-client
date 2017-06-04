package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.entity.ManageLogInfo;

/**
 * manageLogInfo service
 * Created by chenqilin on 2017/5/8.
 */
public interface ManageLogInfoService {

    /**
     * 添加logInfo
     * @param manageLogInfo
     * @return
     */
    int addManageLogInfo(ManageLogInfo manageLogInfo);

    /**
     * 查看列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ManageLogInfo> query(String startDate,String endDate,int pageNum, int pageSize);
}
