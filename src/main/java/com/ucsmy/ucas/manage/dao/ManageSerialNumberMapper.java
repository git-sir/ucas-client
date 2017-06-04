package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManageSerialNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 序列号 mapper
 * Created by chenqilin on 2017/5/18.
 */
@Mapper
public interface ManageSerialNumberMapper {

    /**
     * 根据日期查询序列号信息
     * @param serialNumberDate
     * @return
     */
    ManageSerialNumber querySerialNumberByDateForUpdate(@Param("serialNumberDate") Date serialNumberDate);

    /**
     * 根据主键查询序列号信息
     * @param serialNumberId
     * @return
     */
    ManageSerialNumber querySerialNumberById(@Param("serialNumberId") String serialNumberId);

    /**
     * 添加序列号信息
     * @param manageSerialNumber
     * @return
     */
    int addSerialNumber(ManageSerialNumber manageSerialNumber);

    /**
     * 更新序列号信息
     * @param manageSerialNumber
     * @return
     */
    int updateSerialNumber(ManageSerialNumber manageSerialNumber);
}
