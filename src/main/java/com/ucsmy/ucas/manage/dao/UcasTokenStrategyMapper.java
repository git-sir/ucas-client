package com.ucsmy.ucas.manage.dao;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Token策略Mapper
 * Created by chenqilin on 2017/5/10.
 */
@Mapper
public interface UcasTokenStrategyMapper {

    /**
     * 条件分页查询token策略列表
     * @param clientName
     * @param status
     * @param pageRequest
     * @return
     */
    UcasPageInfo<UcasTokenStrategy> queryTokenStrategyList(@Param("clientName") String clientName
            , @Param("clientId") String clientId, @Param("status") String status, PageRequest pageRequest);

    /**
     * 根据clientId查询token策略
     * @param clientId
     * @return
     */
    List<UcasTokenStrategy> queryTokenStrategyListByClientId(@Param("clientId") String clientId, @Param("status") String status);

    /**
     * 根据主键uuid查询
     * @param uuid
     * @return
     */
    UcasTokenStrategy queryTokenStrategyByUuid(@Param("uuid") String uuid);

    /**
     * 添加token策略
     * @param ucasTokenStrategy
     * @return
     */
    int addTokenStrategy(UcasTokenStrategy ucasTokenStrategy);

    /**
     * 更新token策略
     * @param ucasTokenStrategy
     * @return
     */
    int updateTokenStrategy(UcasTokenStrategy ucasTokenStrategy);

    /**
     * 删除token策略
     * @param uuid
     * @return
     */
    int deleteTokenStrategy(@Param("uuid") String uuid);
}
