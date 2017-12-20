package com.aiiju.bean.oa.permission;

import java.io.Serializable;

/**
 * 
 * @ClassName: Menu 
 * @Description: 菜单实体类
 * @author 哪吒 
 * @date 2016年12月8日 下午2:24:20 
 *
 */

public class Menu implements Serializable {

	private static final long serialVersionUID = 8796870809841537681L;

	private Long id;

    private String menuName;

    private String menuOriginName;

    private String buildPath;

    private Long parentId;

    private String url;

    private Integer menuOrder;
    
    private String ext;
    
    private String menuTarget;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
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