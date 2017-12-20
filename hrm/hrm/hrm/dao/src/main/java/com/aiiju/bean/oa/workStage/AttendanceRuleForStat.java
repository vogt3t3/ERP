package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
/**
 * 考勤规则--考勤统计需要
 * 
 * @author BZ
 *
 */
public class AttendanceRuleForStat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//用户ID
	private Long userId;
	//公司ID
	private Long companyId;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//规则ID
	private Long ruleId;
	//规则对应的工作日
	private String weekDays;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getWeekDays() {
		return weekDays;
	}
	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}
}
