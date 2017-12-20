package com.aiiju.util.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 集合除重复工具（整合计算）
 * @author 琦玉
 *
 * 2016年14月15日   
 */
public class RemoveRepeatTools {

	
	/**
	  * 整合计数
	  * @param email
	  * @return
	  */
	 public static Map<String,Object> integrationCount(String [] arr){
		 Map<String,Object> map = new HashMap();
		 for (String string : arr) {
			if(map.get(string)==null){
				map.put(string, 1);
			}else {
				map.put(string, Integer.valueOf(map.get(string).toString())+1);
			}
		}
	 
	  return map;
	 }
	
	
}
