package com.aiiju.bean.oa.workStage;

import java.io.Serializable;

/**
 * 
 * @ClassName: ReplyLog 
 * @Description: 回复日志类
 * @author 哪吒 
 * @date 2016年12月28日 下午3:09:57 
 *
 */

public class ReplyLog implements Serializable {

	private static final long serialVersionUID = -2845516450725007540L;
	
	private Long id;
	// 公司ID
	private Long companyId;
	// 场景类型 1-日报;2-任务;3-审批
	private Integer sceneType;
	// 回复ID
	private Long replyId;
	// 接收用户ID
	private Long receiveUid;
	// 创建时间
	private String createTime;
	// 删除标记
	private Integer isDel;
	// 是否已读
	private Integer isRead;
	// 是否撤回
	private Integer isWithdraw;
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
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public Long getReceiveUid() {
		return receiveUid;
	}
	public void setReceiveUid(Long receiveUid) {
		this.receiveUid = receiveUid;
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
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Integer isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
}
