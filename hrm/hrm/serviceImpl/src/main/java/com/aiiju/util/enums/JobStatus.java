package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 在职状态
 * 
 * @author 维斯
 *	2016年10月31日	 下午5:45:41
 */
public enum JobStatus {

	
	JOB_ONE("1","在职"),
	JOB_FIVE("2","离职");
	
	private String name;
	private String index;
	
	private JobStatus(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (JobStatus c : JobStatus.values()) {
            if (c.getIndex().equals(index) ) {
                return c.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
      	for (JobStatus c : JobStatus.values()) {
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
		 for (JobStatus c : JobStatus.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}
}
