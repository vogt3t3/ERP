package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 婚姻状态
 * 
 * @author 维斯
 *	2016年10月31日	 下午5:45:57
 */
public enum Marital {

	MARITAL_ONE("1","未婚"),
	MARITAL_TWO("2","已婚"),
	MARITAL_THREE("3","离婚"),
	MARITAL_FOUR("4","丧偶");
	
	
	private String name;
	private String index;
	
	private Marital(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (Marital c : Marital.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    public static String getIndex(String name) {
      	for (Marital c : Marital.values()) {
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
		 for (Marital c : Marital.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}

	public static void main(String[] args) {
		System.out.println(getName("1"));
	}
}
