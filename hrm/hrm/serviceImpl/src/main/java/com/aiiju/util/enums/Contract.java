package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;
/**
 * 合同类型
 * @author 维斯
 *
 * 2016年11月19日   下午1:44:10
 */
public enum Contract {

	CONTRACTTYPE_ONE("1","劳动合同"),
	CONTRACTTYPE_THREE("2","实习协议"),
	CONTRACTTYPE_FOUR("3","保密协议"),
	CONTRACTTYPE_FIVE("4","培训协议"),
	CONTRACTTYPE_SIX("5","兼职协议");
	
	private String name;
	private String index;
	
	private Contract(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (Contract c : Contract.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    public static String getIndex(String name) {
    	for (Contract c : Contract.values()) {
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
		 for (Contract c : Contract.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}
    
}
