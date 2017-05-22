package com.ucsmy.ucas.manage.service;


/**
 * Created by ucs_mojiazhou on 2017/5/11.
 */
public interface ManageCommonService {

     static final String ROOT_URL_KEY = "USER_CORE_URL";
    /**
     * 地址前添加
     * RootUrl
     * @param url
     * @return
     */
    String concantRootUrl(String url);

}
