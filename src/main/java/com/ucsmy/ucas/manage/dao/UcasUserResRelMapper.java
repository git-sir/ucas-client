package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasUserResRel;
import com.ucsmy.ucas.manage.ext.UserRelResourcePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户授权资源关系Mapper
 * Created by chenqilin on 2017/4/25.
 */
@Mapper
public interface UcasUserResRelMapper {

    /**
     * 查询openId下的授权资源
     * @param openId
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UserRelResourcePojo> queryUserRelResource(@Param("openId") String openId, PageRequest pageRequest);

    /**
     * 取消openId下的授权资源
     * @param openId
     * @return
     */
    int deleteUserRelResource(@Param("openId") String openId);

    /**
     * 根据资源uuid查询账号授权资源情况
     * @param resUuid
     * @return
     */
    int getUserRelResourceByResUuid(@Param("resUuid") String resUuid);
}
