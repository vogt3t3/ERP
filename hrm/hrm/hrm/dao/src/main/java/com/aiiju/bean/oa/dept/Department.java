package com.aiiju.bean.oa.dept;

import java.io.Serializable;
import java.util.Date;

public class Department implements Serializable{
	
	private static final long serialVersionUID = 1400815639775169708L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织简称
     */
    private String shortName;

    /**
     * 上级组织
     */
    private Integer parentDeptId;
    
    /**
     * 上级节点--zTree需要该字段
     */
    private Integer pid;

    /**
     * 组织层次
     */
    private Byte level;

 

    /**
     * 组织编号(部门序号)
     */
    private String deptNo;

    /**
     * 所属行业类别
     */
    private String industryType;

    /**
     * 登记注册类型
     */
    private String registerType;

    /**
     * 法人单位编码
     */
    private String companyNo;

    /**
     * 法人代表
     */
    private String corporate;

    /**
     * 纳税人登记号
     */
    private String taxpayerRegistrationNo;

    /**
     * 批准成立文号
     */
    private String approvalDocNo;

    /**
     * 行政区划代码
     */
    private String administrativeDivisionCode;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 当前编制人数
     */
    private Integer personNum;

    /**
     * 当前在编人数
     */
    private Integer curPersonNum;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;


    /**
     * 描述说明
     */
    private String remark;
    //缺编人数
    private Integer lackpersonNum;
    //负责人姓名
  	private String userName;
  	//负责人id
  	private Integer userId;
  	//上级部门名称
  	private String parentDeptName;
  	
  	
  	//区分员工和部门的状态码：1为员工 2为部门
    private Integer isGroup;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Integer getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Integer parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

   
    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType == null ? null : industryType.trim();
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType == null ? null : registerType.trim();
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo == null ? null : companyNo.trim();
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate == null ? null : corporate.trim();
    }

    public String getTaxpayerRegistrationNo() {
        return taxpayerRegistrationNo;
    }

    public void setTaxpayerRegistrationNo(String taxpayerRegistrationNo) {
        this.taxpayerRegistrationNo = taxpayerRegistrationNo == null ? null : taxpayerRegistrationNo.trim();
    }

    public String getApprovalDocNo() {
        return approvalDocNo;
    }

    public void setApprovalDocNo(String approvalDocNo) {
        this.approvalDocNo = approvalDocNo == null ? null : approvalDocNo.trim();
    }

    public String getAdministrativeDivisionCode() {
        return administrativeDivisionCode;
    }

    public void setAdministrativeDivisionCode(String administrativeDivisionCode) {
        this.administrativeDivisionCode = administrativeDivisionCode == null ? null : administrativeDivisionCode.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }


    public Integer getCurPersonNum() {
		return curPersonNum;
	}

	public void setCurPersonNum(Integer curPersonNum) {
		this.curPersonNum = curPersonNum;
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

    public Integer getLackpersonNum() {
		return lackpersonNum;
	}

	public void setLackpersonNum(Integer lackpersonNum) {
		this.lackpersonNum = lackpersonNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	
	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", shortName=" + shortName + ", parentDeptId=" + parentDeptId
				+ ", level=" + level + ", deptNo=" + deptNo + ", industryType=" + industryType + ", registerType="
				+ registerType + ", companyNo=" + companyNo + ", corporate=" + corporate + ", taxpayerRegistrationNo="
				+ taxpayerRegistrationNo + ", approvalDocNo=" + approvalDocNo + ", administrativeDivisionCode="
				+ administrativeDivisionCode + ", companyId=" + companyId + ", personNum=" + personNum
				+ ", curPersonNum=" + curPersonNum + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", remark=" + remark + ", lackpersonNum=" + lackpersonNum + ", userName=" + userName + ", userId="
				+ userId + ", parentDeptName=" + parentDeptName + "]";
	}

	// 重写hashCode方法，把name当作对象的哈希表值返回
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	// 重写equals方法
	@Override
	public boolean equals(Object obj) {
		Department department = (Department) obj;
		return department.name.equals(name);
	}

	
	
	
}