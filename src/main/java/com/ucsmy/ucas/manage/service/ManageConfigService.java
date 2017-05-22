package com.ucsmy.ucas.manage.service;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.ucas.manage.entity.ManageConfig;

import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
public interface ManageConfigService {

    PageInfo<ManageConfig> queryConfig(String paramKey, int page, int size);

    int addConfig(ManageConfig manageConfig);

    int editConfig(ManageConfig manageConfig);

    int deleteConfig(String id);

    ManageConfig getConfig(String id);

    ManageConfig queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);

}


