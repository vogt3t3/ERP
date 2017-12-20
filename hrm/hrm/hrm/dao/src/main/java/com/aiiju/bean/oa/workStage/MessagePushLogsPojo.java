package com.aiiju.bean.oa.workStage;

import java.io.Serializable;


/**
 * 公告和考勤消息推送记录
 * @author qiqi
 *
 */
public class MessagePushLogsPojo implements Serializable{
	
	private static final long serialVersionUID = -2122573962897379767L;
	//接收的用户Id
	private Long userId;
	//消息Title
	private String title;
	//消息类型
	private Integer type;
	//创建时间Date型
	private String createTime;
	//关联的具体信息Id
	private Long relateId;
	//内容
	private String content;
	//公司ID
	private Long companyId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getRelateId() {
		return relateId;
	}
	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
}
