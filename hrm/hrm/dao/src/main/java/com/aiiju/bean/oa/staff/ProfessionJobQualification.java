package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 职业资格
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:24:28
 */
public class ProfessionJobQualification implements Serializable {
    /**
     * 职业资格Id
     */
    private Long id;

    /**
     * 获得日期
     */
    private Date gainDate;

    /**
     * 注册单位
     */
    private String registedUnit;

    /**
     * 颁发单位
     */
    private String issuedUnit;

    /**
     * 证书名称
     */
    private String certificateName;

    /**
     * 证书编号
     */
    private String certificateNo;

    /**
     * 用户ID
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

    /**
     * 描述说明
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGainDate() {
        return gainDate;
    }

    public void setGainDate(Date gainDate) {
        this.gainDate = gainDate;
    }

    public String getRegistedUnit() {
        return registedUnit;
    }

    public void setRegistedUnit(String registedUnit) {
        this.registedUnit = registedUnit == null ? null : registedUnit.trim();
    }

    public String getIssuedUnit() {
        return issuedUnit;
    }

    public void setIssuedUnit(String issuedUnit) {
        this.issuedUnit = issuedUnit == null ? null : issuedUnit.trim();
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
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
        sb.append(", gainDate=").append(gainDate);
        sb.append(", registedUnit=").append(registedUnit);
        sb.append(", issuedUnit=").append(issuedUnit);
        sb.append(", certificateName=").append(certificateName);
        sb.append(", certificateNo=").append(certificateNo);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}