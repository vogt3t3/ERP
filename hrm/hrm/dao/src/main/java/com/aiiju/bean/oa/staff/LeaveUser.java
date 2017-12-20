package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 离职员工
 * @author 维斯
 * 
 * 2017年2月8日 	上午11:16:32
 */
public class LeaveUser  implements Serializable {
	
	private Long id;
    /**
     * 员工id
     */
    private Long userId;

    /**
     * 所属组织
     */
    private String deptId;

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
     * 职位名称
     */
    private String positionId;

    /**
     * 担任职务
     */
    private String dutyId;

    /**
     * 职等
     */
    private String dutyTypeId;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 入职日期
     */
    private Date joinDate;

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
     * 出生日期
     */
    private Date birdthDay;

    /**
     * 学历
     */
    private Byte eduationalLevel;

    /**
     * 籍贯
     */
    private Integer myNative;

    /**
     * 参加工作日期
     */
    private Date workDate;

    /**
     * 员工编码
     */
    private String userNo;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 司龄
     */
    private Integer curConpanyAge;

    /**
     * 工龄
     */
    private Integer companyAge;

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
    
    /**
     * 公司id
     */
    private Long companyId;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date updateDate;

    /**
     * 描述
     */
    private String remark;

    /**
     * 昵称
     */
    private String nick;

    private static final long serialVersionUID = 1L;

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

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId==null?null:deptId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
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
        this.nation = nation == null ? null : nation.trim();
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId== null ? null : positionId.trim();
    }

    public String getDutyId() {
        return dutyId;
    }

    public void setDutyId(String dutyId) {
        this.dutyId = dutyId==null?null:dutyId.trim();
    }

    public String getDutyTypeId() {
        return dutyTypeId;
    }

    public void setDutyTypeId(String dutyTypeId) {
        this.dutyTypeId = dutyTypeId==null?null:dutyTypeId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
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

    public Date getBirdthDay() {
        return birdthDay;
    }

    public void setBirdthDay(Date birdthDay) {
        this.birdthDay = birdthDay;
    }

    public Byte getEduationalLevel() {
        return eduationalLevel;
    }

    public void setEduationalLevel(Byte eduationalLevel) {
        this.eduationalLevel = eduationalLevel;
    }

    public Integer getMyNative() {
        return myNative;
    }

    public void setMyNative(Integer myNative) {
        this.myNative = myNative;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCurConpanyAge() {
        return curConpanyAge;
    }

    public void setCurConpanyAge(Integer curConpanyAge) {
        this.curConpanyAge = curConpanyAge;
    }

    public Integer getCompanyAge() {
        return companyAge;
    }

    public void setCompanyAge(Integer companyAge) {
        this.companyAge = companyAge;
    }

    public String getBirdthAddress() {
        return birdthAddress;
    }

    public void setBirdthAddress(String birdthAddress) {
        this.birdthAddress = birdthAddress == null ? null : birdthAddress.trim();
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress == null ? null : homeAddress.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", deptId=").append(deptId);
        sb.append(", name=").append(name);
        sb.append(", certificateType=").append(certificateType);
        sb.append(", certificateNo=").append(certificateNo);
        sb.append(", sex=").append(sex);
        sb.append(", nation=").append(nation);
        sb.append(", positionId=").append(positionId);
        sb.append(", dutyId=").append(dutyId);
        sb.append(", dutyTypeId=").append(dutyTypeId);
        sb.append(", phone=").append(phone);
        sb.append(", joinDate=").append(joinDate);
        sb.append(", regularDate=").append(regularDate);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", formalFace=").append(formalFace);
        sb.append(", birdthDay=").append(birdthDay);
        sb.append(", eduationalLevel=").append(eduationalLevel);
        sb.append(", myNative=").append(myNative);
        sb.append(", workDate=").append(workDate);
        sb.append(", userNo=").append(userNo);
        sb.append(", age=").append(age);
        sb.append(", curConpanyAge=").append(curConpanyAge);
        sb.append(", companyAge=").append(companyAge);
        sb.append(", birdthAddress=").append(birdthAddress);
        sb.append(", homeAddress=").append(homeAddress);
        sb.append(", email=").append(email);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append(", nick=").append(nick);
        sb.append(", companyId=").append(companyId);
        sb.append("]");
        return sb.toString();
    }
}