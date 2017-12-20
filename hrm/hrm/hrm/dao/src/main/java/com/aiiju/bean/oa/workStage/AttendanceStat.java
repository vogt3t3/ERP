package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
/**
 * 考勤统计返回对象实体类
 * 
 * @author BZ
 *
 */
public class AttendanceStat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//用户ID
	private Long userId;
	//工号
	private String empNo;
	//用户姓名
	private String userName;
	//部门ID
	private Long deptId;
	//部门名称
	private String deptName;
	//迟到
	private Integer lateDays;
	//早退
	private Integer leftDays;
	//加班
	private Integer overDays;
	//下班忘打卡
	private Integer offDutyForgetDays;
	//上班打卡--用于(应工作天数-上班打卡=上班未打卡)  
	private Integer onDutyDays;
	//上班忘打卡  (应工作天数-上班打卡=上班未打卡)
	private Integer onDutyForgetDays;
	//正常考勤
	private Integer normalDays;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Integer getLateDays() {
		return lateDays;
	}
	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}
	public Integer getLeftDays() {
		return leftDays;
	}
	public void setLeftDays(Integer leftDays) {
		this.leftDays = leftDays;
	}
	public Integer getOverDays() {
		return overDays;
	}
	public void setOverDays(Integer overDays) {
		this.overDays = overDays;
	}
	public Integer getOffDutyForgetDays() {
		return offDutyForgetDays;
	}
	public void setOffDutyForgetDays(Integer offDutyForgetDays) {
		this.offDutyForgetDays = offDutyForgetDays;
	}
	public Integer getOnDutyDays() {
		return onDutyDays;
	}
	public void setOnDutyDays(Integer onDutyDays) {
		this.onDutyDays = onDutyDays;
	}
	public Integer getOnDutyForgetDays() {
		return onDutyForgetDays;
	}
	public void setOnDutyForgetDays(Integer onDutyForgetDays) {
		this.onDutyForgetDays = onDutyForgetDays;
	}
	public Integer getNormalDays() {
		return normalDays;
	}
	public void setNormalDays(Integer normalDays) {
		this.normalDays = normalDays;
	}
}
