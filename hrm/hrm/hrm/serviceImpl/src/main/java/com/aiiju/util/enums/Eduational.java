package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 学历
 * 
 * @author 维斯
 *	2016年10月31日	 下午5:36:32
 */
public enum Eduational {
 
	
	EDUATIONAL_TWO("1","高中及中专"),
	EDUATIONAL_THREE("2","大专"),
	EDUATIONAL_FOUR("3","本科"),
	EDUATIONAL_FIVE("4","硕士研究生"),
	EDUATIONAL_SIX("5","博士研究生");
	
	private String name;
	private String index;
	private Eduational(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (Eduational c : Eduational.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
    	for (Eduational c : Eduational.values()) {
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
		 for (Eduational c : Eduational.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}
	
}
