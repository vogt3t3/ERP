package com.aiiju.bean.oa.usbKey;

import java.io.Serializable;
import java.util.Date;




/**
 * 用户USB关联，并带有2级密码
 */

public class UserUsbKey implements Serializable {
	
	 /**
     * 主键
     */	
	private Long id;
	
	 /**
     * 用户ID
     */
	
	private Long userId;
	

	 /**
    * 公司ID
    */
	
	private Long companyId;
	
	/**
     * U盘ID：带权限的普通用户激活时，设置为null
     */
	
	private Long usbKeyId;
	
	 /**
     * 2级密码：管理员只激活Usbkey时，设置为null
     */
	
	
	private String secondPassword;
	
	 /**
     * 激活时间
     */
	
	private Date serialTime;
	
	

	
	/**
     * 一对一      映射 UsbKey实体
     */
	private UsbKey usbKey;


	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getUserId() {
		return userId;
	}




	public void setUserId(Long userId) {
		this.userId = userId;
	}




	public Long getCompanyId() {
		return companyId;
	}




	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}




	public Long getUsbKeyId() {
		return usbKeyId;
	}




	public void setUsbKeyId(Long usbKeyId) {
		this.usbKeyId = usbKeyId;
	}




	public String getSecondPassword() {
		return secondPassword;
	}




	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}




	public Date getSerialTime() {
		return serialTime;
	}




	public void setSerialTime(Date serialTime) {
		this.serialTime = serialTime;
	}




	




	public UsbKey getUsbKey() {
		return usbKey;
	}




	public void setUsbKey(UsbKey usbKey) {
		this.usbKey = usbKey;
	}




	@Override
	public String toString() {
		return "UserUsbKey [id=" + id + ", userId=" + userId + ", companyId=" + companyId + ", usbKeyId=" + usbKeyId
				+ ", secondPassword=" + secondPassword + ", serialTime=" + serialTime 
				+ ", usbKey=" + usbKey + "]";
	}



	
	
	
	
	
}
