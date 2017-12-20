package com.aiiju.bean.oa.permission;

import java.io.Serializable;

/**
 * 
 * @ClassName: RoleUser 
 * @Description: 用户角色关联实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:42:49 
 *
 */

public class RoleUser implements Serializable {

	private static final long serialVersionUID = 840669698566140375L;

	private Long userId;
	
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
