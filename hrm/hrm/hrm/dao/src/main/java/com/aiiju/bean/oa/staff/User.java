package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;

public class User implements Serializable {
    /**
     * 主键
     */
    private Long id;
    private Byte isAdmin;
    /**
     * 用户id，大系统同步过来的
     */
    private Long loginUserId;

    private FileInfo fileInfo;
    private String url;
    /**
     * 所属组织
     */
    private Long deptId;
    private String deptName;
    /**
     *APP 组织数组（一个人可能在多个部门）
     */
    
   private List<Department> deptList;
    /**
     * 姓名
     */
    private String name;
    
    //姓名拼音
    private String pyName;
    //拼音首字母
    private String firstPYName;
    //最后一个字的拼音首字母  用于背景色
    private String lastLetter;

    /**
     * 证件类型
     */
    private Byte certificateType;
    private String certificateTypeName;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 性别
     */
    private Integer sex;
    private String sexName;

    /**
     * 民族
     */
    private Integer nation;
    private String nationName;

    /**
     * 状态:1在职 2 离职
     */
    private String status;

    /**
     * 职位名称
     */
    private Long positionId;
    private String positionName;

    /**
     * 担任职务
     */
    private Long dutyId;
    private String dutyName;

    /**
     * 职等
     */
    private Long dutyTypeId;
    private String dutyTypeName;

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
    private String regular;

    /**
     * 婚姻状况：1未婚2已婚3离异4丧偶
     */
    private Byte maritalStatus;
    private String marital;

    /**
     * 政治面貌 ：1团员2党员
     */
    private Byte formalFace;
    private String formal;
    /**
     * 是否激活0未激活1已激活
     */
    private Byte isActive;
    private String isActiveName;
    /**
     * 出生日期
     */
    private Date birdthDay;

    /**
     * 学历 1初中2高中及中专3大专4本科5硕士研究生6博士研究生7其他
     */
    private Byte eduationalLevel;
    private String eduational;

    /**
     * 籍贯
     */
    private String myNative;

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
     * 公司Id
     */
    private Long companyId;
    private String companyName;
    /**
     * 注册时的公司Id
     */
    private Long registerCompanyId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;
    /**
     * 人员编制
     */
    private Byte userDraw;
    private String userDrawName;
    /**
     * 是否删除
     */
    private Byte isDel;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 扩展字段1
     */
    private String remark;
    /**
     * 关联的角色
     */
    private String roleIds;

    /**
     * 设置薪资时二级密码
     */
    private String twoLevelPwd;
    
    private Integer startAge;
    private Integer endAge;
    /**
     * 用户的头像
     */
    private String userHeadImg;
    /**
     * 用户的角色
     */
    private String userRoles;
    
    /**
     * 是否是管理员身份: 1=管理员  2=普通用户
     */
    private int userNature;
 
    //APP 新版通讯录需要的字段
    //部门ID(APP使用)
    private Long parentDeptId;
    //部门层次状态码
    private Integer level;
    //区分员工和部门的状态码：1为员工 2为部门
    private Integer isGroup;
    
    private static final long serialVersionUID = 1L;
    
    public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Long getRegisterCompanyId() {
		return registerCompanyId;
	}

	public void setRegisterCompanyId(Long registerCompanyId) {
		this.registerCompanyId = registerCompanyId;
	}

	public String getIsActiveName() {
		return isActiveName;
	}

	public void setIsActiveName(String isActiveName) {
		this.isActiveName = isActiveName;
	}

	public Byte getIsActive() {
		return isActive;
	}

	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegular() {
		return regular;
	}

