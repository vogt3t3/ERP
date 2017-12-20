package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

public enum LogInError {

	invite("x","邀请员工"),
	negative("-1","员工号已存在"),
	zero("1","添加成功"),
	one("001","时间错误"),
	two("002","签名错误"),
	three("003","方法or签名错误"),
	four("004","手机存在"),
	five("005","逻辑错误"),
	six("006","数据库异常");
	
	private String name;
	private String index;
	
	private LogInError(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (LogInError c : LogInError.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    public static String getIndex(String name) {
      	for (LogInError c : LogInError.values()) {
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
		System.out.println(getName("001"));
	}
}
