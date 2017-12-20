package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 人事事务
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:23:37
 */
public class PersonnelAffairs implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 人事事务类型
     */
    private Byte type;
    private String typeName;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 所属部门
     */
    private Long deptId;
    private String deptName;

    /**
     * 状态
     */
    private Byte status;
    private String statusName;

    /**
     * 具体时间
     */
    private Date abstractDate;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String type) {
	 	if("1".equals(type)){
    		typeName="转正";
    	}else if("2".equals(type)){
    		typeName="调动";
		}else if ("3".equals(type)) {
			typeName="离职";
		}else if ("4".equals(type)) {
			typeName="重新入职";
		}
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String status) {
		if("1".equals(status)){
			statusName="在职";
		}else if("2".equals(status)) {
			statusName="离职";
		}
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getAbstractDate() {
        return abstractDate;
    }

    public void setAbstractDate(Date abstractDate) {
        this.abstractDate = abstractDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", typeName=").append(typeName);
        sb.append(", userId=").append(userId);
        sb.append(", deptId=").append(deptId);
        sb.append(", deptName=").append(deptName);
        sb.append(", status=").append(status);
        sb.append(", statusName=").append(statusName);
        sb.append(", abstractDate=").append(abstractDate);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}