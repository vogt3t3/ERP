package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: DutyModelEnum 
 * @Description: 部门模版枚举类
 * @author 太一
 * @date 2016-11-11 17:07:41
 */

public enum DeptModelEnum {

	NAME("name", "部门名称"),
	PARENTDEPTNAME("parentDeptName", "上级部门"),
	SHORTNAME("shortName", "部门简称"),
	DEPTNO("deptNo", "部门序号"),
	REMARK("remark", "描述说明");
	
	private final String value;
	
	private final String desc;
	
	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	private DeptModelEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	
	public static DeptModelEnum getValue(String desc) {
		DeptModelEnum result = null;
        for (DeptModelEnum e: DeptModelEnum.values()) {
            if (e.getDesc().equals(desc)) {
                return e;
            }
        }
        
        return result;
    }
	
	public static List<String> getModelHeader() {
		List<String> list = new ArrayList<String>();
		for (DeptModelEnum e : DeptModelEnum.values()) {
			list.add(e.getDesc());
		}
		return list;
	}
}
