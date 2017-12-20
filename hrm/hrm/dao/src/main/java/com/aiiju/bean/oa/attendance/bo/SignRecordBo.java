package com.aiiju.bean.oa.attendance.bo;


/**
 * 用户当天的考勤记录(app接口用)
 * @author 小辉
 *
 */
public class SignRecordBo {
	//签到类型标记 1:签到 2:签退
	private int signType;
	//签到地址
	private String address;
	//规则时间
	private String ruleTime;
	//签到时间
	private String signTime;
	//签到状态 0：未签，1：正常，2：迟到，3：早退
	private int status;
	
	public int getSignType() {
		return signType;
	}
	public void setSignType(int signType) {
		this.signType = signType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRuleTime() {
		return ruleTime;
	}
	public void setRuleTime(String ruleTime) {
		this.ruleTime = ruleTime;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
