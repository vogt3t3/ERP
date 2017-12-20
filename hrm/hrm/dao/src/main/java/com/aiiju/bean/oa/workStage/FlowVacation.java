package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
import java.util.Date;
/**
 * 请假实体类
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public class FlowVacation implements Serializable{

	/**
	 * 可序列化Id
	 */
	private static final long serialVersionUID = -5684607084349969007L;
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
	 * 审批Id
	 */
	private Long flowId;
	/**
	 * 请假类型
	 */
	private Integer vacationType;
	/**
	 * 请假类型Str
	 */
	private String vacationTypeStr;
	/**
	 * 请假开始时间
	 */
	private String startDate;
	/**
	 * 请假结束时间
	 */
	private String endDate;
	/**
	 * 请假时间
	 */
	private Float period;
	/**
	 * 创建时间
	 */
	private Date createDate;
	private String userName; // 用户名
	private String deptName;
	private String content; // 请假内容
	private String sendToPerson; // 审批人
    private String recipient; // 审批人姓名,根据sendToPerson查找的
    private int step; // 审批状态
    private String stepDesc; // 审批状态描述
	public FlowVacation(){
		
	}
	public String getVacationTypeStr() {
		return vacationTypeStr;
	}
	public void setVacationTypeStr(String vacationTypeStr) {
		this.vacationTypeStr = vacationTypeStr;
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
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	public Integer getVacationType() {
		return vacationType;
	}
	public void setVacationType(Integer vacationType) {
		this.vacationType = vacationType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Float getPeriod() {
		return period;
	}
	public void setPeriod(Float period) {
		this.period = period;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getStepDesc() {
		return stepDesc;
	}
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	
}
