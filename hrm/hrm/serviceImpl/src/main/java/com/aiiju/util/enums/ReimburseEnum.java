package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报销常量类型
 * @author 琪琪
 * @date 2017-01-09 11:11:11
 */

public enum ReimburseEnum {
	ZERO("0","选择类型"),
	ONE("1", "招待费"),
	TWO("2", "交通费"),
	THREE("3", "住宿费"),
	FOUR("4", "办公费"),
	FIVE("5", "通讯费"),
	SIX("6", "补贴费"),
	SEVEN("7", "其它");
	
	private final String id;
	
	private final String name;
	


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private ReimburseEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
	
	public static String getValue(String id) {
		String result = null;
        for (ReimburseEnum e: ReimburseEnum.values()) {
            if (e.getId().equals(id)) {
                return e.getName();
            }
        }
        
        return result;
    }
	
	public static List<Map<String, String>> getReimburseEnumList() {
		List<Map<String, String>> result=new ArrayList<Map<String,String>>();
		for (ReimburseEnum e : ReimburseEnum.values()) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("id",e.getId());
			map.put("name",e.getName());
			result.add(map);
		}
		return result;
	}
}
