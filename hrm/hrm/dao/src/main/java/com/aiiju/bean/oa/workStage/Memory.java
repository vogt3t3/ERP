package com.aiiju.bean.oa.workStage;

import java.io.Serializable;

/**
 * 
 * @ClassName: Memory 
 * @Description: 用户最近行为数据（目前是最近收件人）实体类
 * @author 哪吒 
 * @date 2016年12月28日 下午6:04:36 
 *
 */

public class Memory implements Serializable {

	private static final long serialVersionUID = -5501719277030609035L;
	
	private Long id;
	// 公司ID
	private Long companyId;
	// 用户ID
	private Long userId;
	// 业务场景 1-工作汇报；2-任务；3-审批
	private Integer sceneType;
	// 最近接收部门
	private String sendToDept;
	// 最近接收人
	private String sendToPerson;
	// 最近接收人--名字拼接
	private String personNames;
	// 最近接收部门--名字拼接
	private String deptNames;
	// 状态 1-最新；0-其他情况
	private Integer status;
	// 创建时间
	private String updateTime;
	// 删除标志
	private Integer isDel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public String getSendToDept() {
		return sendToDept;
	}
	public void setSendToDept(String sendToDept) {
		this.sendToDept = sendToDept;
	}
	public String getSendToPerson() {
		return sendToPerson;
	}
	public void setSendToPerson(String sendToPerson) {
		this.sendToPerson = sendToPerson;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getPersonNames() {
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
}
