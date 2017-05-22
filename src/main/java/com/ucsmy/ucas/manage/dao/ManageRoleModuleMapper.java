package com.ucsmy.ucas.manage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chenqilin on 2017/4/14.
 */
@Mapper
public interface ManageRoleModuleMapper {

    int deleteRoleByModuleId(@Param("id") String id);
}
