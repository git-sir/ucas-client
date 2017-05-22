package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageIpScheduleTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by chenqilin on 2017/4/18.
 */
@Mapper
public interface ManageIpScheduleTaskMapper {

    UcasPageInfo<ManageIpScheduleTask> queryScheduleTask(@Param("taskName") String taskName, PageRequest pageRequest);

    ManageIpScheduleTask getScheduleTaskById(@Param("id") String id);

    int isTaskCodeExist(@Param("taskCode") String taskCode, @Param("id") String id);

    int addSchedulTask(ManageIpScheduleTask scheduleTask);

    int updateScheduleTask(ManageIpScheduleTask scheduleTask);

    int deleteSchedulTask(@Param("id") String id);

    List<ManageIpScheduleTask> getAll();
}
