package com.aiiju.bean.common;

import java.io.Serializable;
/**
 * 枚举表
 * @author qiqi
 * @date 2016-10-26 11:11:11
 */
public class BasicEnum implements Serializable {
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