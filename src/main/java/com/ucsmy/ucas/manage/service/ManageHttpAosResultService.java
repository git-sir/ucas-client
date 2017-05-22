package com.ucsmy.ucas.manage.service;

import com.ucsmy.ucas.commons.aop.exception.result.AosResult;

/**
 * Created by ucs_mojiazhou on 2017/4/28.
 */
public interface ManageHttpAosResultService {

    /*****
     * 发送post jsono格式请求
     * @param url
     * @param json
     * @return
     */
    AosResult sendPostJson(String url,String strJson);

    /**
     * 发送get请求
     * @param url
     * @return
     */
    AosResult sendGet(String url);

}
