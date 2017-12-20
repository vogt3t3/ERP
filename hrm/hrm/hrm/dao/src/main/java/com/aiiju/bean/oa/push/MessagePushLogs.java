package com.aiiju.bean.oa.push;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送记录
 * @author qiqi
 *
 */
public class MessagePushLogs implements Serializable{
	
	private static final long serialVersionUID = -2122573962897379767L;
	//接收的用户Id
	private Integer userId;
	//消息Title
	private String title;
	//消息类型
	private Integer type;
	//创建时间Date型
	private String createTime;
	//关联的具体信息Id
	private Integer relateId;
	//内容
	private String content;
	//公司ID
	private Integer companyId;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
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
	public Integer getRelateId() {
		return relateId;
	}
	public void setRelateId(Integer relateId) {
		this.relateId = relateId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}
