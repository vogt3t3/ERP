package com.crm.service;

import java.util.List;

import com.crm.model.Cst_activity_t;
import com.crm.model.Cst_customer_t;
import com.crm.model.Cst_indent_t;
import com.crm.model.Cst_linkman_t;
import com.crm.model.Cst_lst_t;
/**
 * 客户信息管理
 * @author yuduan
 *
 */
public interface ClientService extends BaseService{

	/**
	 * 客户信息编辑-保存更新
	 * @param c 客户信息
	 */
	void saveClient(Cst_customer_t c);
	
	/**
	 * 客户信息删除
	 * @param c
	 */
	void delClient (Cst_customer_t c);
	
	/**
	 * 客户联系人-保存更新
	 * @param l 联系人
	 */
	void saveLinkman(Cst_linkman_t l);
	
	/**
	 * 客户联系人删除
	 * @param l
	 */
	void delLinkman(Cst_linkman_t l);

	/**
	 * 查询联系人
	 */
	List<Cst_linkman_t> getLinkman(int clientId);
	
	/**
	 * 交往记录-保存更新
	 * @param a 交往记录
	 */
	void saveActivity(Cst_activity_t a);
	
	/**
	 * 交往记录-删除
	 * @param a
	 */
	void delActivity(Cst_activity_t a);
	
	/**
	 * 客户流失预警
	 * 在固定的时间点对超过6个月没有下单的客户添加流失预警记录
	 * 
	 */
	List<Cst_indent_t> LstList();
	

	/**
	 * 客户信息列表
	 */
	List<Cst_customer_t> listClient();
	
	/**
	 * 暂缓流失列表
	 */
	List<Cst_lst_t> lstList();
	
	/**
	 * 客户流失预警记录-显示记录
	 * 暂缓客户流失-对预警客户采取暂缓流失并填写预计采取的措施
	 * 客户流失-确认流失时的删除
	 * @param l
	 */

	void burreyLst(Cst_lst_t l);	
	void delLst(Cst_lst_t l);


	/**
	 * 查询客户信息
	 * @param manager 客户经理名
	 * @param area	     地区
	 * @param credit  客户信誉度
	 * @return
	 */
	List<Cst_customer_t> searchClient(String manager,String area,String credit);
	
}
