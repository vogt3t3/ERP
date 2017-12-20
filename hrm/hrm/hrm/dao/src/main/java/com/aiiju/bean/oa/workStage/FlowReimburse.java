package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
/**
 * 报销实体类
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public class FlowReimburse implements Serializable{

	/**
	 * 可序列化Id
	 */
	private static final long serialVersionUID = -8527569970139645819L;
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
	 * 姓名
	 */
	private String userName;
	/**
	 *审批Id
	 */
	private Long flowId;
	/**
	 * 报销类型
	 */
	private Integer feeType;
	/**
	 * 报销类型Str
	 */
	private String feeTypeStr;
	/**
	 * 报销金额
	 */
	private Double fees;
	/**
	 * 备注
	 */
	private String  remark;
	/**
	 * 创建时间
	 */
	private String createDate;
	// 内容
    private String content;
    // 接收人
    private String sendToPerson;
    // 接收人姓名
    private String recipient;
    // 部门名称
    private String deptName;
    // 状态
    private Integer step;
    // 状态转义
    private String stepDesc;
	
	public String getFeeTypeStr() {
		return feeTypeStr;
	}
	public void setFeeTypeStr(String feeTypeStr) {
		this.feeTypeStr = feeTypeStr;
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
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getStepDesc() {
		return stepDesc;
	}
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	
}
