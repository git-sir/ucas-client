package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.commons.utils.BeanUtil;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.manage.dao.ManageIpScheduleTaskMapper;
import com.ucsmy.ucas.manage.entity.ManageIpScheduleTask;
import com.ucsmy.ucas.manage.service.SysScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by chenqilin on 2017/4/18.
 */
@Service
public class SysSchedulTaskServiceImpl implements SysScheduleTaskService {

    private final static String STATUS_STOP = "0";
    private final static String STATUS_START = "1";

    @Autowired
    private ManageIpScheduleTaskMapper manageIpScheduleTaskMapper;

    @Override
    @Logger(printSQL = true)
    public PageInfo<ManageIpScheduleTask> queryScheduleTask(String taskName, int page, int size) {
        return manageIpScheduleTaskMapper.queryScheduleTask(taskName, new PageRequest(page, size));
    }

    @Override
    @Logger(printSQL = true)
    public ManageIpScheduleTask getScheduleTaskById(String id) {
        return manageIpScheduleTaskMapper.getScheduleTaskById(id);
    }

    @Override
    @Logger(printSQL = true)
    public int isTaskCodeExist(String taskCode, String id) {
        return manageIpScheduleTaskMapper.isTaskCodeExist(taskCode, id);
    }

    @Override
    @Logger(printSQL = true)
    public int addSchedulTask(ManageIpScheduleTask scheduleTask) {
        scheduleTask.setId(UUIDGenerator.generate());
        scheduleTask.setStatus(STATUS_STOP);
        return manageIpScheduleTaskMapper.addSchedulTask(scheduleTask);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult updateScheduleTask(ManageIpScheduleTask scheduleTask) {
        if (scheduleTask.getId() == null) {
            return AosResult.retFailureMsg("任务Id不能为空");
        }
        ManageIpScheduleTask oldScheduleTask = this.getScheduleTaskById(scheduleTask.getId());
        if (oldScheduleTask == null) {
            return AosResult.retFailureMsg("定时任务不存在，更新失败");
        }
        if (scheduleTask.getStatus() == null
                && !STATUS_STOP.equals(oldScheduleTask.getStatus())) {
            return AosResult.retFailureMsg("定时任务还在启用中，不能修改");
        }
        if (scheduleTask.getStatus() == null
                && this.isTaskCodeExist(scheduleTask.getTaskCode(), scheduleTask.getId()) > 0) {
            return AosResult.retFailureMsg("修改后的任务码已存在");
        }
        BeanUtil.copyPropertiesIgnoreNull(scheduleTask, oldScheduleTask);
        int resultCode = manageIpScheduleTaskMapper.updateScheduleTask(oldScheduleTask);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg("更新失败，请检查网络");
        }
        return AosResult.retSuccessMsg("success", null);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult startScheduleTask(String id) {
        ManageIpScheduleTask scheduleTask = new ManageIpScheduleTask();
        scheduleTask.setId(id);
        scheduleTask.setStatus(STATUS_START);
        return this.updateScheduleTask(scheduleTask);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult stopScheduleTask(String id) {
        ManageIpScheduleTask scheduleTask = new ManageIpScheduleTask();
        scheduleTask.setId(id);
        scheduleTask.setStatus(STATUS_STOP);
        return this.updateScheduleTask(scheduleTask);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteSchedulTask(String id) {
        return manageIpScheduleTaskMapper.deleteSchedulTask(id);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult validateScheduleTask(ManageIpScheduleTask scheduleTask) {
        if (scheduleTask.getTaskName() == null) {
            return AosResult.retFailureMsg("任务名称不能为空");
        }
        if (scheduleTask.getTaskCode() == null) {
            return AosResult.retFailureMsg("任务码不能为空");
        }
        if (scheduleTask.getTaskConf() == null) {
            return AosResult.retFailureMsg("定时配置不能为空");
        }
        if (scheduleTask.getTaskClass() == null) {
            return AosResult.retFailureMsg("执行地址不能为空");
        }
        if (scheduleTask.getTaskServerIp() == null) {
            return AosResult.retFailureMsg("指定ip不能为空");
        }
        if (scheduleTask.getRemark() == null) {
            return AosResult.retFailureMsg("备注不能为空");
        }
        return AosResult.retSuccessMsg("success");
    }
}
