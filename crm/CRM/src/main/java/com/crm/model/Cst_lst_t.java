package com.crm.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 流失客户表
 * @author 洪学超
 *
 */
@Entity
public class Cst_lst_t {
	/**
	 * 序号
	 */
	@Id
	@GeneratedValue
     private int lst_id;
   
	/**
 	 * 流失客户id
 	 */
     private String lst_cst_id;
     /**
 	 * 流失客户名称
 	 */
     private String lst_cst_name;
     /**
 	 * 客户经理id
 	 */
     private int lst_cst_manager_id;
     /**
 	 * 客户经理名称
 	 */
     private String lst_cst_manager_name;
     /**
 	 * 最后订单日期
 	 */
     private Date lst_last_order_date;
     /**
 	 * 暂缓流失措施
 	 */
     private String lst_delay;
     /**
 	 * 流失原因
 	 */
     private String lst_reason;
     /**
      * 确认流失时间
      */
     private Date sureLstDate;
     /**
 	 * 状态
 	 */
     private String lst_status;
     
     public int getLst_id() {
 		return lst_id;
 	}
 	public void setLst_id(int lst_id) {
 		this.lst_id = lst_id;
 	}
 	public String getLst_cst_id() {
 		return lst_cst_id;
 	}
 	public void setLst_cst_id(String lst_cst_id) {
 		this.lst_cst_id = lst_cst_id;
 	}
 	public String getLst_cst_name() {
 		return lst_cst_name;
 	}
 	public void setLst_cst_name(String lst_cst_name) {
 		this.lst_cst_name = lst_cst_name;
 	}
 
 	public int getLst_cst_manager_id() {
		return lst_cst_manager_id;
	}
	public void setLst_cst_manager_id(int lst_cst_manager_id) {
		this.lst_cst_manager_id = lst_cst_manager_id;
	}
	public String getLst_cst_manager_name() {
 		return lst_cst_manager_name;
 	}
 	public void setLst_cst_manager_name(String lst_cst_manager_name) {
 		this.lst_cst_manager_name = lst_cst_manager_name;
 	}
 
 	public Date getLst_last_order_date() {
		return lst_last_order_date;
	}
	public void setLst_last_order_date(Date lst_last_order_date) {
		this.lst_last_order_date = lst_last_order_date;
	}
	public String getLst_delay() {
 		return lst_delay;
 	}
 	public void setLst_delay(String lst_delay) {
 		this.lst_delay = lst_delay;
 	}
 	public String getLst_reason() {
 		return lst_reason;
 	}
 	public void setLst_reason(String lst_reason) {
 		this.lst_reason = lst_reason;
 	}
 	public String getLst_status() {
 		return lst_status;
 	}
 	public void setLst_status(String lst_status) {
 		this.lst_status = lst_status;
 	}
	public Date getSureLstDate() {
		return sureLstDate;
	}
	public void setSureLstDate(Date sureLstDate) {
		this.sureLstDate = sureLstDate;
	}
 	
}
