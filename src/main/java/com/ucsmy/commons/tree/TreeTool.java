package com.ucsmy.commons.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具类
 * Created by chenqilin on 2017/4/14.
 */
public class TreeTool {

    /**
     * 将列表封装成树的结构
     *
     * @param list
     * @return
     */
    public static List<? extends BaseTreeNode> getTreeList(List<? extends BaseTreeNode> list) {
        List<BaseTreeNode> nodeList = new ArrayList<>();
        for (BaseTreeNode node1 : list) {
            boolean mark = false;
            for (BaseTreeNode node2 : list) {
                if (node2.getChildren() == null) {
                    node2.setChildren(new ArrayList<BaseTreeNode>());
                }
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    node2.getChildren().add(node1);
                    break;
                }
            }
            if (!mark && (node1.getParentId() == null
                    || "".equals(node1.getParentId().trim())
                    || "0".equals(node1.getParentId().trim())) ) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }
}
