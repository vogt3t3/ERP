package com.aiiju.bean.oa.salary;

import java.io.Serializable;

public class SpwSalaryBatch implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 用户Id
     */
    private Long userId;
    
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 否是撤回
     */
    private Byte isWithdraw;

    /**
     * 否是发送
     */
    private Byte isSend;

    /**
     * 文件名
     */
    private String salaryName;

    private Long companyId;
    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    
    public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Byte getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(Byte isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public Byte getIsSend() {
        return isSend;
    }

    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    public String getSalaryName() {
        return salaryName;
    }

    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName == null ? null : salaryName.trim();
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", userId=").append(userId);
        sb.append(", isWithdraw=").append(isWithdraw);
        sb.append(", isSend=").append(isSend);
        sb.append(", salaryName=").append(salaryName);
        sb.append(", createTime=").append(createTime);
        sb.append(", companyId=").append(companyId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}