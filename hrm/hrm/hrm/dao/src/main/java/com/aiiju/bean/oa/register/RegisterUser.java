package com.aiiju.bean.oa.register;

import java.io.Serializable;

public class RegisterUser implements Serializable{
	/**
	 * 生成序列号
	 */
	private static final long serialVersionUID = -753750329470195442L;
	/**
	 * 主键
	 */
	private Long Id;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
