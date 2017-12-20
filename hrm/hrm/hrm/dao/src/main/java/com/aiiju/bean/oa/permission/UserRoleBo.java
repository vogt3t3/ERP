package com.aiiju.bean.oa.permission;


public class UserRoleBo {
	
    private Long id;
    private Long loginUserId;
    private String name;
    private Long deptId;
    private String deptName;
    private String email;
    private String phone;
    /**
     * 是否激活0未激活1已激活
     */
    private Byte isActive;
    private String isActiveName;
    /**
     * 用户已分配的角色
     */
    private String userRoles;
    private Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Byte getIsActive() {
		return isActive;
	}
	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}
	public String getIsActiveName() {
		return isActiveName;
	}
	public void setIsActiveName(String isActiveName) {
		this.isActiveName = isActiveName;
	}
	public String getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    
}
