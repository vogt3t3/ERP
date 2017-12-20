package com.crm.service;

import java.util.List;

import com.crm.model.Cst_indent_t;

/**
 * 订单管理
 * @author yuduan
 *
 */
public interface IndentService extends BaseService {

	void saveOrUpdateIndent(Cst_indent_t i);
	
	/**
	 * 订单-保存、更新
	 * @param p 
	 */
	
	/**
	 * 订单-删除
	 * @param i
	 */
	void deleteIndent(Cst_indent_t i);
	/**
	 * 订单-查询
	 * @param i
	 * @return
	 */
	List<Cst_indent_t> showIndent(String iname,String cname);

	/**
	 * 订单列表
	 * @return
	 */
	List<Cst_indent_t> listIndent();
	
}
