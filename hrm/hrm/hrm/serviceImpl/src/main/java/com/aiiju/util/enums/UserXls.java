package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;
/**
 * 人员模板
 * @author 维斯
 *
 * 2016年11月22日   下午3:13:18
 */
public enum UserXls {

	names("name","姓名"),
	certificateType("certificateTypeName","证件类型"),
	certificateNo("certificateNo","证件号码"),
	sexName("sexName","性别"),
	nation("nationName","民族"),
	status("status","状态"),
	deptName("deptName","部门"),
	positionName("positionName","职位"),
	phone("phone","联系方式"),
	regularDate("regular","拟转正日期"),
	marital("marital","婚姻状况"),
	formalFace("formal","政治面貌"),
	userNo("userNo","员工编号"),
	birdthAddress("birdthAddress","出生地"),
	homeAddress("homeAddress","家庭住址"),
	email("email","邮箱");
	
	
	private String name;
	private String index;
	private UserXls(String index, String name) {
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (UserXls c : UserXls.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    public static String getIndex(String name) {
    	for (UserXls c : UserXls.values()) {
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
		 for (UserXls c : UserXls.values()) {
			 list.add(c.getName());
	        }
		return list;
		
	}
	public static List<String> getAllVal(){
		List<String> list = new ArrayList<String>();
		for (UserXls c : UserXls.values()) {
			list.add(c.getIndex());
		}
		return list;
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(getAll());
		System.out.println(getAllVal());
	}
	
}
