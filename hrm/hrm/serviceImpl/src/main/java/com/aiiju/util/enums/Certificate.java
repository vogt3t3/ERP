package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 证件类型
 * 
 * @author 维斯
 *	2016年10月31日	 下午5:40:39
 */
public enum Certificate {
	
	CERTIFICATE_ONE("1","身份证"),
	CERTIFICATE_TWO("2","因私护照"),
	CERTIFICATE_THREE("3","因公护照"),
	CERTIFICATE_FOUR("4","香港永久居民身份证"),
	CERTIFICATE_FIVE("5","澳门永久居民身份证"),
	CERTIFICATE_SIX("6","港澳居民来往内地通行证"),
	CERTIFICATE_SEVEN("7","台湾居民来往大陆通行证"),
	CERTIFICATE_EIGHT("8","外国人永久居留证"),
	CERTIFICATE_NINE("9","其他证件");
	
	private String name;
	private String index;
	
	private Certificate(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (Certificate c : Certificate.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    public static String getIndex(String name) {
    	for (Certificate c : Certificate.values()) {
    		if (c.getName().equals(name)) {
    			return c.index;
    		}
    	}
    	return null;
    }

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}
	
	public static List<String> getAll(){
		List<String> list = new ArrayList<String>();
		 for (Certificate c : Certificate.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}
    
}
