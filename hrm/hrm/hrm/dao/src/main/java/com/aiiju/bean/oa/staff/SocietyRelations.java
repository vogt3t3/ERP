package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 社会关系
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:25:22
 */
public class SocietyRelations implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 关系人名称
     */
    private String name;

    /**
     * 与本人关系
     */
    private String relation;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 工作单位
     */
    private String companyName;

    /**
     * 职务
     */
    private String dutyName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }



    public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName == null ? null : dutyName.trim();
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
        sb.append(", name=").append(name);
        sb.append(", relation=").append(relation);
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", companyName=").append(companyName);
        sb.append(", dutyName=").append(dutyName);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}