package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 政治面貌
 * 
 * @author 维斯
 *	2016年10月31日	 下午5:36:18
 */
public enum Formal {
	
	FORMAL_ONE("1","中共党员"),
	FORMAL_TWO("2","中共预备党员"),
	FORMAL_THREE("3","共青团员"),
	FORMAL_FOUR("4","其他");
	
	  private String name;
      private String index;
	  private Formal(String index, String name) {
          this.name = name;
          this.index = index;
      }
	  
	  
      public static String getName(String index) {
          for (Formal c : Formal.values()) {
              if (c.getIndex().equals(index)) {
                  return c.name;
              }
          }
          return null;
      }
      
      public static String getIndex(String name) {
      	for (Formal c : Formal.values()) {
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
		 for (Formal c : Formal.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	} 
}
