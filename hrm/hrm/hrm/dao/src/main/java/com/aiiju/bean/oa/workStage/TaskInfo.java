package com.aiiju.bean.oa.workStage;

import java.io.Serializable;
import java.util.Date;

public class TaskInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 公司id
	 */
	private Long companyId;
	/**
	 * 员工id
	 */
	private Long userId;
	/**
	 * 任务类型
	 */
	private Integer taskType;
	/**
	 * 任务标题
	 */
	private String title;
	/**
	 * 任务描述
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private String startDate;
	/**
	 * 到期时间
	 */
	private String expireDate;
	
	
	
	/**
	 * 到期时间状态  ：0未到期   1到期   
	 */
	
	private String isExpired;
;
	/**
	 * 任务执行人id（多人逗号分开）
	 */
	private Long sendToPerson;
	private String sendToPersonName;
	private String Persons;
	/**
	 * 任务状态0 待处理，1处理中，2已完成，3已撤回， 4已过期
	 */
	private Integer step;
	/**
	 * 附件参数
	 */
	private String taskArgs;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 修改时间
	 */
	private String updateTime;
	/**
	 * 删除标记
	 */
	private Integer isDel;
	/**
	 * 撤回标记
	 */
	private Integer isWithdraw;
	
	/**
	 * 转发标记
	 */

	private Integer isForward;
	
	// 场景类型  1-日报;2-任务;3-审批(收到的回复会用到这个字段)
	private Integer sceneType;
	//发起人名称
	private String userName;
	//LOGO
	private String logo;
	//人名的最后一个英文字母
	private String lastLetter;
	
	//发起者LOGO
	private String senderLogo;
	//发起者部门名字
	private String deptName;
	//发起者职位名字
	private String positionName;
	
	/**
	 * 回复数
	 */
	private Integer replyNum = 0;//默认为0
	
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	public String getSendToPersonName() {
		return sendToPersonName;
	}
	public void setSendToPersonName(String sendToPersonName) {
		this.sendToPersonName = sendToPersonName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public Long getSendToPerson() {
		return sendToPerson;
	}
	public void setSendToPerson(Long sendToPerson) {
		this.sendToPerson = sendToPerson;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getTaskArgs() {
		return taskArgs;
	}
	public void setTaskArgs(String taskArgs) {
		this.taskArgs = taskArgs;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public Integer getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Integer isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLastLetter() {
		return lastLetter;
	}
	public void setLastLetter(String lastLetter) {
		this.lastLetter = lastLetter;
	}
	public String getPersons() {
		return Persons;
	}
	public void setPersons(String persons) {
		Persons = persons;
	}
	
	
	
	public String getSenderLogo() {
		return senderLogo;
	}
	public void setSenderLogo(String senderLogo) {
		this.senderLogo = senderLogo;
	}
	public Integer getIsForward() {
		return isForward;
	}
	public void setIsForward(Integer isForward) {
		this.isForward = isForward;
	}
	public String getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
	
	
	
	
	
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	@Override
	public String toString() {
		return "TaskInfo [id=" + id + ", companyId=" + companyId + ", userId="
				+ userId + ", taskType=" + taskType + ", title=" + title
				+ ", content=" + content + ", startDate=" + startDate
				+ ", expireDate=" + expireDate + ", sendToPerson="
				+ sendToPerson + ", step=" + step + ", taskArgs=" + taskArgs
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", isDel=" + isDel + ", isWithdraw=" + isWithdraw + ", isExpired=" + isExpired+ 
				", isForward=" + isForward+	"]";
	}
	
	
}
