package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.staff.User;
/**
 * 审核流程实体
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public class FlowInfo implements Serializable{

	/**
	 * 反序列化ID
	 */
	private static final long serialVersionUID = 4233886189487342431L;
	/**
	 * 回复数
	 */
	private int replyNum;
	/**
	 * userId相关的用户信息
	 */
	private User user;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 公司Id
	 */
	private Long companyId;
	/**
	 * 员工Id
	 */
	private Long userId;
	/**
	 * 审批内容
	 */
	private String content;
	/**
	 * 审批发送人信息id,
	 */
	private String sendToPerson;
	/**
	 * 审批人信息
	 */
	private String persons;
	/**
	 * 审批人列表
	 */
	private List<User> sendToPersonList;
	/**
	 * 审批步骤 step: 0 待处理，1处理中，2已同意，3已拒绝
	 */
	private Byte step;
	/**
	 * 审批步骤 step: 0 待处理，1处理中，2已同意，3已拒绝
	 */
	private String stepDesc;
	/**
	 * 审批类型 2 请假 3报销
	 */
	private Byte flowType;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新时间
	 */
	private String updateDate;
	/**
	 * 是否删除 0 否 1 是
	 */
	private Byte isDel;
	/**
	 * 是否撤回 0 否 1撤回
	 */
	private Byte isWithdraw;
	/**
	 * 存储信息
	 */
	private Map<String,Object> extraInfos;
	/**
	 * 请假列表
	 */
	private List<FlowVacation> vacationList;
	/**
	 * 报销列表
	 */
	private List<FlowReimburse> reimburseList;
	/**
	 * 审核流程信息列表
	 */
	private List<Reply> replyList;
	/**
	 * 附件对象
	 */
	private List<FileInfo> fileLists;
	/**
	 * 附件逗号分隔
	 */
	private String file;
	// 场景类型  1-日报;2-任务;3-审批(收到的回复会用到这个字段)
	private Integer sceneType;
	//发起人名称
	private String userName;
	//LOGO
	private String logo;
	//人名的最后一个英文字母
	private String lastLetter;
	/**
	 * app端调用审批列表接口新增字段
	 * 表示流程的标题,如：xxx的请假申请
	 */
	private String title;
	
	/**
	 * app端调用审批列表接口新增字段
	 * 表示流审批状态,如：已通过,待审批
	 */
	private String stepText;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStepText() {
		return stepText;
	}
	public void setStepText(String stepText) {
		this.stepText = stepText;
	}
	public List<FileInfo> getFileLists() {
		return fileLists;
	}
	public void setFileLists(List<FileInfo> fileLists) {
		this.fileLists = fileLists;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public List<User> getSendToPersonList() {
		return sendToPersonList;
	}
	public void setSendToPersonList(List<User> sendToPersonList) {
		this.sendToPersonList = sendToPersonList;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<FlowVacation> getVacationList() {
		return vacationList;
	}
	public void setVacationList(List<FlowVacation> vacationList) {
		this.vacationList = vacationList;
	}
	public List<FlowReimburse> getReimburseList() {
		return reimburseList;
	}
	public void setReimburseList(List<FlowReimburse> reimburseList) {
		this.reimburseList = reimburseList;
	}
	public Map<String, Object> getExtraInfos() {
		return extraInfos;
	}
	public void setExtraInfos(Map<String, Object> extraInfos) {
		this.extraInfos = extraInfos;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendToPerson() {
		return sendToPerson;
	}
	public void setSendToPerson(String sendToPerson) {
		this.sendToPerson = sendToPerson;
	}
	public Byte getStep() {
		return step;
	}
	public void setStep(Byte step) {
		this.step = step;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Byte getIsDel() {
		return isDel;
	}
	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}
	public Byte getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Byte isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public Byte getFlowType() {
		return flowType;
	}
	public void setFlowType(Byte flowType) {
		this.flowType = flowType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStepDesc() {
		return stepDesc;
	}
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
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
	
}
