package com.aiiju.bean.oa.permission;

import java.io.Serializable;

/**
 * 
 * @ClassName: PermitMenu 
 * @Description: 权限菜单关联实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:35:07 
 *
 */

public class PermitMenu implements Serializable {

	private static final long serialVersionUID = -4542990909897451664L;

	// 菜单ID
	private Long menuId;
	// 权限ID
	private Long permitId;
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getPermitId() {
		return permitId;
	}
	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}
}
