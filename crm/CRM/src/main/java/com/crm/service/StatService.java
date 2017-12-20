package com.crm.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import com.crm.model.Cst_lst_t;


/**
 * 统计报表
 * @author Administrator
 *
 */
public interface StatService extends BaseService {

	/**
	 * 客户贡献度分析
	 * 根据客户订单总金额进行统计
	 */
	JFreeChart offerAnalysis(String year);
	/**
	 * 客户构成分析
	 * 了解各种类型客户所占比例
	 */
	JFreeChart formAnalysis();
	/**
	 * 客户服务分析
	 * 根据服务类型对服务进行统计
	 */
	JFreeChart serviceAnalysis(String year);
	/**
	 * 客户流失分析
	 * 统计流失客户的流失记录
	 */
	List<Cst_lst_t> lstAnalysis();
}
