package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员导出模板类
 * 
 * @author 维斯
 *	2016年11月1日	 上午11:13:20
 */
public class UserXLS implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * 姓名
     */
    private String name;

    /**
     * 证件类型
     */
    private Byte certificateType;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 状态
     */
    private String status;

    /**
     * 职位名称
     */
    private Long positionId;
    
    /**
     * 联系方式
     */
    private String phone;
    
    /**
     * 拟转正日期
     */
    private Date regularDate;

    /**
     * 婚姻状况
     */
    private Byte maritalStatus;

    /**
     * 政治面貌
     */
    private Byte formalFace;

    /**
     * 员工编码
     */
    private String userNo;
    
    private Long companyId;
    /**
     * 出生地
     */
    private String birdthAddress;

    /**
     * 家庭住址
     */
    private String homeAddress;
    
    /**
     * 邮箱
     */
    private String email;

    
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(Byte certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegularDate() {
		return regularDate;
	}

	public void setRegularDate(Date regularDate) {
		this.regularDate = regularDate;
	}

	public Byte getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Byte maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Byte getFormalFace() {
		return formalFace;
	}

	public void setFormalFace(Byte formalFace) {
		this.formalFace = formalFace;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getBirdthAddress() {
		return birdthAddress;
	}

	public void setBirdthAddress(String birdthAddress) {
		this.birdthAddress = birdthAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserXLS [name=" + name + ", certificateType=" + certificateType
				+ ", certificateNo=" + certificateNo + ", sex=" + sex
				+ ", nation=" + nation + ", status=" + status + ", positionId="
				+ positionId + ", phone=" + phone + ", regularDate="
				+ regularDate + ", maritalStatus=" + maritalStatus
				+ ", formalFace=" + formalFace + ", userNo=" + userNo
				+ ", companyId=" + companyId + ", birdthAddress="
				+ birdthAddress + ", homeAddress=" + homeAddress + ", email="
				+ email + "]";
	}


	
    
    
    
    

}
