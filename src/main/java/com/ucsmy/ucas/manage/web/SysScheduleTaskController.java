package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.commons.constant.CommMessage;
import com.ucsmy.ucas.manage.entity.ManageIpScheduleTask;
import com.ucsmy.ucas.manage.service.SysScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 定时任务模块控制器
 * Created by chenqilin on 2017/4/18.
 */
@RestController
@RequestMapping("schedule")
public class SysScheduleTaskController {

    private final static String TASK_CODE_DUPLICATE = "任务码不能重复";

    private final static String TASK_ID_EMPTY = "任务id不能为空";
    private final static String TASK_DEL_EMPTY = "定时任务不存在，删除失败";

    private final static String TASK_STAR_SUCCESS = "启动定时任务成功";
    private final static String TASK_STOP_SUCCESS = "关闭定时任务成功";

    @Autowired
    private SysScheduleTaskService sysScheduleTaskService;

    /**
     * 查询定时任务列表
     * @param taskName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list")
    public PageInfo<ManageIpScheduleTask> queryScheduleTaskList(@RequestParam(required = false) String taskName, int pageNum, int pageSize) {
        return sysScheduleTaskService.queryScheduleTask(taskName, pageNum, pageSize);
    }

    /**
     * 添加定时任务
     * @param scheduleTask
     * @return
     */
    @RequestMapping("add")
    public AosResult addScheduleTask(ManageIpScheduleTask scheduleTask) {
        AosResult validate = sysScheduleTaskService.validateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        if (sysScheduleTaskService.isTaskCodeExist(scheduleTask.getTaskCode(), null) > 0) {
            return AosResult.retFailureMsg(TASK_CODE_DUPLICATE);
        }
        int resultCode = sysScheduleTaskService.addSchedulTask(scheduleTask);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.ADD_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.ADD_SUCCESS, null);
    }

    /**
     * 更新定时任务
     * @param scheduleTask
     * @return
     */
    @RequestMapping("update")
    public AosResult updateScheduleTask(ManageIpScheduleTask scheduleTask) {
        AosResult validate = sysScheduleTaskService.validateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        AosResult result = sysScheduleTaskService.updateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(CommMessage.UPDATE_SUCCESS, null);
    }

    /**
     * 删除定时任务
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public AosResult deleteScheduleTask(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(TASK_ID_EMPTY);
        }
        ManageIpScheduleTask oldScheduleTask = sysScheduleTaskService.getScheduleTaskById(id);
        if (oldScheduleTask == null) {
            return AosResult.retFailureMsg(TASK_DEL_EMPTY);
        }
        int resultCode = sysScheduleTaskService.deleteSchedulTask(id);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(CommMessage.DELETE_FAILURE);
        }
        return AosResult.retSuccessMsg(CommMessage.DELETE_SUCCESS, null);
    }

    /**
     * 开始定时任务
     * @param id
     * @return
     */
    @RequestMapping("start")
    public AosResult startScheduleTask(String id) {
        AosResult result = sysScheduleTaskService.startScheduleTask(id);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(TASK_STAR_SUCCESS, null);
    }

    /**
     * 停止定时任务
     * @param id
     * @return
     */
    @RequestMapping("stop")
    public AosResult stopScheduleTask(String id) {
        AosResult result = sysScheduleTaskService.stopScheduleTask(id);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(TASK_STOP_SUCCESS, null);
    }

}
