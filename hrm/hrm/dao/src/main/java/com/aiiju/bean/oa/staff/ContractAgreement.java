package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 合同
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:21:45
 */
public class ContractAgreement implements Serializable {
    /**
     * 合同Id
     */
    private Long id;

    /**
     * 签订日期
     */
    private Date signDate;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 签订类型
     */
    private Byte signType;
    private String signTypeName;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 拟转正日期
     */
    private Date planRegularDate;

    /**
     * 承诺服务期限
     */
    private Integer planServiceTimes;

    /**
     * 合同类型
     */
    private Byte contractType;
    private String contractTypeName;

    /**
     * 是否生效
     */
    private Byte isEffect;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 期限类型
     */
    private Byte limitType;
    private String limitTypeName;

    /**
     * 实际转正日期
     */
    private Date realRegularDate;

    /**
     * 违约金
     */
    private Double damageRayment;

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

    /**
     * 描述说明
     */
    private String remark;

    private static final long serialVersionUID = 1L;



	public String getSignTypeName() {
		return signTypeName;
	}



	public void setSignTypeName(String signType) {
		if("1".equals(signType)){
    		signTypeName="初签";
    	}else {
    		signTypeName="续签";
		}
	}



	public String getLimitTypeName() {
		return limitTypeName;
	}



	public void setLimitTypeName(String limitType) {
		if("1".equals(limitType)){
			limitTypeName="有固定期限";
		}else if ("2".equals(limitType)) {
			limitTypeName="无固定期限";
		}else if("3".equals(limitType)){
			limitTypeName="以完成任务期限";
		}
	}



	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}



	public String getContractTypeName() {
		return contractTypeName;
	}

	

	public void setContractTypeName(Byte contractType) {
		this.contractType=contractType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Byte getSignType() {
        return signType;
    }

    public void setSignType(Byte signType) {
        this.signType = signType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPlanRegularDate() {
        return planRegularDate;
    }

    public void setPlanRegularDate(Date planRegularDate) {
        this.planRegularDate = planRegularDate;
    }

    public Integer getPlanServiceTimes() {
        return planServiceTimes;
    }

    public void setPlanServiceTimes(Integer planServiceTimes) {
        this.planServiceTimes = planServiceTimes;
    }

    public Byte getContractType() {
        return contractType;
    }

    public void setContractType(Byte contractType) {
        this.contractType = contractType;
    }

    public Byte getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Byte isEffect) {
        this.isEffect = isEffect;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Byte getLimitType() {
        return limitType;
    }

    public void setLimitType(Byte limitType) {
        this.limitType = limitType;
    }

    public Date getRealRegularDate() {
        return realRegularDate;
    }

    public void setRealRegularDate(Date realRegularDate) {
        this.realRegularDate = realRegularDate;
    }

    public Double getDamageRayment() {
        return damageRayment;
    }

    public void setDamageRayment(Double damageRayment) {
        this.damageRayment = damageRayment;
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
        sb.append(", signDate=").append(signDate);
        sb.append(", contractNo=").append(contractNo);
        sb.append(", signType=").append(signType);
        sb.append(", signTypeName=").append(signTypeName);
        sb.append(", endDate=").append(endDate);
        sb.append(", planRegularDate=").append(planRegularDate);
        sb.append(", planServiceTimes=").append(planServiceTimes);
        sb.append(", contractType=").append(contractType);
        sb.append(", contractTypeName=").append(contractTypeName);
        sb.append(", isEffect=").append(isEffect);
        sb.append(", startDate=").append(startDate);
        sb.append(", limitType=").append(limitType);
        sb.append(", limitTypeName=").append(limitTypeName);
        sb.append(", realRegularDate=").append(realRegularDate);
        sb.append(", damageRayment=").append(damageRayment);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}