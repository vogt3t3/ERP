package com.crm.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 客户服务表
 * 
 * @author 洪学超
 * 
 */
@Entity
public class Cst_service_t {
	/**
	 * 服务编号
	 */
	@Id
	@GeneratedValue
	private int svc_id;
	/**
	 * 服务类型
	 */
	private String svc_type;
	/**
	 * 服务概要
	 */
	private String svc_title;
	/**
	 * 客户名称
	 */
	private String svc_cst_name;
	/**
	 *服务状态
	 */
	private String svc_status;
	/**
	 * 服务请求
	 */
	private String svc_request;
	/**
	 *  创建编号
	 */
	private int svc_create_id;
	/**
	 * 创建者
	 */
	private String svc_create_by;
	/**
	 *分配编号
	 */
	private int svc_due_id;
	/**
	 * 责任人
	 */
	private String svc_due_to;
	/**
	 * 分配日期
	 */
	private Date svc_due_date;
	/**
	 *处理概述
	 */
	private String svc_deal;
	/**
	 * 处理编号
	 */
	private int svc_deal_id;
	/**
	 *  处理者
	 */
	private String svc_deal_by;
	/**
	 * 处理日期
	 */
	private String svc_deal_date;
	/**
	 * 处理结果
	 */
	private String svc_result;
	/**
	 * 满意度
	 */
	private String svc_satisfy;

	/**
	 * 创建日期
	 */
	private Date svc_create_date;

	public int getSvc_id() {
		return svc_id;
	}

	public void setSvc_id(int svc_id) {
		this.svc_id = svc_id;
	}

	public String getSvc_type() {
		return svc_type;
	}

	public void setSvc_type(String svc_type) {
		this.svc_type = svc_type;
	}

	public String getSvc_title() {
		return svc_title;
	}

	public void setSvc_title(String svc_title) {
		this.svc_title = svc_title;
	}

	public String getSvc_cst_name() {
		return svc_cst_name;
	}

	public void setSvc_cst_name(String svc_cst_name) {
		this.svc_cst_name = svc_cst_name;
	}

	public String getSvc_status() {
		return svc_status;
	}

	public void setSvc_status(String svc_status) {
		this.svc_status = svc_status;
	}

	public String getSvc_request() {
		return svc_request;
	}

	public void setSvc_request(String svc_request) {
		this.svc_request = svc_request;
	}

	public int getSvc_create_id() {
		return svc_create_id;
	}

	public void setSvc_create_id(int svc_create_id) {
		this.svc_create_id = svc_create_id;
	}

	public String getSvc_create_by() {
		return svc_create_by;
	}

	public void setSvc_create_by(String svc_create_by) {
		this.svc_create_by = svc_create_by;
	}

	public int getSvc_due_id() {
		return svc_due_id;
	}

	public void setSvc_due_id(int svc_due_id) {
		this.svc_due_id = svc_due_id;
	}

	public String getSvc_due_to() {
		return svc_due_to;
	}

	public void setSvc_due_to(String svc_due_to) {
		this.svc_due_to = svc_due_to;
	}

	public Date getSvc_due_date() {
		return svc_due_date;
	}

	public void setSvc_due_date(Date svc_due_date) {
		this.svc_due_date = svc_due_date;
	}

	public String getSvc_deal() {
		return svc_deal;
	}

	public void setSvc_deal(String svc_deal) {
		this.svc_deal = svc_deal;
	}

	public int getSvc_deal_id() {
		return svc_deal_id;
	}

	public void setSvc_deal_id(int svc_deal_id) {
		this.svc_deal_id = svc_deal_id;
	}

	public String getSvc_deal_by() {
		return svc_deal_by;
	}

	public void setSvc_deal_by(String svc_deal_by) {
		this.svc_deal_by = svc_deal_by;
	}

	public String getSvc_deal_date() {
		return svc_deal_date;
	}

	public void setSvc_deal_date(String svc_deal_date) {
		this.svc_deal_date = svc_deal_date;
	}

	public String getSvc_result() {
		return svc_result;
	}

	public void setSvc_result(String svc_result) {
		this.svc_result = svc_result;
	}

	public String getSvc_satisfy() {
		return svc_satisfy;
	}

	public void setSvc_satisfy(String svc_satisfy) {
		this.svc_satisfy = svc_satisfy;
	}

	public Date getSvc_create_date() {
		return svc_create_date;
	}

	public void setSvc_create_date(Date svc_create_date) {
		this.svc_create_date = svc_create_date;
	}


}
