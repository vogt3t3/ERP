package com.aiiju.bean.oa.salary;

import java.io.Serializable;

/**
 * 基本工资记录  实体类
 * @author R
 * 2016-12-19
 */
public class SpwSalaryBasicLog implements Serializable {
    
	private static final long serialVersionUID = 7058097708993671617L;
	
	//主键
	private Long id;
	//员工ID
	private Long userId;
	//员工姓名
	private String userName;
	//员工编号
	private String empNo;
	//部门ID
	private Long deptId;
	//部门名称
	private String deptName;
	//调整前薪资
	private Double beforeBasicPay;
	//调整前薪资（String）
	private String beforeBasicPayStr;
	//调整后薪资
	private Double afterBasicPay;
	//调整后薪资（String）
	private String afterBasicPayStr;
	//调整幅度
	private Double adjustRange;
	//调整幅度（String）
	private String adjustRangeStr;
	//生效时间
	private String forceTime;
	//调整原因
	private String adjustReason;
	//操作人ID
	private String operateUserId;
	//操作人姓名
	private String operateUserName;
	//是否初始薪资 0：否 ；1：是
	private Integer isInit;
	//创建时间
	private String createTime;
	//公司ID
	private Long companyId;
	//是否可以删除
	private boolean canDel;
	// 入职日期
	private String joinDate;
	
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
	public Double getBeforeBasicPay() {
		return beforeBasicPay;
	}
	public void setBeforeBasicPay(Double beforeBasicPay) {
		this.beforeBasicPay = beforeBasicPay;
	}
	public Double getAfterBasicPay() {
		return afterBasicPay;
	}
	public void setAfterBasicPay(Double afterBasicPay) {
		this.afterBasicPay = afterBasicPay;
	}
	public Double getAdjustRange() {
		return adjustRange;
	}
	public void setAdjustRange(Double adjustRange) {
		this.adjustRange = adjustRange;
	}
	public String getForceTime() {
		return forceTime;
	}
	public void setForceTime(String forceTime) {
		this.forceTime = forceTime;
	}
	public String getAdjustReason() {
		return adjustReason;
	}
	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public Integer getIsInit() {
		return isInit;
	}
	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public boolean isCanDel() {
		return canDel;
	}
	public void setCanDel(boolean canDel) {
		this.canDel = canDel;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getBeforeBasicPayStr() {
		return beforeBasicPayStr;
	}
	public void setBeforeBasicPayStr(String beforeBasicPayStr) {
		this.beforeBasicPayStr = beforeBasicPayStr;
	}
	public String getAfterBasicPayStr() {
		return afterBasicPayStr;
	}
	public void setAfterBasicPayStr(String afterBasicPayStr) {
		this.afterBasicPayStr = afterBasicPayStr;
	}
	public String getAdjustRangeStr() {
		return adjustRangeStr;
	}
	public void setAdjustRangeStr(String adjustRangeStr) {
		this.adjustRangeStr = adjustRangeStr;
	}
}