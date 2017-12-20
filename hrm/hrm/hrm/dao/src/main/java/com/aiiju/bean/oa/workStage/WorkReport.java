package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 工作汇报列表-实体类---列表查询使用
 * @author BZ
 *
 */

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
public class WorkReport implements Serializable{
	
	private static final long serialVersionUID = 813108285254713235L;
	//工作汇报ID
	private Long id;
	//公司ID
	private Long companyId;
	//发起人ID
	private Long userId;
	//发起人名称
	private String userName;
	//工作汇报类型 1：日报 2：周报 3：月报
	private int workReportType;
	//所属部门ID
	private Long deptId;
	//部门名称
	private String deptName;
	//发送内容
	private Object content;
	//回复数
	private int replyNum;
	//点赞数
	private int likeNum;
	//LOGO
	private String logo;
	//人名的最后一个英文字母
	private String lastLetter;
	//createTimeDesc,按照前端展示需求
	private String createTime;
	//点赞ID    是否已点赞---0:未点赞，存在ID 即为点赞
	private Integer likeId;
	//是否已点赞---0:未读，1：已读
	private Integer isRead;
	private Integer likeUserId;
	private Integer likeVisitId;
	private Integer isDel;
	private Integer isWithdraw;
	
	//发送人，发送部门
	private String sendToDept;
	private String sendToPerson;
	private String depts;
	private String persons;
	
	private List<FileInfo> fileInfos;
	/**
	 * 工作汇报相关的附件ids
	 */
	private String fileIds;
	
	//点赞人
	private List<Map<String,Object>> likePersons;
	// 场景类型  1-日报;2-任务;3-审批(收到的回复会用到这个字段)
	private Integer sceneType;
	
	private User userInfo;
	
	//回复对象数组
	private List<Reply> replys;
	//接收人对象数组
	private List<User> users;
	//接收部门对象数组
	private List<Department> departments;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getWorkReportType() {
		return workReportType;
	}
	public void setWorkReportType(int workReportType) {
		this.workReportType = workReportType;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getLikeId() {
		return likeId;
	}
	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getLikeUserId() {
		return likeUserId;
	}
	public void setLikeUserId(Integer likeUserId) {
		this.likeUserId = likeUserId;
	}
	public Integer getLikeVisitId() {
		return likeVisitId;
	}
	public void setLikeVisitId(Integer likeVisitId) {
		this.likeVisitId = likeVisitId;
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
	public String getDepts() {
		return depts;
	}
	public void setDepts(String depts) {
		this.depts = depts;
	}
	public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Integer isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public List<Map<String, Object>> getLikePersons() {
		return likePersons;
	}
	public void setLikePersons(List<Map<String, Object>> likePersons) {
		this.likePersons = likePersons;
	}
	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}
	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public User getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
	public List<Reply> getReplys() {
		return replys;
	}
	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	public String getFiles() {
		return fileIds;
	}
	public void setFiles(String fileIds) {
		this.fileIds = fileIds;
	}
	
}


