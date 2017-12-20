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

public enum DutyModelEnum {

	NAME("name", "职务名称"),
	DUTYTYPE("dutyTypeId", "职务类型"),
	DUTYLEVELBOTTOM("dutyLevelBottomId", "职等下限"),
	DUTYLEVELTOP("dutyLevelTopId", "职等上限"),
	REMARK("remark", "描述说明"),
	DUTYNO("dutyNo", "职务序号");
	
	
	private final String value;
	
	private final String desc;
	
	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	private DutyModelEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	
	public static DutyModelEnum getValue(String desc) {
		DutyModelEnum result = null;
        for (DutyModelEnum e: DutyModelEnum.values()) {
            if (e.getDesc().equals(desc)) {
                return e;
            }
        }
        
        return result;
    }
	
	public static List<String> getModelHeader() {
		List<String> list = new ArrayList<String>();
		for (DutyModelEnum e : DutyModelEnum.values()) {
			list.add(e.getDesc());
		}
		return list;
	}
}
