package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
/**
 * 工作台草稿-实体类
 * @author BZ
 *
 */
public class Draft implements Serializable{
	
	private static final long serialVersionUID = -3536836537299159734L;
	//草稿ID
	private Long id;
	//公司ID
	private Long companyId;
	//发起人ID
	private Long userId;
	//场景类型：1.工作汇报2.审批3.作务
	private int sceneType;
	//子类型 scene_type为1时-1:日报2:周报3:月报
	private int subType;
	//接收部门ids
	private String sendToDept;
	//接收人ids
	private String sendToPerson;
	// 最近接收人--名字拼接
	private String persons;
	// 最近接收部门--名字拼接
	private String depts;
	//发送内容
	private Object content;
	//创建时间
	private String createTime;
	
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
	public int getSceneType() {
		return sceneType;
	}
	public void setSceneType(int sceneType) {
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
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public String getDepts() {
		return depts;
	}
	public void setDepts(String depts) {
		this.depts = depts;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}


