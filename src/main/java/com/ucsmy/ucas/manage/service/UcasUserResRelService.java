package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.ext.UserRelResourcePojo;

/**
 * 账号资源授权service
 * Created by chenqilin on 2017/4/25.
 */
public interface UcasUserResRelService {

    /**
     * 查询openId下的授权资源
     * @param openId
     * @param pageNum
     * @param pageSize
     * @return
     */
    UcasPageInfo<UserRelResourcePojo> queryUserRelResource(String openId, int pageNum, int pageSize);
}
