package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageIpScheduleTask implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 任务名称编码
     */
    private String taskCode;

    /**
     * 任务名
     */
    private String taskName;

    /**
     * 任务执行表达式
     */
    private String taskConf;

    /**
     * 任务执行类
     */
    private String taskClass;

    /**
     * 任务执行的服务器
     */
    private String taskServerIp;

    /**
     * 任务状态1:启用;0：禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskConf() {
        return taskConf;
    }

    public void setTaskConf(String taskConf) {
        this.taskConf = taskConf;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getTaskServerIp() {
        return taskServerIp;
    }

    public void setTaskServerIp(String taskServerIp) {
        this.taskServerIp = taskServerIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}