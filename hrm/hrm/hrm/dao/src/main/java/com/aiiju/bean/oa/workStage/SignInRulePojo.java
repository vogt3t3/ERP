package com.aiiju.bean.oa.workStage;

import java.io.Serializable;
/**
 * 
 * @ClassName: SignInRulePojo
 * @Description: 考勤规则实体类
 * @author 琪琪
 * @date 2016年9月9日 上午11:02:23 
 *
 */
public class SignInRulePojo  implements Serializable{

	/**
	 * 序列化serialVersionUID
	 */
	private static final long serialVersionUID = 2380478903175255415L;
	private int id;//规则Id
	private String signInTime;//上班考勤时间
	private String signOutTime;//下班考勤时间
	private int isSignInAlert;//签到提醒
	private int isSignOutAlert;//签退提醒
	private int signOutAlertTime;//签退提前多少提醒，单位秒
	private int signInAlertTime;//签到提前多少提醒，单位秒
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}
	public String getSignOutTime() {
		return signOutTime;
	}
	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}
	public int getIsSignInAlert() {
		return isSignInAlert;
	}
	public void setIsSignInAlert(int isSignInAlert) {
		this.isSignInAlert = isSignInAlert;
	}
	public int getIsSignOutAlert() {
		return isSignOutAlert;
	}
	public void setIsSignOutAlert(int isSignOutAlert) {
		this.isSignOutAlert = isSignOutAlert;
	}
	public int getSignOutAlertTime() {
		return signOutAlertTime;
	}
	public void setSignOutAlertTime(int signOutAlertTime) {
		this.signOutAlertTime = signOutAlertTime;
	}
	public int getSignInAlertTime() {
		return signInAlertTime;
	}
	public void setSignInAlertTime(int signInAlertTime) {
		this.signInAlertTime = signInAlertTime;
	}
	
	
	

}
