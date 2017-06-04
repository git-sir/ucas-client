package com.ucsmy.ucas.manage.ext;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.tree.BaseNode;

import java.io.Serializable;
import java.util.List;

/**
 * 首页左侧菜单
 * Created by chenqilin on 2017/4/17.
 */
public class MainModulePojo extends BaseNode {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * 节点样式
     */
    private String className;
    /*
     * 链接
     */
    private String url;
    /*
     * 子节点
     */
    private List<MainModulePojo> children;

    private boolean childShow;

    private String href;

    private JSONObject icon;

    private String image;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MainModulePojo> getChildren() {
        return children;
    }

    public void setChildren(List<MainModulePojo> children) {
        this.children = children;
    }

    public boolean isChildShow() {
        return childShow;
    }

    public void setChildShow(boolean childShow) {
        this.childShow = childShow;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public JSONObject getIcon() {
        return icon;
    }

    public void setIcon(JSONObject icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
