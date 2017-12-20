package com.crm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 
 * @author 吴凯鑫
 *
 */
@Entity
public class Sys_user_t {
	
    /**
	 * 序号
	 */
	@Id
	@GeneratedValue
    private int user_id;	
	
    /**
	 * 用户名称
	 */     
    private String user_name;
    
    /**
	 * 用户密码
	 */     
    private String user_password;
    
    /**
	 * 用户角色id
	 */     
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)    
    private Sys_role_t sys_role_t;
    
    /**
	 * 用户状态
	 */     
    private String user_flag;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}



	public Sys_role_t getSys_role_t() {
		return sys_role_t;
	}

	public void setSys_role_t(Sys_role_t sys_role_t) {
		this.sys_role_t = sys_role_t;
	}

	public String getUser_flag() {
		return user_flag;
	}

	public void setUser_flag(String user_flag) {
		this.user_flag = user_flag;
	}

    
}
