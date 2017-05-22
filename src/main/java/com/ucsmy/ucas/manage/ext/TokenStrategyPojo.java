package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasClientGroup;
import com.ucsmy.ucas.manage.entity.UcasClientInfo;
import com.ucsmy.ucas.manage.entity.UcasTokenStrategy;

/**
 * token策略扩展类，列表展示用
 * Created by chenqilin on 2017/5/11.
 */
public class TokenStrategyPojo extends UcasTokenStrategy {

    private static final long serialVersionUID = 1L;

    private UcasClientInfo ucasClientInfo;

    public UcasClientInfo getUcasClientInfo() {
        return ucasClientInfo;
    }

    public void setUcasClientInfo(UcasClientInfo ucasClientInfo) {
        this.ucasClientInfo = ucasClientInfo;
    }
}
