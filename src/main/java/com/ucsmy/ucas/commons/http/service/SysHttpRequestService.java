package com.ucsmy.ucas.commons.http.service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/5/4.
 */
public interface SysHttpRequestService {

    /***getJson请求**/
    String sendHttpGetJson(String url,String json) throws IOException;

    /***get请求***/
    String sendHttpGet(String url) throws IOException;

    /**
     * get map参数请求
     * @param url
     * @param params
     * @return
     */
    String sendHttpGetMap(String url,Map<String, Object> params) throws IOException;

    /**
     * post json请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    String sendHttpPostJson(String url,String json) throws IOException;

    /****
     * post map参数请求
     * @param httpUrl
     * @param params
     * @return
     */
    String sendHttpPostMap(String httpUrl,Map<String, Object> params) throws IOException;

    /******
     * post xml参数请求
     * @param httpUrl
     * @param strXml
     * @return
     */
    String sendHttpPostXml(String httpUrl,String strXml) throws IOException;

    /**
     * 参数(格式:key1=value1&key2=value2)
     * @param httpUrl
     * @param params
     * @return
     */
    String sendHttpPost(String httpUrl, String params) throws IOException;

}
