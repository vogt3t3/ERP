package com.aiiju.bean.oa.duty;

import java.io.Serializable;
import java.util.Date;

public class CompanyDutyInfo implements Serializable {
    /**
     * 职务ID
     */
    private Long id;

    /**
     * 职务名称
     */
    private String name;

    /**
     * 职务类型ID
     */
    private Long dutyTypeId;

    /**
     * 职等下限
     */
    private Long dutyLevelBottomId;

    /**
     * 职等上限
     */
    private Long dutyLevelTopId;

    /**
     * 职务序号
     */
    private String dutyNo;

    /**
     * 启用日期
     */
    private Date startDate;

    /**
     * 封存日期
     */
    private Date endDate;

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
    private String dutyScope;

    /**
     * 任职要求
     */
    private String dutyRequire;

    /**
     * 补充说明
     */
    private String extraRemark;

    /**
     * 能力要求
     */
    private String abilityRequire;

    //非数据库对应属性,对应上面部分字段的string名称
    private String dutyTypeName;
    private String dutyLevelBottomName;
    private String dutyLevelTopName;
    private String isSleepStr;
    private String startDateStr;
    private String endDateStr;
    public String getDutyTypeName() {
		return dutyTypeName;
	}

	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}

	public String getDutyLevelBottomName() {
		return dutyLevelBottomName;
	}

	public void setDutyLevelBottomName(String dutyLevelBottomName) {
		this.dutyLevelBottomName = dutyLevelBottomName;
	}

	public String getDutyLevelTopName() {
		return dutyLevelTopName;
	}

	public void setDutyLevelTopName(String dutyLevelTopName) {
		this.dutyLevelTopName = dutyLevelTopName;
	}

	public String getIsSleepStr() {
		return isSleepStr;
	}

	public void setIsSleepStr(String isSleepStr) {
		this.isSleepStr = isSleepStr;
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
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDutyScope() {
		return dutyScope;
	}

	public void setDutyScope(String dutyScope) {
		this.dutyScope = dutyScope;
	}

	public String getDutyRequire() {
		return dutyRequire;
	}

	public void setDutyRequire(String dutyRequire) {
		this.dutyRequire = dutyRequire;
	}

	public String getExtraRemark() {
		return extraRemark;
	}

	public void setExtraRemark(String extraRemark) {
		this.extraRemark = extraRemark;
	}

	public String getAbilityRequire() {
		return abilityRequire;
	}

	public void setAbilityRequire(String abilityRequire) {
		this.abilityRequire = abilityRequire;
	}

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

    public Long getDutyTypeId() {
        return dutyTypeId;
    }

    public void setDutyTypeId(Long dutyTypeId) {
        this.dutyTypeId = dutyTypeId;
    }

    public Long getDutyLevelBottomId() {
        return dutyLevelBottomId;
    }

    public void setDutyLevelBottomId(Long dutyLevelBottomId) {
        this.dutyLevelBottomId = dutyLevelBottomId;
    }

    public Long getDutyLevelTopId() {
        return dutyLevelTopId;
    }

    public void setDutyLevelTopId(Long dutyLevelTopId) {
        this.dutyLevelTopId = dutyLevelTopId;
    }

    public String getDutyNo() {
        return dutyNo;
    }

    public void setDutyNo(String dutyNo) {
        this.dutyNo = dutyNo == null ? null : dutyNo.trim();
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
        sb.append(", name=").append(name);
        sb.append(", dutyTypeId=").append(dutyTypeId);
        sb.append(", dutyLevelBottomId=").append(dutyLevelBottomId);
        sb.append(", dutyLevelTopId=").append(dutyLevelTopId);
        sb.append(", dutyNo=").append(dutyNo);
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