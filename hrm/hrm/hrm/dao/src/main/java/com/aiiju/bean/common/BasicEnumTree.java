package com.aiiju.bean.common;

import java.io.Serializable;
import java.util.List;
/**
 * 枚举表
 * @author qiqi
 * @date 2016-10-26 11:11:11
 */
public class BasicEnumTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 2职业证书名称 1专业资格名称 3 能力素养
     */
    private Byte type;
    /**
     * 名字
     */
    private String name;
    /**
     * 父类Id
     */
    private Long parentId;
    /**
     * 子节点
     */
    private List<BasicEnumTree> children;
    /**
     * 是否显示
     */
    private boolean isShow;
    
    public BasicEnumTree(Long id,Byte type, String name,Long parentId,boolean isShow){
    	this.id=id;
    	this.type=type;
    	this.name=name;
    	this.parentId=parentId;
    	this.isShow=isShow;
    }

	public boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}
	public List<BasicEnumTree> getChildren() {
		return children;
	}
	public void setChildren(List<BasicEnumTree> children) {
		this.children = children;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "BasicEnum [id=" + id + ", type=" + type + ", name=" + name
				+ ", parentId=" + parentId + "]";
	}
    
    
}