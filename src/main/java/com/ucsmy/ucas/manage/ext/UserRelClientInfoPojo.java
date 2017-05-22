package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasUserClientRel;

import java.io.Serializable;

/**
 * 用作账号授权管理展示页面，包含应用信息和openId
 * Created by chenqilin on 2017/4/25.
 */
public class UserRelClientInfoPojo extends UcasUserClientRel {

    private static final long serialVersionUID = 1L;

    /**
     * 应用简称
     */
    private String clientName;

    /**
     * 应用描述
     */
    private String descRibe;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescRibe() {
        return descRibe;
    }

    public void setDescRibe(String descRibe) {
        this.descRibe = descRibe;
    }
}
