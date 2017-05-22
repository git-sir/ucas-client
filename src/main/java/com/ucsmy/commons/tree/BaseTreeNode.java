package com.ucsmy.commons.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树的基本节点
 * Created by chenqilin on 2017/4/14.
 */
public class BaseTreeNode implements Serializable {

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

    /*
     * 子节点列表
     */
    private List<BaseTreeNode> children;

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

    public List<BaseTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTreeNode> children) {
        this.children = children;
    }
}
