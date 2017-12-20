package com.crm.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * 营销机会表
 * @author 洪学超
 *
 */
@Entity
public class Sal_chance_t {
	
	
	/**
	 * 序号 
	 */
	@Id
	@GeneratedValue
    private int chc_id;
	
	/**
	 * 机会来源
	 */
    private String chc_source;
    
	/**
	 * 客户名称
	 */
    private String chc_cust_name;
    
	/**
	 * 机会名称
	 */
    private String chc_title;
    
    /**
	 * 成功几率
	 */
    private int chc_rate;
    
	/**
	 * 客户联系人
	 */
    private String chc_linkman;
    
	/**
	 * 联系电话
	 */
    private String chc_tel;
    
	/**
	 * 机会描述
	 */
    private String chc_desc;
    
	/**
	 * 序号 
	 */
    private int chc_create_id;
    
	/**
	 * 创建人
	 */
    private String chc_create_by;
    
	/**
	 * 创建日期
	 */
    private Date chc_create_date;
    
	/**
	 * 序号 
	 */
    private int chc_due_id;
    
	/**
	 * 责任人
	 */
    private int chc_due_to;
    
	/**
	 * 分配日期
	 */
    private Date chc_due_date;

	/**
	 * 机会状态
	 */
    private String chc_status;

	public int getChc_id() {
		return chc_id;
	}

	public void setChc_id(int chc_id) {
		this.chc_id = chc_id;
	}

	public String getChc_source() {
		return chc_source;
	}

	public void setChc_source(String chc_source) {
		this.chc_source = chc_source;
	}

	public String getChc_cust_name() {
		return chc_cust_name;
	}

	public void setChc_cust_name(String chc_cust_name) {
		this.chc_cust_name = chc_cust_name;
	}

	public String getChc_title() {
		return chc_title;
	}

	public void setChc_title(String chc_title) {
		this.chc_title = chc_title;
	}

	public int getChc_rate() {
		return chc_rate;
	}

	public void setChc_rate(int chc_rate) {
		this.chc_rate = chc_rate;
	}

	public String getChc_linkman() {
		return chc_linkman;
	}

	public void setChc_linkman(String chc_linkman) {
		this.chc_linkman = chc_linkman;
	}

	public String getChc_tel() {
		return chc_tel;
	}

	public void setChc_tel(String chc_tel) {
		this.chc_tel = chc_tel;
	}

	public String getChc_desc() {
		return chc_desc;
	}

	public void setChc_desc(String chc_desc) {
		this.chc_desc = chc_desc;
	}

	public int getChc_create_id() {
		return chc_create_id;
	}

	public void setChc_create_id(int chc_create_id) {
		this.chc_create_id = chc_create_id;
	}

	public String getChc_create_by() {
		return chc_create_by;
	}

	public void setChc_create_by(String chc_create_by) {
		this.chc_create_by = chc_create_by;
	}

	public Date getChc_create_date() {
		return chc_create_date;
	}

	public void setChc_create_date(Date chc_create_date) {
		this.chc_create_date = chc_create_date;
	}

	

	public int getChc_due_to() {
		return chc_due_to;
	}

	public void setChc_due_to(int chc_due_to) {
		this.chc_due_to = chc_due_to;
	}

	public int getChc_due_id() {
		return chc_due_id;
	}

	public void setChc_due_id(int chc_due_id) {
		this.chc_due_id = chc_due_id;
	}

	public Date getChc_due_date() {
		return chc_due_date;
	}

	public void setChc_due_date(Date chc_due_date) {
		this.chc_due_date = chc_due_date;
	}

	public String getChc_status() {
		return chc_status;
	}

	public void setChc_status(String chc_status) {
		this.chc_status = chc_status;
	}
    
    
}
