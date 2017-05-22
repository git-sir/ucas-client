package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasUserResRel;


/**
 * 账户授权资源扩展类
 * Created by chenqilin on 2017/4/25.
 */
public class UserRelResourcePojo extends UcasUserResRel {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private int no;

    /**
     * 资源URI
     */
    private String resUri;

    /**
     * 资源描述
     */
    private String descRibe;

    /**
     * 资源状态
     */
    private String status;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getResUri() {
        return resUri;
    }

    public void setResUri(String resUri) {
        this.resUri = resUri;
    }

    public String getDescRibe() {
        return descRibe;
    }

    public void setDescRibe(String descRibe) {
        this.descRibe = descRibe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
