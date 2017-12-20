package com.aiiju.bean.oa.salary;

import java.io.Serializable;

/**
 * 基本工资  实体类
 * @author R
 * 2016-12-19
 */
public class SpwSalaryBasic implements Serializable {
    
	private static final long serialVersionUID = 3616335526343313392L;
	
	//主键
	private Long id;
	//员工ID
	private Long userId;
	//员工姓名
	private String userName;
	//员工编号
	private String empNo;
	//员工编制
	private String userDraw;
	//员工电话
	private String phone;
	//员工邮箱
	private String email;
	//员工入职时间
	private String joinDate;
	//部门ID
	private Long deptId;
	//部门名称
	private String deptName;
	//基本工资
	private Double basicPay;
	//创建时间
	private String createTime;
	//更新时间
	private String updateTime;
	//公司ID
	private Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getUserDraw() {
		return userDraw;
	}
	public void setUserDraw(String userDraw) {
		this.userDraw = userDraw;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
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
	public Double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}