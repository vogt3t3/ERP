package com.crm.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 客户信息表
 * @author 吴凯鑫
 *
 */
@Entity
public class Cst_customer_t implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 序号
	 */
	@Id
	@GeneratedValue
    private int cst_id;
	
	
    /**
	 * 客户名称
	 */     
    private String cst_name;
    
     /**
 	 * 客户区域
 	 */
    private String cst_region;
    
	/**
	 * 客户经理
	 */
    private int cst_manager_id;
    
    /**
	 * 客户经理名称
	 */
    private String cust_manager_name;
    
    /**
	 * 客户级别
	 */
    private int cst_level;
    
    /**
	 * 客户级别名称
	 */
    private String cst_level_label;
    
    /**
	 * 客户满意度
	 */
    private int cst_satisfy;
    
    /**
	 * 客户信誉度
	 */
    private int cst_credit;
    
    /**
	 * 客户地址
	 */
    private String cst_addr;
    
    /**
	 * 客户邮编
	 */
    private String cst_zip;
    
    /**
	 * 客户电话
	 */
    private String cst_tel;
    
    /**
	 * 客户传真
	 */
    private String cst_fax;
    
    /**
	 * 客户网站
	 */
    private String cst_website;
    
    /**
	 * 客户营业执照注册号
	 */
    private String cst_licence_no;
    
    /**
	 * 客户法人
	 */
    private String cst_chieftain;
    
    /**
	 * 注册金额
	 */
    private int cst_bankroll;
    
    /**
	 * 年营业额
	 */
    private int cst_turnover;
    
    /**
	 * 开户银行
	 */
    private String cst_bank;
    
    /**
	 * 银行账户
	 */
    private String cst_bank_account;
    
    /**
	 * 地税登记号
	 */
    private String cst_local_tax_no;
    
    /**
	 * 国税登记号
	 */
    private String cst_national_tax_no;
    
    /**
	 * 客户状态
	 */
    private int cst_status;

    /**
     * 联系人
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cst_customer_t")
    private Set<Cst_linkman_t> cst_linkman_ts;
    
    /**
     * 交往记录
     * @return
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cst_customer_t")
    private Set<Cst_activity_t> cst_activity_t;
    
    /**
     * 历史订单
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cst_customer_t")
    private Set<Cst_indent_t> cst_indent_t;
	public int getCst_id() {
		return cst_id;
	}

	public void setCst_id(int cst_id) {
		this.cst_id = cst_id;
	}

	public String getCst_name() {
		return cst_name;
	}

	public void setCst_name(String cst_name) {
		this.cst_name = cst_name;
	}

	public String getCst_region() {
		return cst_region;
	}

	public void setCst_region(String cst_region) {
		this.cst_region = cst_region;
	}

	public int getCst_manager_id() {
		return cst_manager_id;
	}

	public void setCst_manager_id(int cst_manager_id) {
		this.cst_manager_id = cst_manager_id;
	}

	public String getCust_manager_name() {
		return cust_manager_name;
	}

	public void setCust_manager_name(String cust_manager_name) {
		this.cust_manager_name = cust_manager_name;
	}

	public int getCst_level() {
		return cst_level;
	}

	public void setCst_level(int cst_level) {
		this.cst_level = cst_level;
	}

	public String getCst_level_label() {
		return cst_level_label;
	}

	public void setCst_level_label(String cst_level_label) {
		this.cst_level_label = cst_level_label;
	}

	public int getCst_satisfy() {
		return cst_satisfy;
	}

	public void setCst_satisfy(int cst_satisfy) {
		this.cst_satisfy = cst_satisfy;
	}

	public int getCst_credit() {
		return cst_credit;
	}

	public void setCst_credit(int cst_credit) {
		this.cst_credit = cst_credit;
	}

	public String getCst_addr() {
		return cst_addr;
	}

	public void setCst_addr(String cst_addr) {
		this.cst_addr = cst_addr;
	}

	public String getCst_zip() {
		return cst_zip;
	}

	public void setCst_zip(String cst_zip) {
		this.cst_zip = cst_zip;
	}

	public String getCst_tel() {
		return cst_tel;
	}

	public void setCst_tel(String cst_tel) {
		this.cst_tel = cst_tel;
	}

	public String getCst_fax() {
		return cst_fax;
	}

	public void setCst_fax(String cst_fax) {
		this.cst_fax = cst_fax;
	}

	public String getCst_website() {
		return cst_website;
	}

	public void setCst_website(String cst_website) {
		this.cst_website = cst_website;
	}

	public String getCst_licence_no() {
		return cst_licence_no;
	}

	public void setCst_licence_no(String cst_licence_no) {
		this.cst_licence_no = cst_licence_no;
	}

	public String getCst_chieftain() {
		return cst_chieftain;
	}

	public void setCst_chieftain(String cst_chieftain) {
		this.cst_chieftain = cst_chieftain;
	}

	public int getCst_bankroll() {
		return cst_bankroll;
	}

	public void setCst_bankroll(int cst_bankroll) {
		this.cst_bankroll = cst_bankroll;
	}

	public int getCst_turnover() {
		return cst_turnover;
	}

	public void setCst_turnover(int cst_turnover) {
		this.cst_turnover = cst_turnover;
	}

	public String getCst_bank() {
		return cst_bank;
	}

	public void setCst_bank(String cst_bank) {
		this.cst_bank = cst_bank;
	}

	public String getCst_bank_account() {
		return cst_bank_account;
	}

	public void setCst_bank_account(String cst_bank_account) {
		this.cst_bank_account = cst_bank_account;
	}

	public String getCst_local_tax_no() {
		return cst_local_tax_no;
	}

	public void setCst_local_tax_no(String cst_local_tax_no) {
		this.cst_local_tax_no = cst_local_tax_no;
	}

	public String getCst_national_tax_no() {
		return cst_national_tax_no;
	}

	public void setCst_national_tax_no(String cst_national_tax_no) {
		this.cst_national_tax_no = cst_national_tax_no;
	}

	
	public int getCst_status() {
		return cst_status;
	}

	public void setCst_status(int cst_status) {
		this.cst_status = cst_status;
	}

	public Set<Cst_linkman_t> getCst_linkman_ts() {
		return cst_linkman_ts;
	}

	public void setCst_linkman_ts(Set<Cst_linkman_t> cst_linkman_ts) {
		this.cst_linkman_ts = cst_linkman_ts;
	}

	public Set<Cst_activity_t> getCst_activity_t() {
		return cst_activity_t;
	}

	public void setCst_activity_t(Set<Cst_activity_t> cst_activity_t) {
		this.cst_activity_t = cst_activity_t;
	}

	public Set<Cst_indent_t> getCst_indent_t() {
		return cst_indent_t;
	}

	public void setCst_indent_t(Set<Cst_indent_t> cst_indent_t) {
		this.cst_indent_t = cst_indent_t;
	}

	@Override
	public String toString() {
		return "Cst_customer_t [cst_id=" + cst_id + ", cst_name=" + cst_name
				+ ", cst_region=" + cst_region + ", cst_manager_id="
				+ cst_manager_id + ", cust_manager_name=" + cust_manager_name
				+ ", cst_level=" + cst_level + ", cst_level_label="
				+ cst_level_label + ", cst_satisfy=" + cst_satisfy
				+ ", cst_credit=" + cst_credit + ", cst_addr=" + cst_addr
				+ ", cst_zip=" + cst_zip + ", cst_tel=" + cst_tel
				+ ", cst_fax=" + cst_fax + ", cst_website=" + cst_website
				+ ", cst_licence_no=" + cst_licence_no + ", cst_chieftain="
				+ cst_chieftain + ", cst_bankroll=" + cst_bankroll
				+ ", cst_turnover=" + cst_turnover + ", cst_bank=" + cst_bank
				+ ", cst_bank_account=" + cst_bank_account
				+ ", cst_local_tax_no=" + cst_local_tax_no
				+ ", cst_national_tax_no=" + cst_national_tax_no
				+ ", cst_status=" + cst_status + ", cst_linkman_ts="
				+ cst_linkman_ts + ", cst_activity_t=" + cst_activity_t
				+ ", cst_indent_t=" + cst_indent_t + "]";
	}

}
