package com.crm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 客户联系人表
 * @author 洪学超
 *
 */
@Entity
public class Cst_linkman_t {
	/**
	 * 序号
	 */
	@Id
	@GeneratedValue
    private int link_id;
    /**
	 * 客户id
	 */
    private int link_cst_id;
    /**
	 * 客户名称
	 */
    private String link_cst_name;
    /**
	 * 联系人名称
	 */
    private String link_name;
    /**
	 * 性别
	 */
    private String link_sex;
    /**
	 * 职位
	 */
    private String link_postion;
    /**
	 * 座机
	 */
    private String link_tel;
    /**
	 * 手机
	 */
    private String link_mobile;
    /**
	 * 备注
	 */
    private String link_memo;
     
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Cst_customer_t cst_customer_t;
    
	public int getLink_id() {
		return link_id;
	}
	public void setLink_id(int link_id) {
		this.link_id = link_id;
	}
	public int getLink_cst_id() {
		return link_cst_id;
	}
	public void setLink_cst_id(int link_cst_id) {
		this.link_cst_id = link_cst_id;
	}
	public String getLink_cst_name() {
		return link_cst_name;
	}
	public void setLink_cst_name(String link_cst_name) {
		this.link_cst_name = link_cst_name;
	}
	public String getLink_name() {
		return link_name;
	}
	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}
	public String getLink_sex() {
		return link_sex;
	}
	public void setLink_sex(String link_sex) {
		this.link_sex = link_sex;
	}
	public String getLink_postion() {
		return link_postion;
	}
	public void setLink_postion(String link_postion) {
		this.link_postion = link_postion;
	}
	public String getLink_tel() {
		return link_tel;
	}
	public void setLink_tel(String link_tel) {
		this.link_tel = link_tel;
	}
	public String getLink_mobile() {
		return link_mobile;
	}
	public void setLink_mobile(String link_mobile) {
		this.link_mobile = link_mobile;
	}
	public String getLink_memo() {
		return link_memo;
	}
	public void setLink_memo(String link_memo) {
		this.link_memo = link_memo;
	}
	public Cst_customer_t getCst_customer_t() {
		return cst_customer_t;
	}
	public void setCst_customer_t(Cst_customer_t cst_customer_t) {
		this.cst_customer_t = cst_customer_t;
	}
	
	
}
