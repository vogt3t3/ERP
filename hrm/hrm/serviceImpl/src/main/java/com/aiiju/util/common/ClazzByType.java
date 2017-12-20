package com.aiiju.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
/**
 * 获取对象属性 值
 * @author 维斯
 *
 * 2016年11月4日   下午4:47:17
 */
public class ClazzByType {

	/** 
	 * 根据属性名获取属性值 
	 * */  
	public static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {  
	    	   System.err.println(e);
	    	   return null;     
	       }
	   }   
	   /** 
	    * 获取属性名数组 
	    * */  
	public static String[] getFiledName(Object o){  
		    Field[] fields=o.getClass().getDeclaredFields();  
		        String[] fieldNames=new String[fields.length];  
		    for(int i=0;i<fields.length;i++){  
		        fieldNames[i]=fields[i].getName();  
		    }  
		    return fieldNames;  
	   }  
	
	/**
	 * 对象转map
	 * @author 维斯
	 * @param obj
	 * @return
	 * 2017年2月6日 	上午11:13:37
	 */
	 public static LinkedHashMap<String,Object> ConvertObjToMap(Object obj){
		 LinkedHashMap<String,Object> reMap = new LinkedHashMap<String,Object>();
		  if (obj == null) 
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
		   for(int i=0;i<fields.length;i++){
		    try {
		     Field f = obj.getClass().getDeclaredField(fields[i].getName());
		     f.setAccessible(true);
		           Object o = f.get(obj);
		           reMap.put(fields[i].getName(), o);
		    } catch (NoSuchFieldException e) {
		     e.printStackTrace();
		    } catch (IllegalArgumentException e) {
		     e.printStackTrace();
		    } catch (IllegalAccessException e) {
		     e.printStackTrace();
		    }
		   }
		  } catch (SecurityException e) {
		   e.printStackTrace();
		  } 
		  return reMap;
		 }
	 

}
