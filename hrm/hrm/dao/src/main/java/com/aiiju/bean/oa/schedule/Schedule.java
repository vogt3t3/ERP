package com.aiiju.bean.oa.schedule;

import java.io.Serializable;

/**
 * 
 * @ClassName: Schedule 
 * @Description: 待办事项实体类
 * @author 哪吒 
 * @date 2016年12月3日 上午11:36:41 
 *
 */

public class Schedule implements Serializable {
	
	// 主键
	private Long id;
	// 公司ID
	private Long companyId;
	// 内容
	private String content;
	// 创建时间
	private String createDate;
	// 用户ID
	private Long userId;
	// 指定时间（年年年年-月月-日日 格式）
	private String timing;
	
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
	
	
}
