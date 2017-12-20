package com.crm.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 
 * 系统角色表
 * @author 洪学超
 *
 */
@Entity
public class Sys_role_t {
	/*
	 * 序号 
	 */
	@Id
	@GeneratedValue
	private int role_id;
	
	/*
	 * 角色名称
	 */
	private String role_name;
	
	/*
	 * 角色描述
	 */
	private String role_desc;
	
	/*
	 * 角色状态
	 */
	private String role_flag;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sys_role_t")
	private Set<Sys_right_t> sys_right_t;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sys_role_t")
	private Set<Sys_user_t> sys_user_t;
	
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	public String getRole_flag() {
		return role_flag;
	}

	public void setRole_flag(String role_flag) {
		this.role_flag = role_flag;
	}

	public Set<Sys_right_t> getSys_right_t() {
		return sys_right_t;
	}

	public void setSys_right_t(Set<Sys_right_t> sys_right_t) {
		this.sys_right_t = sys_right_t;
	}

	
	
	public Set<Sys_user_t> getSys_user_t() {
		return sys_user_t;
	}

	public void setSys_user_t(Set<Sys_user_t> sys_user_t) {
		this.sys_user_t = sys_user_t;
	}

	@Override
	public String toString() {
		return "Sys_role_t [role_id=" + role_id + ", role_name=" + role_name
				+ ", role_desc=" + role_desc + ", role_flag=" + role_flag
				+ ", sys_right_t=" + sys_right_t + "]";
	}

	
}
