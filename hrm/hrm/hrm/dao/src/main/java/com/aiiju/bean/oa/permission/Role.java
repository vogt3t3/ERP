package com.aiiju.bean.oa.permission;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: Role 
 * @Description: 角色实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:36:57 
 *
 */

public class Role implements Serializable {

	private static final long serialVersionUID = 2773011858034435892L;
    
	private Long id;
	
	private String roleName;
	
	private String remark;
	// 公司ID
	private Long companyId;
	
	private Long parentId;
	
	private Integer roleOrder;
	
	private List<Permit> permitList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getRoleOrder() {
		return roleOrder;
	}

	public void setRoleOrder(Integer roleOrder) {
		this.roleOrder = roleOrder;
	}

	public List<Permit> getPermitList() {
		return permitList;
	}

	public void setPermitList(List<Permit> permitList) {
		this.permitList = permitList;
	}
}