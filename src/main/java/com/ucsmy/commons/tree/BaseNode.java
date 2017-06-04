package com.ucsmy.commons.tree;

import java.io.Serializable;

/**
 * 一般树节点，不带子节点
 * Created by chenqilin on 2017/5/22.
 */
public class BaseNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 节点编号
	 */
    private String id;

    /*
     * 父节点模块编号
     */
    private String parentId;

    /*
     * 模块名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
