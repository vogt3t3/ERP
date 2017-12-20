package com.aiiju.dao.oa.usbKey;

import java.util.Map;

import com.aiiju.bean.oa.usbKey.UsbKey;


/**
 * @Description: UsbkeyDao
 * @author 琦玉
 * @date 2017年4月8日
 */
public interface IUsbKeyDao {
	/**
	 * 查找Usb
	 * @param params
	 * @return
	 */
	public UsbKey selectUsbKey(Map<String,Object> params);
	
	
	
}
