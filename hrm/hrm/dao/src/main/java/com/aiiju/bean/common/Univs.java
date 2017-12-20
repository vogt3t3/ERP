package com.aiiju.bean.common;

import java.io.Serializable;
/**
 * 学校（专科 本科 职高）
 * 
 * @author 维斯
 *	2016年10月25日	 下午4:20:01
 */
public class Univs implements Serializable {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 学校名称
	 */
    private String name;
    /**
	 * 省份id
	 */
    private Integer pid;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", pid=").append(pid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}