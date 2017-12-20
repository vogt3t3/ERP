package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 邀请员工
 * @author 维斯
 * 
 * 2017年2月8日 	上午10:58:32
 */
public class InviteUser implements Serializable {
	private Long id;
    /**
     * 所属组织
     */
    private Long deptId;
    private String deptName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邀请状况
     */
    private Byte inviteStatus;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 公司id
     */
    private Long companyId;
    private String companyName;
    
    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Byte inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deptId=").append(deptId);
        sb.append(", deptName=").append(deptName);
        sb.append(", name=").append(name);
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", inviteStatus=").append(inviteStatus);
        sb.append(", userId=").append(userId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", companyId=").append(companyId);
        sb.append("]");
        return sb.toString();
    }
}