package com.aiiju.bean.oa.workStage;

import java.io.Serializable;
import java.util.Map;

import com.aiiju.bean.oa.staff.User;

/**
 * 
 * @ClassName: ReceivedReply 
 * @Description: 收到的回复详情类
 * @author 琦玉 
 * @date 2017年4月22日  
 *
 */

public class ReceivedReply implements Serializable {

	private static final long serialVersionUID = -2099816600473101035L;
	
	//回复记录的ID
	private Long id;
	// 场景类型  1-工作汇报;2-任务;3-审批
	private Integer sceneType;
	//回复的类型（如：对日报回复、对月报回复、对回复回复等，用字符串表示） ；
	private String infoType;
	//情景ID
	private Integer sceneId;
	
	//情景的信息 （当是回复的回复，就不需要情景的信息，只需要情景ID，用于情景详情）
	private Msg sceneInfo;
	//被回复的信息
	private Reply beReplyInfo;
	//回复者的信息
	private User userInfo;
	
	
	//回复内容
	private String replyCon;

	// 同意标记 1-同意;2-拒绝（现在未涉及到 默认为0）
	private Integer isAgree =0;
	//情景状态的回复内容（比如：我完成了任务，就会自动发送给参与者对情景完成的回复）
	private String agreeText;
	// 是否已读
	private Integer isRead;
	// 回复时间
	private String createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Integer getSceneId() {
		return sceneId;
	}
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	public Msg getSceneInfo() {
		return sceneInfo;
	}
	public void setSceneInfo(Msg sceneInfo) {
		this.sceneInfo = sceneInfo;
	}
	public Reply getBeReplyInfo() {
		return beReplyInfo;
	}
	public void setBeReplyInfo(Reply beReplyInfo) {
		this.beReplyInfo = beReplyInfo;
	}
	public User getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
	public String getReplyCon() {
		return replyCon;
	}
	public void setReplyCon(String replyCon) {
		this.replyCon = replyCon;
	}
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	public String getAgreeText() {
		return agreeText;
	}
	public void setAgreeText(String agreeText) {
		this.agreeText = agreeText;
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
	
	
	
	
	
	
	
	
	
	
	
}
