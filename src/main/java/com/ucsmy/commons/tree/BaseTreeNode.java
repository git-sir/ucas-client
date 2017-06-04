package com.ucsmy.commons.tree;

import java.util.List;

/**
 * 树的基本节点
 * Created by chenqilin on 2017/4/14.
 */
public class BaseTreeNode extends BaseNode {

    private static final long serialVersionUID = 1L;

    /*
     * 子节点列表
     */
    private List<BaseTreeNode> children;

    public List<BaseTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTreeNode> children) {
        this.children = children;
    }
}
