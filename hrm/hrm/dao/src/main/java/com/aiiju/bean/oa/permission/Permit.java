package com.aiiju.bean.oa.permission;

import java.io.Serializable;

/**
 * 
 * @ClassName: Permit 
 * @Description: 权限实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:32:59 
 *
 */

public class Permit implements Serializable {

	private static final long serialVersionUID = 4951469947981301870L;
	
	private Long id;
	
	private Long parentId;
	
	private String name;
	
	private String originName;
	
	private String only;
	
	private String permExcept;
	
	private String permitActions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getOnly() {
		return only;
	}

	public void setOnly(String only) {
		this.only = only;
	}

	public String getPermExcept() {
		return permExcept;
	}

	public void setPermExcept(String permExcept) {
		this.permExcept = permExcept;
	}

	public String getPermitActions() {
		return permitActions;
	}

	public void setPermitActions(String permitActions) {
		this.permitActions = permitActions;
	}

}
