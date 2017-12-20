package com.aiiju.bean.oa.attendance;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤时段历史对象
 * @author 小辉
 *
 */
public class SignRuleTimeHistory implements Serializable {
    private Integer id;

    private Integer companyId;

    private Integer ruleId;

    private String workDay;

    private String signInTime;

    private String signOutTime;

    private String signInTime2;

    private String signOutTime2;

    private String signInTime3;

    private String signOutTime3;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay == null ? null : workDay.trim();
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
}