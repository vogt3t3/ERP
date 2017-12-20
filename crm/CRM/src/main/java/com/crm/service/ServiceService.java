package com.crm.service;

import java.util.List;

import com.crm.model.Cst_service_t;

/**
 * 服务管理
 * @author yuduan
 *
 */
public interface ServiceService extends BaseService {
	
	
	/**
	 * 服务创建
	 * 完成为客户创建服务的操作
	 */
	void saveService(Cst_service_t s);
	
	/**
	 * 服务分配
	 * 将已有的服务分配给专门的客户经理进行服务
	 * 
	 */
	void distService(Cst_service_t s);
	
	/**
	 * 服务分配列表
	 */
	public List<Cst_service_t> listService();
	
	/**
	 * 查询服务
	 * 服务分配时应该先调用服务查询。
	 * 服务处理时应该先调用服务查询
	 */
	/**
	 * 服务处理
	 * 添加服务的处理结果
	 */
	void disposeService(Cst_service_t s);
	/**
	 * 服务反馈
	 * 录入客户对服务的结果满意度
	 */
	void feedbackService(Cst_service_t s);
	/**
	 * 服务归档
	 * 如果客户反馈的满意度高于3则服务完成并进行归档否则重新处理
	 */
	void fileService(Cst_service_t s);
	
	
	public List<Cst_service_t> showService(String cstname,String title,String type,String status);
	
}
