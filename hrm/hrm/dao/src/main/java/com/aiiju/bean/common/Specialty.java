package com.aiiju.bean.common;

import java.io.Serializable;
/**
 * 学校专业
 * 
 * @author 维斯
 *	2016年10月25日	 下午4:20:14
 */
public class Specialty implements Serializable {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 专业名称
	 */
    private String name;
    /**
	 * 学校id
	 */
    private Integer uid;
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", uid=").append(uid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}