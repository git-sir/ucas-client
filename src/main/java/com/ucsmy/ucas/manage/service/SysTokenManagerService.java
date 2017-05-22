package com.ucsmy.ucas.manage.service;


import java.util.Map;

/**
 * Created by ucs_zhongtingyuan on 2017/4/11.
 */
public interface SysTokenManagerService {
    //获取有效token，如果token不存在或失效，则调取token获取接口获取新的token
    String getValidToken() throws Exception;
    //获取有效token，如果token不存在或失效，则调取token获取接口获取新的token  如果tokenStatus =
    /**
     * 获取有效token
     *如果token不存在或失效，则调取token获取接口获取新的token
     * @param  tokenStatus  不为空 则强制刷新缓存token
     *   tokenStatus  为空 则不刷新
     * @return
     */
    String getValidToken(String tokenStatus)throws Exception;

    //token信息缓存内存储
    void saveTokenInfo(Map token, Long time);

    //从认证中心用client_id获取token
    Map  getTokenInfo() throws Exception;

    //从缓存获取token
    Map  getTokenByCache();
    //清空token缓存
    void cleanTokenCache();

    /**
     * 获取系统登录时候，认证中心产生的tokenInfo ，存进缓存
     */

    //系统登录Token保存
    public String setSysLoginToken(Map infoMap);

    public String getSysLoginToken(String key);


}
