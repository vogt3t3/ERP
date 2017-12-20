package com.crm.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 客户交往记录表
 * @author 洪学超
 *
 */
@Entity
public class Cst_activity_t {
	
	/**
	 * 序号
	 */
	@Id
	@GeneratedValue
     private int atv_id;
//     /**
// 	 * 客户id
// 	 */
//     private int atv_cst_id;
//     /**
// 	 * 客户名称
// 	 */
//     private String atv_cst_name;
     /**
 	 * 交往时间
 	 */
     private Date atv_date;
     /**
 	 * 交往地点
 	 */
     private String atv_place;
     /**
 	 * 交往概要
 	 */
     private String atv_title;
     /**
      * 详细信息
      * 
      */
     @Basic(fetch = FetchType.LAZY)
 	 @Column(columnDefinition="text")
     private String atv_detail;
     /**
 	 * 交往备注
 	 */
     private String atv_desc;
     
     @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
     private Cst_customer_t cst_customer_t;
     public int getAtv_id() {
 		return atv_id;
 	}
 	public void setAtv_id(int atv_id) {
 		this.atv_id = atv_id;
 	}
 
 	public Date getAtv_date() {
 		return atv_date;
 	}
 	public void setAtv_date(Date atv_date) {
 		this.atv_date = atv_date;
 	}
 	public String getAtv_place() {
 		return atv_place;
 	}
 	public void setAtv_place(String atv_place) {
 		this.atv_place = atv_place;
 	}
 	public String getAtv_title() {
 		return atv_title;
 	}
 	public void setAtv_title(String atv_title) {
 		this.atv_title = atv_title;
 	}
 	public String getAtv_desc() {
 		return atv_desc;
 	}
 	public void setAtv_desc(String atv_desc) {
 		this.atv_desc = atv_desc;
 	}
	public Cst_customer_t getCst_customer_t() {
		return cst_customer_t;
	}
	public void setCst_customer_t(Cst_customer_t cst_customer_t) {
		this.cst_customer_t = cst_customer_t;
	}
	public String getAtv_detail() {
		return atv_detail;
	}
	public void setAtv_detail(String atv_detail) {
		this.atv_detail = atv_detail;
	}
 	
}
