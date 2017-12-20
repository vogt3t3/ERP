package com.aiiju.bean.oa.permission;

import java.io.Serializable;

/**
 * 
 * @ClassName: PermitRole 
 * @Description: 角色权限关联实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:43:18 
 *
 */

public class PermitRole implements Serializable {

	private static final long serialVersionUID = 7812874517039127490L;

	private Long companyId;
	
	private Long roleId;
	
	private Long permitId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermitId() {
		return permitId;
	}

	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}
}
