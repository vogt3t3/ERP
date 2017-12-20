package com.crm.service;

import java.util.List;

import com.crm.model.Bas_product_t;
import com.crm.model.Bas_dict_t;

/**
 * 数据字典信息管理
 * @author yuduan
 *
 */
public interface DictService extends BaseService {
	/**
	 * 基础数据字典-保存更新
	 * @param b 基础数据字典
	 */
	
	void saveDict(Bas_dict_t b);
	/**
	 *  基础数据字典-删除
	 * @param b
	 */
	void delDict (Bas_dict_t b);
	/**
	 * 基础数据字典-查询
	 * @param item 数据项名称
	 * @return 查询结果
	 */
	List<Bas_dict_t> showDict(String type,String name,String value);
	/**
	 * 按id查询
	 * @param id
	 */
	List<Bas_dict_t> showDict(int id);
	/**
	 * 产品数据查询-显示产品信息
	 */
	List<Bas_product_t> showProduct(String name,String type,String rank);
	/**
	 * 产品信息、库存信息-保存更新
	 * @param b
	 */
	void saveProduct(Bas_product_t b);
	
	/**
	 * 库存查询-得到库存信息
	 */
	List<Bas_product_t> showStock(String pro,String depot);
	
}
