package com.ucsmy.ucas.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.constant.HttpResponseCode;
import com.ucsmy.ucas.commons.http.service.SysHttpRequestService;
import com.ucsmy.ucas.manage.service.ManageHttpAosResultService;
import com.ucsmy.ucas.manage.service.SysTokenManagerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/4/28.
 */
@Service
public class ManageHttpAosResultServerImpl implements ManageHttpAosResultService {

    private static  final  String INVALID_TOKEN= "invalid_token";

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ManageHttpAosResultServerImpl.class);

    @Autowired
    private SysTokenManagerService sysTokenManagerService;

    @Autowired
    private SysHttpRequestService sysHttpRequestService;

    @Override
    @Logger(operationName="发送postJson", printSQL = true)
    public AosResult sendPostJson(String url, String strJson) {

        AosResult aosResult = this.firsendPostJson(url, strJson);
        if(aosResult.getRetmsg().equals(INVALID_TOKEN))
        {
            String token = null;
            try {
                token = sysTokenManagerService.getValidToken("refresh");
            } catch (Exception e) {
                log.error("ManageHttpAosResultServerImpl",e);
                return AosResult.retFailureMsg("调用外部接口异常");
            }
            Map map = JSONObject.parseObject(strJson,Map.class);
            map.put("access_token",token);
           String  newStrJson =  JSONObject.toJSONString(map);
            return  this.firsendPostJson(url, newStrJson);
        }
        return aosResult;
    }

    @Override
    @Logger(operationName="发送Get", printSQL = true)
    public AosResult sendGet(String url) {
        String entity = null;
        try {
                entity =  sysHttpRequestService.sendHttpGet(url);

        } catch (IOException e) {
            log.error("ManageHttpAosResultServerImpl",e);
            return AosResult.retFailureMsg("调用外部接口异常");
        }

        return this.handleResponse(entity);
    }

    @Logger(printSQL = true)
    private AosResult handleResponse(String entity)
    {
        if (null != entity) {
            JSONObject json = JSONObject.parseObject(entity);
            if (null != json.get("errcode"))
            {
                int errcode = (Integer) json.get("errcode");

                if(errcode == HttpResponseCode.SUCCESS.getIndex())
                {
                    return AosResult.retSuccessMsg((String) json.get("errmsg"));
                }
                else if(errcode==HttpResponseCode.INVALID_REQUEST.getIndex())
                {
                    return AosResult.retFailureMsg(HttpResponseCode.SUCCESS.getName());
                }
                else if(errcode == HttpResponseCode.EXPIRE_TOKEN.getIndex() //token过期
                        || errcode == HttpResponseCode.INVALID_TOKEN.getIndex())
                {
                    return AosResult.retFailureMsg(INVALID_TOKEN);

                }else if(errcode==HttpResponseCode.INSUFFICIENT_SCOPE.getIndex())
                {
                    return AosResult.retFailureMsg(HttpResponseCode.INSUFFICIENT_SCOPE.getName());
                }
                else if(errcode==HttpResponseCode.SERVER_ERROR.getIndex())
                {
                    return AosResult.retFailureMsg(HttpResponseCode.SERVER_ERROR.getName());
                }
                else
                {
                    return AosResult.retFailureMsg((String) json.get("errmsg"));
                }
            }
            else{
                return AosResult.retSuccessMsg("success", json);
            }
        }else
        {
            return AosResult.retFailureMsg("调用外部接口异常");
        }
    }

    @Logger(printSQL = true)
    private AosResult firsendPostJson(String url, String strJson)
    {

        String entiy = null;
        try {
            entiy = sysHttpRequestService.sendHttpPostJson(url,strJson);
        } catch (IOException e) {
            log.error("ManageHttpAosResultServerImpl",e);
            return AosResult.retFailureMsg("调用外部接口异常");
        }
         return this.handleResponse(entiy);

    }


}
