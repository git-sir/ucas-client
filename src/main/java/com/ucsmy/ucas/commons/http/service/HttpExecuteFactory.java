package com.ucsmy.ucas.commons.http.service;

import com.ucsmy.ucas.commons.constant.HttpConnectionManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by ucs_mojiazhou on 2017/5/4.
 * http执行
 */
@Component
public abstract class HttpExecuteFactory {

    public static final String CHARSET_UTF_8 = "utf-8";

    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";

    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";



    @Autowired
    private   HttpConnectionManager httpConnectionManager;


    /***
     * http发送post请求
     * @param httpPost
     * @return
     * @throws IOException
     */
    private   String sendHttpPost(HttpPost httpPost,boolean isHttps) throws IOException {

        CloseableHttpClient httpClient ;

        // 创建默认的httpClient实例.
        if(isHttps)
            httpClient = httpConnectionManager.getHttpsClient();
        else
            httpClient = httpConnectionManager.getHttpClient();
        // 执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 响应内容
        String responseContent = null;

        // 得到响应实例
        HttpEntity entity = response.getEntity();
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
            EntityUtils.consume(entity);
        }
        response.close();

        return responseContent;
    }



    /**
     * http发送get请求
     * @param httpGet
     * @return
     * @throws IOException
     */
     private   String sendHttpGet(HttpGet httpGet,boolean isHttps) throws IOException {

        CloseableHttpClient httpClient;
        CloseableHttpResponse response ;
        // 响应内容
        String responseContent = null;

        // 创建默认的httpClient实例.
         if(isHttps)
             httpClient = httpConnectionManager.getHttpsClient();
         else
             httpClient = httpConnectionManager.getHttpClient();
        // 执行请求
        response = httpClient.execute(httpGet);
        // 得到响应实例
        HttpEntity entity = response.getEntity();

        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
            EntityUtils.consume(entity);
        }
        // 释放资源
            response.close();


        return responseContent;
    }

    /**
     *
     * @param httpUrl
     * @return
     * @throws IOException
     */
    public  String sendHttpGet(String httpUrl) throws IOException {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet,this.isHttps(httpUrl));
    }

    /**
     * 发送https get
     * @param httpUrl
     * @return
     * @throws IOException
     */
    public String sendHttpsGet(String httpUrl)throws IOException
    {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet,this.isHttps(httpUrl));
    }

    public String senddHttpPost(String httpUrl,String parame,String comtentType) throws IOException {

        HttpPost httpPost = new HttpPost(httpUrl);
        if (parame != null && parame.trim().length() > 0) {
            StringEntity stringEntity = new StringEntity(parame, "UTF-8");
            stringEntity.setContentType(comtentType);
            httpPost.setEntity(stringEntity);
        }

       return this.sendHttpPost(httpPost,this.isHttps(httpUrl));

    }

    private boolean isHttps(String url)
    {
        if(url.startsWith("https"))
            return true;
        return false;
    }


}
