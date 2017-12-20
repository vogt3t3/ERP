package com.aiiju.bean.oa.attendance;

import java.io.Serializable;
import java.util.Date;

public class AttendanceRule implements Serializable {
    private Integer id;

    private Integer companyId;

    private String name;

    private Byte ruleType;

    private Byte isLegalHolidays;

    private String positionLon;

    private String positionLat;

    private Integer positionOffset;

    private Byte isUsed;

    private Date forceTime;

    private String companyAddress;

    private Byte isDel;

    private Long creatorId;

    private Date createDate;

    private Date updateDate;

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

    public Integer getPositionOffset() {
        return positionOffset;
    }

    public void setPositionOffset(Integer positionOffset) {
        this.positionOffset = positionOffset;
    }

    public Byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Byte isUsed) {
        this.isUsed = isUsed;
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

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}