package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UcasCallTicket implements Serializable {
    /**
     * 调用凭证ticket
     */
    private String callTicketticket;

    /**
     * 应用clientid
     */
    private String clientId;

    /**
     * 资源UUID
     */
    private String resUuid;

    /**
     * 最大使用次数
     */
    private Integer maxTimes;

    /**
     * 当前使用次数
     */
    private Integer curTimes;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 失效时间
     */
    private Date invalidDate;

    private static final long serialVersionUID = 1L;

    public String getCallTicketticket() {
        return callTicketticket;
    }

    public void setCallTicketticket(String callTicketticket) {
        this.callTicketticket = callTicketticket;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResUuid() {
        return resUuid;
    }

    public void setResUuid(String resUuid) {
        this.resUuid = resUuid;
    }

    public Integer getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Integer getCurTimes() {
        return curTimes;
    }

    public void setCurTimes(Integer curTimes) {
        this.curTimes = curTimes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }
}