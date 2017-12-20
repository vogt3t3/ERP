package com.aiiju.bean.oa.workStage;

import java.io.Serializable;

/**
 * 
 * @ClassName: HistoryLog 
 * @Description: 消息扩散表实体类
 * @author 哪吒 
 * @date 2016年12月28日 下午6:04:36 
 *
 */

public class HistoryLog implements Serializable {

	private static final long serialVersionUID = 3458894760481636878L;
	private Long id;
	// 公司ID
	private Long companyId;
	// 场景类型 1-日报;2-任务;3-审批
	private Integer sceneType;
	// 对象ID
	private Long refId;
	private String userName;
	// 接收用户ID
	private Long receiveUid;
	// 接收用户ID(大系统的loginUserID APP需要)
	private Long receiveLoginUserID;
	// 创建时间(按时间戳更新)
	private String createDate;
	// 删除标记
	private Integer isDel;
	// 是否已读
	private Integer isRead;
	// 是否已转发  0:未转发 1:已转发  2:已完成
	private Integer isForward;
	/**
	 * 统计总数
	 */
	private Integer num;
	private String forward;
	
	
	//接收人LOGO
	private String receiveLogo;
	
	//任务的进行状态 (状态值 0待处理 1处理中 2已完成 3已拒绝 4已转发 5已撤回 6已过期 7发起者)
	private Integer logStatus ;
	
	public HistoryLog(Long receiveUid,Long companyId,Long refId,Integer sceneType){
		this.receiveUid=receiveUid;
		this.companyId=companyId;
		this.refId=refId;
		this.sceneType=sceneType;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public HistoryLog(){};
	
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
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public Long getReceiveUid() {
		return receiveUid;
	}
	public void setReceiveUid(Long receiveUid) {
		this.receiveUid = receiveUid;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public Integer getIsForward() {
		return isForward;
	}
	public void setIsForward(Integer isForward) {
		this.isForward = isForward;
	}


	public String getReceiveLogo() {
		return receiveLogo;
	}


	public void setReceiveLogo(String receiveLogo) {
		this.receiveLogo = receiveLogo;
	}


	public Integer getLogStatus() {
		return logStatus;
	}


	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}


	public Long getReceiveLoginUserID() {
		return receiveLoginUserID;
	}


	public void setReceiveLoginUserID(Long receiveLoginUserID) {
		this.receiveLoginUserID = receiveLoginUserID;
	} 
	
	
	
}
