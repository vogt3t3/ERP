package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 教育经历
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:21:54
 */
public class EducationLog implements Serializable {
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
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String profession;

    /**
     * 学历
     */
    private Byte educationLevel;
    private String education;

    /**
     * 学位
     */
    private String degree;

    /**
     * 教育类型
     */
    private Byte educationType;
    private String educationTypeName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    
    public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducationTypeName() {
		return educationTypeName;
	}

	public void setEducationTypeName(String educationType) {
		if("1".equals(educationType)){
			educationTypeName="全日制";
		}else {
			educationTypeName="在职教育";
		}
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public Byte getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Byte educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Byte getEducationType() {
        return educationType;
    }

    public void setEducationType(Byte educationType) {
        this.educationType = educationType;
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
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", school=").append(school);
        sb.append(", profession=").append(profession);
        sb.append(", educationLevel=").append(educationLevel);
        sb.append(", education=").append(education);
        sb.append(", degree=").append(degree);
        sb.append(", educationType=").append(educationType);
        sb.append(", educationTypeName=").append(educationTypeName);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}