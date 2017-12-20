package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 汇报关系
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:25:09
 */
public class ReportRelation implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 汇报依据
     */
    private Long reportBasis;

    /**
     * 汇报对象
     */
    private Long toUserId;
    private String userName;

    /**
     * 用户Id
     */
    private Long fromUserId;

    /**
     * 汇报对象所在部门
     */
    private Long toDeptId;
    private String toDeptName;

    /**
     * 用户所在部门
     */
    private Long fromDeptId;
    private String fromDeptName;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToDeptName() {
		return toDeptName;
	}

	public void setToDeptName(String toDeptName) {
		this.toDeptName = toDeptName;
	}

	public String getFromDeptName() {
		return fromDeptName;
	}

	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getReportBasis() {
        return reportBasis;
    }

    public void setReportBasis(Long reportBasis) {
        this.reportBasis = reportBasis;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToDeptId() {
        return toDeptId;
    }

    public void setToDeptId(Long toDeptId) {
        this.toDeptId = toDeptId;
    }

    public Long getFromDeptId() {
        return fromDeptId;
    }

    public void setFromDeptId(Long fromDeptId) {
        this.fromDeptId = fromDeptId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", reportBasis=").append(reportBasis);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", userName=").append(userName);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toDeptId=").append(toDeptId);
        sb.append(", toDeptName=").append(toDeptName);
        sb.append(", fromDeptId=").append(fromDeptId);
        sb.append(", fromDeptName=").append(fromDeptName);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}