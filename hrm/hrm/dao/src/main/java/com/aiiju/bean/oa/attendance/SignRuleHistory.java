package com.aiiju.bean.oa.attendance;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤规则主表的历史记录表
 * @author 小辉
 *
 */
public class SignRuleHistory implements Serializable {
    private Integer id;

    private Integer companyId;

    private Integer ruleId;

    private String name;

    private Byte ruleType;

    private Byte isLegalHolidays;

    private String positionLon;

    private String positionLat;

    private Long positionOffset;

    private Date forceTime;

    private String companyAddress;

    private Long creatorId;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getRuleType() {
        return ruleType;
    }

    public void setRuleType(Byte ruleType) {
        this.ruleType = ruleType;
    }

    public Byte getIsLegalHolidays() {
        return isLegalHolidays;
    }

    public void setIsLegalHolidays(Byte isLegalHolidays) {
        this.isLegalHolidays = isLegalHolidays;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon == null ? null : positionLon.trim();
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat == null ? null : positionLat.trim();
    }

    public Long getPositionOffset() {
        return positionOffset;
    }

    public void setPositionOffset(Long positionOffset) {
        this.positionOffset = positionOffset;
    }

    public Date getForceTime() {
        return forceTime;
    }

    public void setForceTime(Date forceTime) {
        this.forceTime = forceTime;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}