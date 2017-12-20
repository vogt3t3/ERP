package com.aiiju.bean.oa.position;

import java.io.Serializable;
import java.util.Date;

public class CompanyPositionInfo implements Serializable {
    /**
     * 职位ID
     */
    private Long id;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 所属组织
     */
    private Long deptId;

    /**
     * 所属职务
     */
    private Long dutyId;

    /**
     * 职务类型
     */
    private Long dutyTypeId;

    /**
     * 评估分数
     */
    private Double evaluationScore;

    /**
     * 评估等级
     */
    private String evaluationLevel;

    /**
     * 职位序号
     */
    private String positionNo;

    /**
     * 启用日期
     */
    private String startDate;

    /**
     * 封存日期
     */
    private String endDate;

    /**
     * 是否封存
     */
    private Byte isSleep;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;
    /**
     * 描述说明
     */
    private String remark;

    /**
     * 职责范围
     */
    private String positionScope;

    /**
     * 任职要求
     */
    private String positionRequire;
    
    //非数据库字段,对应上面部分id的name
    private String dutyName;
    private String deptName;
    
    private String dutyTypeName;
    private String startDateStr;
    private String endDateStr;
    private String isSleepStr;
    
    
    
    public Long getDutyTypeId() {
		return dutyTypeId;
	}

	public void setDutyTypeId(Long dutyTypeId) {
		this.dutyTypeId = dutyTypeId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getDutyTypeName() {
		return dutyTypeName;
	}

	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getIsSleepStr() {
		return isSleepStr;
	}

	public void setIsSleepStr(String isSleepStr) {
		this.isSleepStr = isSleepStr;
	}


	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPositionScope() {
		return positionScope;
	}

	public void setPositionScope(String positionScope) {
		this.positionScope = positionScope;
	}

	public String getPositionRequire() {
		return positionRequire;
	}

	public void setPositionRequire(String positionRequire) {
		this.positionRequire = positionRequire;
	}

	public String getExtraRemark() {
		return extraRemark;
	}

	public void setExtraRemark(String extraRemark) {
		this.extraRemark = extraRemark;
	}

	/**
     * 补充说明
     */
    private String extraRemark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Double getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Double evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(String evaluationLevel) {
        this.evaluationLevel = evaluationLevel == null ? null : evaluationLevel.trim();
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo == null ? null : positionNo.trim();
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

	public Byte getIsSleep() {
        return isSleep;
    }

    public void setIsSleep(Byte isSleep) {
        this.isSleep = isSleep;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", deptId=").append(deptId);
        sb.append(", dutyId=").append(dutyId);
        sb.append(", dutyTypeId=").append(dutyTypeId);
        sb.append(", evaluationScore=").append(evaluationScore);
        sb.append(", evaluationLevel=").append(evaluationLevel);
        sb.append(", positionNo=").append(positionNo);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", isSleep=").append(isSleep);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}