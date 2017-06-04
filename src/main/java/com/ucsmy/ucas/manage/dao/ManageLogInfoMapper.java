package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import com.ucsmy.ucas.manage.entity.ManageLogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * manage_log_info
 * Created by chenqilin on 2017/5/8.
 */
@Mapper
public interface ManageLogInfoMapper {

    /**
     * 添加logInfo
     * @param manageLogInfo
     * @return
     */
    int addManageLogInfo(ManageLogInfo manageLogInfo);

    /*****
     * 查找列表
     * @param pageRequest
     * @return
     */
    UcasPageInfo<ManageLogInfo> query(@Param("startDate")String startDate, @Param("endDate")String endDate, PageRequest pageRequest);
}
