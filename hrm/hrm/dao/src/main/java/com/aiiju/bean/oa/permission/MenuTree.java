package com.aiiju.bean.oa.permission;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: MenuTree 
 * @Description: 菜单树
 * @author BZ
 * @date 2016年12月12日 上午10:32:59 
 *
 */

public class MenuTree implements Serializable {

	private static final long serialVersionUID = -5841852765021833011L;

	private Long id;
	
	public Long parentId;
	
	private String menuName;
	
	private String menuOriginName;
	
	private String buildPath;
	
	private String url;

	private String menuOrder;
	
	private String ext;
	
	private String menuTarget;
	
	private List<MenuTree> children;

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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuOriginName() {
		return menuOriginName;
	}

	public void setMenuOriginName(String menuOriginName) {
		this.menuOriginName = menuOriginName;
	}

	public String getBuildPath() {
		return buildPath;
	}

	public void setBuildPath(String buildPath) {
		this.buildPath = buildPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getMenuTarget() {
		return menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}
}
