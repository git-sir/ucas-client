package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.ManageConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
@Mapper
public interface ManageConfigMapper {
    UcasPageInfo<ManageConfig> queryConfig(@Param("paramKey") String  paramKey, PageRequest pageRequest);

    int addConfig(ManageConfig manageConfig);

    int editConfig(ManageConfig manageConfig);

    int deleteConfig(String id);

    ManageConfig getConfig(String id);

    ManageConfig queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);
}
