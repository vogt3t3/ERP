package com.aiiju.bean.common;

import java.io.Serializable;
/**
 * 省市县（区）
 * 
 * @author 维斯
 *	2016年10月25日	 下午4:20:24
 */
public class City implements Serializable {
	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 行政代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     * 城市等级
     */
    private Integer level;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", firstLetter=").append(firstLetter);
        sb.append(", level=").append(level);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}