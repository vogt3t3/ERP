package com.aiiju.bean.oa.attendance.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aiiju.bean.oa.attendance.SignRuleTime;
public class AttendanceRuleBo implements Serializable {
	
	private static final long serialVersionUID = -1761273107312005463L;

	private Long id;
	
	private Long companyId;
	
	private String name;
	
	/**
	 * 考勤规则具体的考勤时段
	 */
	private List<SignRuleTime> signRuleTimeList;
	
    /**
     * 纬度
     */
    private String positionLat;
    /**
     * 经度
     */
    private String positionLon;
    
    /**
     * 允许的偏差
     */
    private Integer positionOffset;
   
    /**
     * 规则创建者id
     */
    private Long creatorId;
    /**
     * 规则签到地址(公司地址)
     */
    private String companyAddress;

    private Date createDate;

    private Date updateDate;
    /**
     * 考勤类型 0:常规考勤 1:灵活考勤
     */
    private Byte ruleType;
    /**
     * 是否遵循法定假日 0:是 1:否
     */
    private Byte isLegalHolidays;
    private String ruleTypeStr;
    /**
     * 考勤规则设置给的部门id
     * (设置给哪个部门)
     */
    private List<Integer> ruleForDepId;
    /**
     * 考勤规则设置给的员工id
     * (设置给哪个员工)
     */
    private List<Long> ruleForEmpId;
    /**
     * 考勤规则对应的部门名称或员工名称
     * 如：财务部,运营部,销售部,佩奇,大和
     */
    private String ruleForDepOrEmp;
    
	public Integer getPositionOffset() {
		return positionOffset;
	}
	public void setPositionOffset(Integer positionOffset) {
		this.positionOffset = positionOffset;
	}
	public Byte getIsLegalHolidays() {
		return isLegalHolidays;
	}
	public void setIsLegalHolidays(Byte isLegalHolidays) {
		this.isLegalHolidays = isLegalHolidays;
	}
	public String getPositionLat() {
		return positionLat;
	}
	public void setPositionLat(String positionLat) {
		this.positionLat = positionLat;
	}
	public String getPositionLon() {
		return positionLon;
	}
	public void setPositionLon(String positionLon) {
		this.positionLon = positionLon;
	}
	public List<Integer> getRuleForDepId() {
		return ruleForDepId;
	}
	public void setRuleForDepId(List<Integer> ruleForDepId) {
		this.ruleForDepId = ruleForDepId;
	}
	public List<Long> getRuleForEmpId() {
		return ruleForEmpId;
	}
	public void setRuleForEmpId(List<Long> ruleForEmpId) {
		this.ruleForEmpId = ruleForEmpId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuleTypeStr() {
		return ruleTypeStr;
	}
	public void setRuleTypeStr(String ruleTypeStr) {
		this.ruleTypeStr = ruleTypeStr;
	}
	public String getRuleForDepOrEmp() {
		return ruleForDepOrEmp;
	}
	public void setRuleForDepOrEmp(String ruleForDepOrEmp) {
		this.ruleForDepOrEmp = ruleForDepOrEmp;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public List<SignRuleTime> getSignRuleTimeList() {
		return signRuleTimeList;
	}
	public void setSignRuleTimeList(List<SignRuleTime> signRuleTimeList) {
		this.signRuleTimeList = signRuleTimeList;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Byte getRuleType() {
		return ruleType;
	}
	public void setRuleType(Byte ruleType) {
		this.ruleType = ruleType;
	}
	
}
