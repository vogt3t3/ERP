package com.aiiju.bean.oa.workStage;

import java.io.Serializable;

public class Msg implements Serializable {

	private static final long serialVersionUID = -5895809076692318945L;

	private Long id;
	// 公司ID
	private Long companyId;
	// 用户ID
	private Long userId;
	private String  userName;
	// 场景及类型对应的ID
	private Integer sceneId;
	// 场景类型ID 1-日报;2-任务;3-审批
	private Integer scenType;
	// 子类型
	private Integer subType;
	// 消息来源ID
	private Integer authorId;
	// 内容
	private String content;
	// 是否已读
	private Integer isRead;
	// 创建时间
	private String createTime;
	// 是否删除
	private Integer isDel;
	//
	private Integer isDeal;
	
	//发起者大系统ID
	private Integer authorloginUserId;
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
	public Integer getSceneId() {
		return sceneId;
	}
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	public Integer getScenType() {
		return scenType;
	}
	public void setScenType(Integer scenType) {
		this.scenType = scenType;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public Integer getAuthorloginUserId() {
		return authorloginUserId;
	}
	public void setAuthorloginUserId(Integer authorloginUserId) {
		this.authorloginUserId = authorloginUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
