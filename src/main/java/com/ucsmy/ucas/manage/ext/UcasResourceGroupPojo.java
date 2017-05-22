package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.UcasResourceGroup;

/**
 * 资源组列表扩展实体
 * Created by chenqilin on 2017/5/3.
 */
public class UcasResourceGroupPojo extends UcasResourceGroup {

    private static final long serialVersionUID = 1L;

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
