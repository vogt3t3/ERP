package com.aiiju.serviceImpl.attendance.vo;

import java.util.List;

public class AttendanceRuleVo {
	private Integer id;
	
    private String name;
    /**
     * 考勤规则类型 0:常规考勤 1:灵活考勤
     */
    private Byte ruleType;
    /**
     * 考勤工作日
     */
    private String workDay;
    /**
     * 是否遵循法定假日 0:是 1:否
     */
    private Byte isLegalHolidays;

    private String signInTime;

    private String signOutTime;

    private String signInTime2;

    private String signOutTime2;

    private String signInTime3;

    private String signOutTime3;
    /**
     * 经度
     */
    private String positionLon;
    /**
     * 纬度
     */
    private String positionLat;
    /**
     * 允许的位置偏差
     */
    private Integer positionOffset;
    /**
     * 规则签到地址(公司地址)
     */
    private String companyAddress;
    
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
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
        this.name = name == null ? null : name.trim();
    }

    public Byte getRuleType() {
        return ruleType;
    }

    public void setRuleType(Byte ruleType) {
        this.ruleType = ruleType;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay == null ? null : workDay.trim();
    }

    public Byte getIsLegalHolidays() {
        return isLegalHolidays;
    }

    public void setIsLegalHolidays(Byte isLegalHolidays) {
        this.isLegalHolidays = isLegalHolidays;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime == null ? null : signInTime.trim();
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime == null ? null : signOutTime.trim();
    }

    public String getSignInTime2() {
        return signInTime2;
    }

    public void setSignInTime2(String signInTime2) {
        this.signInTime2 = signInTime2 == null ? null : signInTime2.trim();
    }

    public String getSignOutTime2() {
        return signOutTime2;
    }

    public void setSignOutTime2(String signOutTime2) {
        this.signOutTime2 = signOutTime2 == null ? null : signOutTime2.trim();
    }

    public String getSignInTime3() {
        return signInTime3;
    }

    public void setSignInTime3(String signInTime3) {
        this.signInTime3 = signInTime3 == null ? null : signInTime3.trim();
    }

    public String getSignOutTime3() {
        return signOutTime3;
    }

    public void setSignOutTime3(String signOutTime3) {
        this.signOutTime3 = signOutTime3 == null ? null : signOutTime3.trim();
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon == null ? null : positionLon.trim();
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat == null ? null : positionLat.trim();
    }

    public Integer getPositionOffset() {
        return positionOffset;
    }

    public void setPositionOffset(Integer positionOffset) {
        this.positionOffset = positionOffset;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }
}
