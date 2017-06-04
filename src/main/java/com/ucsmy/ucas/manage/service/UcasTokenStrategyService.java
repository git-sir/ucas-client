package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import com.ucsmy.ucas.manage.ext.TokenStrategyPojo;

import java.util.List;

/**
 * TokenStrategy Service
 * Created by chenqilin on 2017/5/10.
 */
public interface UcasTokenStrategyService {

    /**
     * 分页查询
     * @param clientName
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    UcasPageInfo<TokenStrategyPojo> queryTokenStrategyList(String clientName, String clientId, String status, int pageNum, int pageSize);

    /**
     * 根据clientId查询正常状态的token策略
     * @param clientId
     * @param uuid 排除的uuid
     * @return
     */
    List<UcasTokenStrategy> queryTokenStrategyListByClientId(String clientId, String uuid);

    /**
     * 主键查询
     * @param uuid
     * @return
     */
    UcasTokenStrategy queryTokenStrategyByUuid(String uuid);

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
     * 逻辑删除token策略
     * @param uuid
     * @return
     */
    int deleteTokenStrategy(String uuid);

    /**
     * 校验tokenStrategy各属性是否合法
     * @param ucasTokenStrategy
     * @return
     */
    AosResult validateTokenStrategy(UcasTokenStrategy ucasTokenStrategy);

    /**
     * 检查clientId下是否已经有其他（不包含查询条件里的uuid）在用的tokenStrategy
     * @param ucasTokenStrategy
     * @return
     */
    AosResult checkTokenStrategyExist(UcasTokenStrategy ucasTokenStrategy);
}
