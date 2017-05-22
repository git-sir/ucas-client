package com.ucsmy.ucas.manage.ext;

import com.ucsmy.commons.tree.BaseTreeNode;


/**
 * module扩展pojo，用于处理树状展示
 * Created by chenqilin on 2017/4/14.
 */
public class ModuleTreePojo extends BaseTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 *  模块说明
	 */
	private String description;
	/*
	 * 模块优先级
	 */
	private int priority;
	/*
	 * 判断标志
	 */
	private String sn;
	/*
	 * 响应地址
	 */
	private String url;
	/*
	 * 是否叶子节点 
	 */
	private String leaf;

	//图标
	private String image;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
