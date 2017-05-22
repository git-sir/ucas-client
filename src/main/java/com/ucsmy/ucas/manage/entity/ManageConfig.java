package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ManageConfig implements Serializable {
    private String id;

    private String paramKey;

    private String paramValue;

    private String paramDesc;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}