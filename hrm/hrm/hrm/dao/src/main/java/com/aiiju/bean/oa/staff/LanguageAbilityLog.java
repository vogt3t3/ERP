package com.aiiju.bean.oa.staff;

import java.io.Serializable;
import java.util.Date;
/**
 * 语言能力
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:22:34
 */
public class LanguageAbilityLog implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 语言
     */
    private String language;

    /**
     * 等级
     */
    private String level;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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
        sb.append(", language=").append(language);
        sb.append(", level=").append(level);
        sb.append(", userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}