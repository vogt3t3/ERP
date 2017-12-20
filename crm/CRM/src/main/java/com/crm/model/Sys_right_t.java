package com.crm.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * 系统权限表
 * @author 洪学超
 *
 */
@Entity
public class Sys_right_t {
	/*
	 * 序号 
	 */
	@Id
	@GeneratedValue
	private int right_id;
	
	/*
	 * 父权限id
	 */
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	private Sys_right_t sys_right_t;
	
	/*
	 * 权限类型
	 */
    private String right_type;
    
    /*
	 * 权限描述
	 */
    private String right_text;
    
    /*
	 * 权限对应的url
	 */
    private String rigth_url;
    
    /*
	 * 权限简称
	 */
    private String right_tip;
    
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_right_t",
    joinColumns = {@JoinColumn(name = "rr_right_id", referencedColumnName = "right_id")},
    inverseJoinColumns = {@JoinColumn(name = "rr_role_id", referencedColumnName ="role_id")})
    private Set<Sys_role_t> sys_role_t;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "sys_right_t")
    private Set<Sys_right_t> rights;
	public int getRight_id() {
		return right_id;
	}
	public void setRight_id(int right_id) {
		this.right_id = right_id;
	}
	
	
	
	public Sys_right_t getSys_right_t() {
		return sys_right_t;
	}
	public void setSys_right_t(Sys_right_t sys_right_t) {
		this.sys_right_t = sys_right_t;
	}
	public String getRight_type() {
		return right_type;
	}
	public void setRight_type(String right_type) {
		this.right_type = right_type;
	}
	public String getRight_text() {
		return right_text;
	}
	public void setRight_text(String right_text) {
		this.right_text = right_text;
	}
	public String getRigth_url() {
		return rigth_url;
	}
	public void setRigth_url(String rigth_url) {
		this.rigth_url = rigth_url;
	}
	public String getRight_tip() {
		return right_tip;
	}
	public void setRight_tip(String right_tip) {
		this.right_tip = right_tip;
	}
	
	
	
	
	public Set<Sys_role_t> getSys_role_t() {
		return sys_role_t;
	}
	public void setSys_role_t(Set<Sys_role_t> sys_role_t) {
		this.sys_role_t = sys_role_t;
	}
	public Set<Sys_right_t> getRights() {
		return rights;
	}
	public void setRights(Set<Sys_right_t> rights) {
		this.rights = rights;
	}
	
	
	
}
