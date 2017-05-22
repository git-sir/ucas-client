package com.ucsmy.ucas.manage.web;

import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.utils.CommStatusEnum;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;
import com.ucsmy.ucas.manage.service.UcasTokenStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Token策略Controller
 * Created by chenqilin on 2017/5/10.
 */
@RestController
@RequestMapping("tokenStrategy")
public class UcasTokenStrategyController {
    
    private final String UPDATE_FAIL = "更新失败，请检查网络";
    private final String UPDATE_SUCCESS = "更新成功";
    private final String UUID_EMPTY = "Token策略UUID为空";

    private final String DELETE_FAIL = "删除失败，请检查网络";
    private final String DELETE_SUCCESS = "删除成功";

    @Autowired
    private UcasTokenStrategyService ucasTokenStrategyService;

    /**
     * 分页查询
     * @param clientName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("queryList")
    public PageInfo queryTokenStrategyList(String clientName, String clientId, int pageNum, int pageSize) {
        return ucasTokenStrategyService.queryTokenStrategyList(clientName, clientId, CommStatusEnum.SYS_INUSE.getValue(), pageNum, pageSize);
    }

    /**
     * 根据clientId查询列表
     * @param clientId
     * @return
     */
    @RequestMapping("queryTokenStrategyByClientId")
    public AosResult queryTokenStrategyListByClientId(String clientId) {
        return AosResult.retSuccessMsg("success", ucasTokenStrategyService.queryTokenStrategyListByClientId(clientId));
    }

    /**
     * 更新，检查该clientId下是否有已经在用的策略
     * @param ucasTokenStrategy
     * @return
     */
    @RequestMapping("update")
    public AosResult updateTokenStrategy(UcasTokenStrategy ucasTokenStrategy) {
        if (StringUtils.isEmpty(ucasTokenStrategy.getUuid())) {
            return AosResult.retFailureMsg(UUID_EMPTY);
        }
        AosResult validate = ucasTokenStrategyService.validateTokenStrategy(ucasTokenStrategy);
        if (!validate.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))) {
            return validate;
        }
        // 检查clientId下在用的tokenStrategy
        AosResult check = ucasTokenStrategyService.checkTokenStrategyExist(ucasTokenStrategy);
        if (!check.getRetcode().equals(String.valueOf(ResultConst.SUCCESS))) {
            return check;
        }
        int result = ucasTokenStrategyService.updateTokenStrategy(ucasTokenStrategy);
        if (result > 0) {
            return AosResult.retSuccessMsg(UPDATE_SUCCESS);
        }
        return AosResult.retFailureMsg(UPDATE_FAIL);
    }

    /**
     * 逻辑删除
     * @param uuid
     * @return
     */
    @RequestMapping("delete")
    public AosResult deleteTokenStrategy(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return AosResult.retFailureMsg(UUID_EMPTY);
        }
        int result = ucasTokenStrategyService.deleteTokenStrategy(uuid);
        if (result > 0) {
            return AosResult.retSuccessMsg(DELETE_SUCCESS);
        }
        return AosResult.retFailureMsg(DELETE_FAIL);
    }
}
