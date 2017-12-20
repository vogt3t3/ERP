package com.crm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Cst_indent_t implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	/**
	 * 客户id
	 */
	//private int cst_id;
	/**
	 * 客户名称
	 */
	
	/**
	 * 订单名称
	 */
	private  String indent_name;
	@Id
	@GeneratedValue
	private int indent_id;
	
	/**
	 * 日期
	 */
	private Date indent_date;
	
	/**
	 * 送货地址
	 */
	private String indent_destination;
	
	
	/**
	 * 总金额
	 */
	private Double indent_sum;
	
	
	/**
	 * 状态
	 */
	private String indent_state;
	
	/**
	 * 创建人
	 */
	private String indent_create_by;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Cst_customer_t cst_customer_t;
	

	public int getIndent_id() {
		return indent_id;
	}


	public void setIndent_id(int indent_id) {
		this.indent_id = indent_id;
	}


	public Date getIndent_date() {
		return indent_date;
	}


	public void setIndent_date(Date indent_date) {
		this.indent_date = indent_date;
	}


	public String getIndent_destination() {
		return indent_destination;
	}


	public void setIndent_destination(String indent_destination) {
		this.indent_destination = indent_destination;
	}


	public Double getIndent_sum() {
		return indent_sum;
	}


	public void setIndent_sum(Double indent_sum) {
		this.indent_sum = indent_sum;
	}


	public String getIndent_state() {
		return indent_state;
	}


	public void setIndent_state(String indent_state) {
		this.indent_state = indent_state;
	}


	public Cst_customer_t getCst_customer_t() {
		return cst_customer_t;
	}


	public void setCst_customer_t(Cst_customer_t cst_customer_t) {
		this.cst_customer_t = cst_customer_t;
	}


	public String getIndent_name() {
		return indent_name;
	}


	public void setIndent_name(String indent_name) {
		this.indent_name = indent_name;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getIndent_create_by() {
		return indent_create_by;
	}


	public void setIndent_create_by(String indent_create_by) {
		this.indent_create_by = indent_create_by;
	}


	






	
	
	
}
