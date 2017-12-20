package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: DutyModelEnum 
 * @Description: 职务模版枚举类
 * @author 哪吒 
 * @date 2016年11月3日 下午2:40:13 
 *
 */

public enum PositionModelEnum {

	NAME("name", "职位名称"),
	DUTY("dutyId", "所属职务"),
	DEPT("deptId", "所属组织"),
	POSITIONNO("positionNo", "职位序号"),
	REMARK("remark", "描述说明");
	
	private final String value;
	
	private final String desc;
	
	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	private PositionModelEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	
	public static PositionModelEnum getValue(String desc) {
		PositionModelEnum result = null;
        for (PositionModelEnum e: PositionModelEnum.values()) {
            if (e.getDesc().equals(desc)) {
                return e;
            }
        }
        
        return result;
    }
	
	public static List<String> getModelHeader() {
		List<String> list = new ArrayList<String>();
		for (PositionModelEnum e : PositionModelEnum.values()) {
			list.add(e.getDesc());
		}
		return list;
	}
}
