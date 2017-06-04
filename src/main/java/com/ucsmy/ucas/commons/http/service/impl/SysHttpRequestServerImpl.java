package com.ucsmy.ucas.commons.http.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.ucas.commons.http.service.HttpExecuteFactory;
import com.ucsmy.ucas.commons.http.service.SysHttpRequestService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/5/4.
 */
@Service
public class SysHttpRequestServerImpl extends HttpExecuteFactory implements SysHttpRequestService {


    @Override
    public String sendHttpGetJson(String url, String json) throws IOException {

        Map map = JSONObject.parseObject(json, Map.class);
        String parame = this.convertStringParamter(map);

        return super.sendHttpGet(url+"?"+parame);
    }

    @Override
    public String sendHttpGet(String url) throws IOException {
        return super.sendHttpGet(url);
    }

    @Override
    public String sendHttpGetMap(String url, Map<String, Object> params) throws IOException {
        String parame = this.convertStringParamter(params);
        return super.sendHttpGet(url+"?"+parame);
    }

    @Override
    public String sendHttpPostJson(String url, String json) throws IOException {

       return super.senddHttpPost(url,json,CONTENT_TYPE_JSON_URL);

    }

    @Override
    public String sendHttpPostMap(String httpUrl, Map<String, Object> params) throws IOException {

        String parma = this.convertStringParamter(params);

        return this.senddHttpPost(httpUrl,parma,CONTENT_TYPE_FORM_URL);
    }

    @Override
    public String sendHttpPostXml(String httpUrl, String strXml) throws IOException {

        return this.senddHttpPost(httpUrl,strXml,CONTENT_TYPE_TEXT_HTML);
    }

    @Override
    public String sendHttpPost(String httpUrl, String params) throws IOException {

        return this.senddHttpPost(httpUrl,params,CONTENT_TYPE_FORM_URL);
    }

    private   String convertStringParamter(Map parameterMap) {
        StringBuilder parameterBuffer = new StringBuilder();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key ;
            String value ;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }


}
