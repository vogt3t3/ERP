package com.aiiju.bean.oa.register;

import java.io.Serializable;
/**
 * 公司实体类
 * @author qiqi
 * @date 2016-12-29 11:11:11
 */
public class Company implements Serializable{

	/**
	 * 生成反序列号
	 */
	private static final long serialVersionUID = -5913606858596139193L;
	/**
	 * 主键Id
	 */
    private Long id;
    /**
     * 公司名
     */
    private String companyName;
    /**
     * 手机号码
     */
    private String phone;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
    
}
