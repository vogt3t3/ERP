package com.aiiju.bean.oa.salary;

import java.io.Serializable;

public class SpwSalaryPay implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 工号
     */
    private String staffNo;

    /**
     * 名字
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 部门ID
     */
    private Long deptId;
    private String deptName;

    /**
     * 发放月份
     */
    private String releaseTime;

    /**
     * 基本工资
     */
    private Double basicPay;

    /**
     * 提成
     */
    private Double percentage;

    /**
     * 年终奖
     */
    private Double yearEndBonuses;

    /**
     * 请假天数
     */
    private String leaveDays;

    /**
     * 请假扣钱
     */
    private Double leaveDeductMoney;

    /**
     * 加班费
     */
    private Double overtimePay;

    /**
     * 考勤扣钱
     */
    private Double attendanceDeduct;

    /**
     * 奖金
     */
    private Double bonus;

    /**
     * 福利
     */
    private Double welfare;

    /**
     * 工资扣减
     */
    private Double totalDeduct;

    /**
     * 社保个人
     */
    private Double socialSecurityPays;

    /**
     * 公积金个人
     */
    private Double providentFundPay;

    /**
     * 代扣个税
     */
    private Double withholdingTax;

    /**
     * 实发工资
     */
    private Double needPayment;

    /**
     * 加密密码
     */
    private String pswKey;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 是否撤回
     */
    private Integer isWithdraw;

    /**
     * 薪资批次ID
     */
    private Long pid;

    /**
     * 否是发送
     */
    private Integer isSend;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Double getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(Double basicPay) {
        this.basicPay = basicPay;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getYearEndBonuses() {
        return yearEndBonuses;
    }

    public void setYearEndBonuses(Double yearEndBonuses) {
        this.yearEndBonuses = yearEndBonuses;
    }

    public String getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays == null ? null : leaveDays.trim();
    }

    public Double getLeaveDeductMoney() {
        return leaveDeductMoney;
    }

    public void setLeaveDeductMoney(Double leaveDeductMoney) {
        this.leaveDeductMoney = leaveDeductMoney;
    }

    public Double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(Double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public Double getAttendanceDeduct() {
        return attendanceDeduct;
    }

    public void setAttendanceDeduct(Double attendanceDeduct) {
        this.attendanceDeduct = attendanceDeduct;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getWelfare() {
        return welfare;
    }

    public void setWelfare(Double welfare) {
        this.welfare = welfare;
    }

    public Double getTotalDeduct() {
        return totalDeduct;
    }

    public void setTotalDeduct(Double totalDeduct) {
        this.totalDeduct = totalDeduct;
    }

    public Double getSocialSecurityPays() {
        return socialSecurityPays;
    }

    public void setSocialSecurityPays(Double socialSecurityPays) {
        this.socialSecurityPays = socialSecurityPays;
    }

    public Double getProvidentFundPay() {
        return providentFundPay;
    }

    public void setProvidentFundPay(Double providentFundPay) {
        this.providentFundPay = providentFundPay;
    }

    public Double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(Double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public Double getNeedPayment() {
        return needPayment;
    }

    public void setNeedPayment(Double needPayment) {
        this.needPayment = needPayment;
    }

    public String getPswKey() {
        return pswKey;
    }

    public void setPswKey(String pswKey) {
        this.pswKey = pswKey == null ? null : pswKey.trim();
    }

    

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCreateTime() {
		return createTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsWithdraw() {
		return isWithdraw;
	}

	public void setIsWithdraw(Integer isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
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

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", staffNo=").append(staffNo);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", deptId=").append(deptId);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", basicPay=").append(basicPay);
        sb.append(", percentage=").append(percentage);
        sb.append(", yearEndBonuses=").append(yearEndBonuses);
        sb.append(", leaveDays=").append(leaveDays);
        sb.append(", leaveDeductMoney=").append(leaveDeductMoney);
        sb.append(", overtimePay=").append(overtimePay);
        sb.append(", attendanceDeduct=").append(attendanceDeduct);
        sb.append(", bonus=").append(bonus);
        sb.append(", welfare=").append(welfare);
        sb.append(", totalDeduct=").append(totalDeduct);
        sb.append(", socialSecurityPays=").append(socialSecurityPays);
        sb.append(", providentFundPay=").append(providentFundPay);
        sb.append(", withholdingTax=").append(withholdingTax);
        sb.append(", needPayment=").append(needPayment);
        sb.append(", pswKey=").append(pswKey);
        sb.append(", isDel=").append(isDel);
        sb.append(", isWithdraw=").append(isWithdraw);
        sb.append(", pid=").append(pid);
        sb.append(", isSend=").append(isSend);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
	
	// 重写hashCode方法，把name当作对象的哈希表值返回
	@Override
	public int hashCode() {
		return phone.hashCode();
	}

	// 重写equals方法
	@Override
	public boolean equals(Object obj) {
		SpwSalaryPay user = (SpwSalaryPay) obj;
		return user.phone.equals(phone);
	}
}