package com.aiiju.bean.oa.permission;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 角色管理列表实体对象
 * @author 小辉
 *
 */
public class RoleManagerBo {
	private String id;
	private String companyId;
	//角色名称
	private String roleName;
	//如：协同办公 HRM
	private String permitName;
	//某个角色对应的所有权限集
	private List<JSONObject> permitList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermitName() {
		return permitName;
	}
	public void setPermitName(String permitName) {
		this.permitName = permitName;
	}
	public List<JSONObject> getPermitList() {
		return permitList;
	}
	public void setPermitList(List<JSONObject> permitList) {
		this.permitList = permitList;
	}
}
