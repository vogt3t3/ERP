package com.aiiju.bean.oa.workStage;

import java.io.Serializable;

/**
 * 
 * @ClassName: Reply 
 * @Description: 回复实体类
 * @author 哪吒 
 * @date 2016年12月28日 下午3:09:36 
 *
 */

public class Reply implements Serializable {

	private static final long serialVersionUID = -2099816600473101035L;
	
	private Long id;
	// 公司ID
	private Long companyId;
	// 场景类型  1-日报;2-任务;3-审批
	private Integer sceneType;
	// 用户ID
	private Long userId;
	// 用户姓名
	private String userName;
	// 消息回复的ID
	private Long replyPid;
	// 对象ID（日报/任务/审批ID）
	private Long pid;
	// 对象发布者ID
	private Long pidUid;
	// 被回复者ID
	private Long receiveUid;
	// 被回复者姓名
	private String receiveName;
	// 内容
	private String replyCon;
	// 创建时间
	private String createTime;
	// 删除标记
	private Integer isDel;
	// 同意标记 1-同意;2-拒绝
	private Integer isAgree;
	//业务
	private String agreeText;
	// 是否撤回
	private Integer isWithdraw;
	// 用户头像
	private String logo;
	// 用户姓名简拼
	private String lastLetter;
	// 被回复内容
	private String receiveContent;
	// 是否已读
	private Integer isRead;
	/**
	 * 带有参数的构造方法
	 * @param companyId
	 * @param sceneType
	 * @param userId
	 * @param replyPid
	 * @param pid
	 * @param receiveUid
	 * @param replyCon
	 * @param isAgree
	 * @param pidUid
	 */
	public Reply(Long companyId,Integer sceneType,Long userId,Long pid,Long receiveUid,String replyCon,Integer isAgree,Long pidUid){
		this.companyId=companyId;
		this.sceneType=sceneType;
		this.userId=userId;
		this.pid=pid;
		this.receiveUid=receiveUid;
		this.isAgree=isAgree;
		this.replyCon=replyCon;
		if(isAgree.intValue()==1){
			this.replyCon="同意";
		}else if(isAgree.intValue()==2){
			this.replyCon="不同意";
		}
		this.pidUid=pidUid;
	}
	public Reply(){
		
	}
	public Reply(Integer isAgree,String receiveName,String agreeText,String createTime){
		this.isAgree=isAgree;
		this.receiveName=receiveName;
		this.agreeText=agreeText;
		this.createTime=createTime;
	}
	

	public String getAgreeText() {
		return agreeText;
	}
	public void setAgreeText(String agreeText) {
		this.agreeText = agreeText;
	}
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getReplyPid() {
		return replyPid;
	}
	public void setReplyPid(Long replyPid) {
		this.replyPid = replyPid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getPidUid() {
		return pidUid;
	}
	public void setPidUid(Long pidUid) {
		this.pidUid = pidUid;
	}
	public Long getReceiveUid() {
		return receiveUid;
	}
	public void setReceiveUid(Long receiveUid) {
		this.receiveUid = receiveUid;
	}
	public String getReplyCon() {
		return replyCon;
	}
	public void setReplyCon(String replyCon) {
		this.replyCon = replyCon;
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
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
		if(isAgree.intValue()==1){
			setAgreeText("已同意");
		}else if(isAgree.intValue()==2){
			setAgreeText("已拒绝");
		}else if(isAgree.intValue()==3){
			setAgreeText("撤回");
		}else{
			setAgreeText("待审核");
		}
	}
	public Integer getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Integer isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
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
	public String getReceiveContent() {
		return receiveContent;
	}
	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
}
