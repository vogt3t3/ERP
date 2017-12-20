package com.aiiju.bean.oa.duty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PositionTypeInfo implements Serializable {
    /**
     * 类型ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date createDate;

    private Date updateDate;
    /**
     * 类型下的职务列表
     */
    private List subList;

    private static final long serialVersionUID = 1L;

    public List getSubList() {
		return subList;
	}

	public void setSubList(List subList) {
		this.subList = subList;
	}

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        sb.append(", type=").append(type);
        sb.append(", companyId=").append(companyId);
        sb.append(", sort=").append(sort);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}