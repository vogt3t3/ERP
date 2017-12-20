package com.aiiju.dao.oa.usbKey;

import java.util.Map;

import com.aiiju.bean.oa.usbKey.UserUsbKey;




/**
 * @Description: 用户Usb关联
 * @author 琦玉
 * @date 2017年3月31 
 */
public interface IUserUsbKeyDao {
	/**
	 * 添加用户Usb关联信息
	 * @param params
	 * @return
	 */
	public int addUserUsbKey(Object obj);
	
	/**
	 * 修改用户Usb关联信息
	 * @param params
	 * @return
	 */
	public int updateUserUsbKey(Object obj);
	
	/**
	 * 查找用户Usb关联信息
	 * @param params
	 * @return
	 */
	public UserUsbKey selectUserUsbKey(Map<String,Object> params);
	
}