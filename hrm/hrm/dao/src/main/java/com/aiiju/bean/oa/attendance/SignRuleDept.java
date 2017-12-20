package com.aiiju.bean.oa.attendance;

import java.io.Serializable;
import java.util.Date;

public class SignRuleDept implements Serializable {
    private Integer id;

    private Integer companyId;

    private Integer deptId;

    private Integer ruleId;
    /**
     * 虚拟的父级部门id
     */
    private Integer pdetpId;

    private Date createDate;

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getPdetpId() {
        return pdetpId;
    }

    public void setPdetpId(Integer pdetpId) {
        this.pdetpId = pdetpId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}