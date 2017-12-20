package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 专业技术资格
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:24:52
 */
public class ProfessionSkillQualification implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 资格名称
     */
    private String qualificationName;

    /**
     * 资格等级
     */
    private String qualificationLevel;

    /**
     * 资格编号
     */
    private String qualificationNo;

    /**
     * 获得日期
     */
    private Date gainDate;

    /**
     * 注册单位
     */
    private String registerCompany;

    /**
     * 用户Id
     */
    private Long userId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName == null ? null : qualificationName.trim();
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel == null ? null : qualificationLevel.trim();
    }

    public String getQualificationNo() {
        return qualificationNo;
    }

    public void setQualificationNo(String qualificationNo) {
        this.qualificationNo = qualificationNo == null ? null : qualificationNo.trim();
    }

    public Date getGainDate() {
        return gainDate;
    }

    public void setGainDate(Date gainDate) {
        this.gainDate = gainDate;
    }

    public String getRegisterCompany() {
        return registerCompany;
    }

    public void setRegisterCompany(String registerCompany) {
        this.registerCompany = registerCompany == null ? null : registerCompany.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        sb.append(", qualificationName=").append(qualificationName);
        sb.append(", qualificationLevel=").append(qualificationLevel);
        sb.append(", qualificationNo=").append(qualificationNo);
        sb.append(", gainDate=").append(gainDate);
        sb.append(", registerCompany=").append(registerCompany);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}