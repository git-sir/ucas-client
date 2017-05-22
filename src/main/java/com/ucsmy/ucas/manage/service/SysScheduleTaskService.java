package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.entity.ManageIpScheduleTask;


/**
 * Created by chenqilin on 2017/4/18.
 */
public interface SysScheduleTaskService {

    PageInfo<ManageIpScheduleTask> queryScheduleTask(String taskName, int page, int size);

    ManageIpScheduleTask getScheduleTaskById(String id);

    /**
     * 检查taskCode是否存在
     * @param taskCode
     * @param id scheduleTask主键id，非必填，如果填了查询时过滤这条id
     * @return
     */
    int isTaskCodeExist(String taskCode, String id);

    int addSchedulTask(ManageIpScheduleTask scheduleTask);

    /**
     * 更新scheduleTask，开启中的任务不能更新
     * @param scheduleTask
     * @return
     */
    AosResult updateScheduleTask(ManageIpScheduleTask scheduleTask);

    AosResult startScheduleTask(String id);

    AosResult stopScheduleTask(String id);

    int deleteSchedulTask(String id);

    /**
     * 验证scheduleTask属性值
     * @param scheduleTask
     * @return
     */
    AosResult validateScheduleTask(ManageIpScheduleTask scheduleTask);
}
