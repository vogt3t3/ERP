package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.List;

import com.aiiju.bean.oa.dept.Department;

public class UserCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long companyId;
	private Integer nums;
    /**
     * 所属组织
     */
    private Long deptId;
    private String deptName;

    private List<Long> deptIds;
    /**
     * 性别： 1 男 2 女
     */
    private Byte sex;
    /**
     * 状态 ：1正常在岗2待岗3长期休假4其他在册
     */
    private String status;
    /**
     * 职等
     */
    private Long dutyTypeId;
    private String dutyTypeName;
    /**
     * 入职年份
     */
    private String nian;
    /**
     * 年龄
     */
    private Integer age;
    
    private Integer endAge;
    private Integer startAge;
    /**
     * 学历 1初中2高中3职高4大专5本科6研究生7博士8其他
     */
    private String eduationalLevel;
    
    private String companyName;
    
    private List<Department> deptList;
	
	public List<Department> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDutyTypeName() {
		return dutyTypeName;
	}
	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}
	public Long getDutyTypeId() {
		return dutyTypeId;
	}
	public void setDutyTypeId(Long dutyTypeId) {
		this.dutyTypeId = dutyTypeId;
	}
	public String getNian() {
		return nian;
	}
	public void setNian(String nian) {
		this.nian = nian;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getEndAge() {
		return endAge;
	}
	public void setEndAge(Integer endAge) {
		this.endAge = endAge;
	}
	public Integer getStartAge() {
		return startAge;
	}
	public void setStartAge(Integer startAge) {
		this.startAge = startAge;
	}
	public String getEduationalLevel() {
		return eduationalLevel;
	}
	public void setEduationalLevel(String eduationalLevel) {
		this.eduationalLevel = eduationalLevel;
	}
	@Override
	public String toString() {
		return "UserCount [companyId=" + companyId + ", nums=" + nums
				+ ", deptId=" + deptId + ", sex=" + sex + ", status=" + status
				+ ", dutyTypeId=" + dutyTypeId + ", nian=" + nian + ", age="
				+ age + ", endAge=" + endAge + ", startAge=" + startAge
				+ ", eduationalLevel=" + eduationalLevel + "]";
	}
    
    
}
