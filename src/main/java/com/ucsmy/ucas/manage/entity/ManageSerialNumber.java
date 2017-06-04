package com.ucsmy.ucas.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ManageSerialNumber implements Serializable {

    /**
     * 业务主键
     */
    private String serialNumberId;

    /**
     * 当前序列号
     */
    private Long currentSerialNumber;

    /**
     * 自增量
     */
    private Long generatedSerialNumber;

    /**
     * 修改时间
     */
    private Date serialNumberDate;

    private static final long serialVersionUID = 1L;

    public String getSerialNumberId() {
        return serialNumberId;
    }

    public void setSerialNumberId(String serialNumberId) {
        this.serialNumberId = serialNumberId;
    }

    public Long getCurrentSerialNumber() {
        return currentSerialNumber;
    }

    public void setCurrentSerialNumber(Long currentSerialNumber) {
        this.currentSerialNumber = currentSerialNumber;
    }

    public Long getGeneratedSerialNumber() {
        return generatedSerialNumber;
    }

    public void setGeneratedSerialNumber(Long generatedSerialNumber) {
        this.generatedSerialNumber = generatedSerialNumber;
    }

    public Date getSerialNumberDate() {
        return serialNumberDate;
    }

    public void setSerialNumberDate(Date serialNumberDate) {
        this.serialNumberDate = serialNumberDate;
    }
}