package com.ucsmy.ucas.manage.ext;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 首页左侧菜单
 * Created by chenqilin on 2017/4/17.
 */
public class MainModulePojo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /*
     * 节点编号
     */
    private String id;
    /*
     * 父节点编号
     */
    private String parentId;
    /*
     * 节点名称
     */
    private String name;
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