package com.aiiju.service.usbKey;

import java.util.Map;
/** 
 * USB Service
@Author  琦玉 

*/
public interface IUsbCheckService  {
	
	
	/**
	 * 用户是否激活(管理员需要激活USBKEY和二级密码，权限用户需要激活二级密码)
	 * @param: params
	 * @throws Exception 
	 */

	public void userCheckActivation (Map<String, Object> map) throws Exception;
	
	/**
	 * 用户激活UsbKey
	 * @param: params
	 * @throws Exception 
	 */
	public void userActivationUSBKey(Map<String, Object> params) throws Exception;
	/**
	 * 用户激活二级密码
	 * @param: params
	 * @throws Exception 
	 */
	public void userActivationSecondPassword(Map<String, Object> params) throws Exception;
	
	/**
	 * 用户验证二级密码
	 * @param: params
	 * @throws Exception 
	 */
	public void userCheckSecondPassword(Map<String, Object> params) throws Exception;

	
	
	

}
