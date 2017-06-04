package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManageModule;
import com.ucsmy.ucas.manage.ext.MainModulePojo;
import com.ucsmy.ucas.manage.ext.ModuleTreePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ucas_client_module
 * Created by chenqilin on 2017/4/13.
 */
@Mapper
public interface ManageModuleMapper {

    List<ModuleTreePojo> getModuleList(@Param("name") String name
            , @Param("parentId") String parentId, @Param("excludeId") String excludeId);

    ModuleTreePojo getModuleDetail(@Param("id") String id);

    int addModule(ManageModule module);

    int updateModule(ManageModule module);

    int deleteModule(@Param("id") String id);

    int getChildrenNum(@Param("id") String id);

    List<MainModulePojo> queryMainAllModule();

    List<MainModulePojo> queryMainModuleByUserId(@Param("userId") String userId);
}
