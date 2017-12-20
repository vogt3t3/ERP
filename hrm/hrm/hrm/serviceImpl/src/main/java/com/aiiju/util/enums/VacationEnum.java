package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请假常量类型
 * @author 琪琪
 * @date 2017-01-09 11:11:11
 */

public enum VacationEnum {
	ZERO("0", "选择事项"),
	ONE("1", "事假"),
	TWO("2", "病假"),
	THREE("3", "调休"),
	FOUR("4", "年休假"),
	FIVE("5", "婚假"),
	SIX("6", "生育假"),
	SEVEN("7","丧假"),
    EIGHT("8","加班"),
    NINE("9","外勤"),
    TEN("10","出差"),
    EVELEN("11","其他");
    
	
	private final String id;
	
	private final String name;
	


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private VacationEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
	
	public static String getValue(String id) {
		String result = null;
        for (VacationEnum e: VacationEnum.values()) {
            if (e.getId().equals(id)) {
                return e.getName();
            }
        }
        
        return result;
    }
	
	public static List<Map<String,String>> getVacationEnumLists() {
		List<Map<String, String>> result=new ArrayList<Map<String,String>>();
		for (VacationEnum e : VacationEnum.values()) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("id",e.getId());
			map.put("name",e.getName());
			result.add(map);
		}
		return result;
	}
}
