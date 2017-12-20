package com.aiiju.bean.oa.salary;

import java.io.Serializable;
/**
 * 发送工资单勾选记录 POJO
 * @author BZ
 *
 */
public class SpwSalaryTemp implements Serializable {
	
	private static final long serialVersionUID = 6786180119407218063L;
	//主键
    private Long id;
    //批次ID
    private Long pid;
    //字段key
    private String originFeild;
    //字段value
    private String feildName;
    //排序
    private Integer feildSort;
    //公司ID
    private Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getOriginFeild() {
		return originFeild;
	}
	public void setOriginFeild(String originFeild) {
		this.originFeild = originFeild;
	}
	public String getFeildName() {
		return feildName;
	}
	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}
	public Integer getFeildSort() {
		return feildSort;
	}
	public void setFeildSort(Integer feildSort) {
		this.feildSort = feildSort;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "SpwSalaryTemp [id=" + id + ", pid=" + pid + ", originFeild=" + originFeild + ", feildName=" + feildName
				+ ", feildSort=" + feildSort + ", companyId=" + companyId + "]";
	}
}