package com.aiiju.bean.oa.permission;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: PermitTree 
 * @Description: 权限树
 * @author BZ
 * @date 2016年12月9日 下午2:32:59 
 *
 */

public class PermitTree implements Serializable {

	private static final long serialVersionUID = -6463646003718801702L;

	private Long id;
	
	public Long parentId;
	
	private String name;
	
	private String originName;
	
	private String only;
	
	private String permExcept;
	
	private String permitActions;
	
	private List<PermitTree> children;

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

	public List<PermitTree> getChildren() {
		return children;
	}

	public void setChildren(List<PermitTree> children) {
		this.children = children;
	}
}