	public void setRegular(String regular) {
		this.regular = regular;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Byte getUserDraw() {
		return userDraw;
	}

	public void setUserDraw(Byte userDraw) {
		this.userDraw = userDraw;
	}

	public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getUserDrawName() {
		return userDrawName;
	}

	public void setUserDrawName(Byte userDraw) {
		if(userDraw==1){
			userDrawName="正式";
		}else if (userDraw==2) {
			userDrawName="试用";
		}else if (userDraw==3) {
			userDrawName="兼职";
		}else {
			userDrawName="实习";
		}
	}
	public String getTwoLevelPwd() {
		return twoLevelPwd;
	}

	public void setTwoLevelPwd(String twoLevelPwd) {
		this.twoLevelPwd = twoLevelPwd;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCertificateTypeName() {
		return certificateTypeName;
	}

	public void setCertificateTypeName(String certificateTypeName) {
		this.certificateTypeName = certificateTypeName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public String getFormal() {
		return formal;
	}

	public void setFormal(String formal) {
		this.formal = formal;
	}

	public String getEduational() {
		return eduational;
	}

	public void setEduational(String eduational) {
		this.eduational = eduational;
	}

	public Integer getStartAge() {
		return startAge;
	}

	public void setStartAge(Integer startAge) {
		this.startAge = startAge;
	}

	public Integer getEndAge() {
		return endAge;
	}

	public void setEndAge(Integer endAge) {
		this.endAge = endAge;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}
	
	public String getFirstPYName() {
		return firstPYName;
	}

	public void setFirstPYName(String firstPYName) {
		this.firstPYName = firstPYName;
	}

	public String getLastLetter() {
		return lastLetter;
	}

	public void setLastLetter(String lastLetter) {
		this.lastLetter = lastLetter;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getDutyId() {
        return dutyId;
    }

	public Byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setUserDrawName(String userDrawName) {
		this.userDrawName = userDrawName;
	}

	public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getDutyTypeId() {
        return dutyTypeId;
    }

    public void setDutyTypeId(Long dutyTypeId) {
        this.dutyTypeId = dutyTypeId;
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

    public String getMyNative() {
        return myNative;
    }

    public void setMyNative(String myNative) {
        this.myNative = myNative == null ? null : myNative.trim();
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
		this.remark = remark;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}
	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public List<Department> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}
	
	public int getUserNature() {
		return userNature;
	}

	public void setUserNature(int userNature) {
		this.userNature = userNature;
	}
	public Long getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(Long parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	

	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deptId=").append(deptId);
        sb.append(", deptName=").append(deptName);
        sb.append(", name=").append(name);
        sb.append(", pyName=").append(pyName);
        sb.append(", firstPYName=").append(firstPYName);
        sb.append(", certificateTypeName=").append(certificateTypeName);
        sb.append(", certificateType=").append(certificateType);
        sb.append(", certificateNo=").append(certificateNo);
        sb.append(", sex=").append(sex);
        sb.append(", nation=").append(nation);
        sb.append(", status=").append(status);
        sb.append(", positionId=").append(positionId);
        sb.append(", positionName=").append(positionName);
        sb.append(", dutyId=").append(dutyId);
        sb.append(", dutyName=").append(dutyName);
        sb.append(", dutyTypeId=").append(dutyTypeId);
        sb.append(", dutyTypeName=").append(dutyTypeName);
        sb.append(", phone=").append(phone);
        sb.append(", joinDate=").append(joinDate);
        sb.append(", regularDate=").append(regularDate);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", marital=").append(marital);
        sb.append(", formalFace=").append(formalFace);
        sb.append(", formal=").append(formal);
        sb.append(", birdthDay=").append(birdthDay);
        sb.append(", eduationalLevel=").append(eduationalLevel);
        sb.append(", eduational=").append(eduational);
        sb.append(", myNative=").append(myNative);
        sb.append(", workDate=").append(workDate);
        sb.append(", userNo=").append(userNo);
        sb.append(", age=").append(age);
        sb.append(", curConpanyAge=").append(curConpanyAge);
        sb.append(", companyAge=").append(companyAge);
        sb.append(", birdthAddress=").append(birdthAddress);
        sb.append(", homeAddress=").append(homeAddress);
        sb.append(", companyId=").append(companyId);
        sb.append(", email=").append(email);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", loginUserId=").append(loginUserId);
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
		User user = (User) obj;
		return user.phone.equals(phone);
	}
}