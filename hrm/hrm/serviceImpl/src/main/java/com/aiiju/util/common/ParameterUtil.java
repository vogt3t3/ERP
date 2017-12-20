package com.aiiju.util.common;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ParameterUtil {

	public static boolean check(String json, Class clazz) {
		if (null != json) {
			try {
				JSONObject.parseObject(json, clazz);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static Object parseObj(String json, Class clazz) {
		Object obj = JSONObject.parseObject(json, clazz);
		return obj;
	}

}
