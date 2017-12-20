package com.aiiju.service.staff;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.aiiju.bean.oa.staff.User;
/**
 * 
 * 购买流程service
 * @author 琦玉
 *	2017年5月11日	 
 */
public interface IBuyService {
   
	/**
	 * 新增送货地址
	 * @author 琦玉
	 * @param map
	 * @return
	 * 2017年5月11日  
	 */
	 public String addDeliveryAddress(Map<String,Object> map);
	 
	 
	 
	 /**
		 * 我的订单
		 * @author 琦玉
		 * @param map
		 * @return
		 * 2017年5月11日  
		 */
	 public Map<String,Object> myOrder(Map<String,Object> map);
		 
		 
		 
		 /**
			 * 用户信息
			 * @author 琦玉
			 * @param map
			 * @return
			 * 2017年5月11日  
			 */
	public Map<String,Object> userInfo(Map<String,Object> map);
}
