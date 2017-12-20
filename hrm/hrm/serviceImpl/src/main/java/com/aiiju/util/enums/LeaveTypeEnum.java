package com.aiiju.util.enums;

/**
 * @ClassName: FeeTypeEnum
 * @Description: 请假类型枚举类
 * @author 太一
 * @date 2016-8-28 23:24:44
 * 
 */
public enum LeaveTypeEnum {

	SELECT_LEAVE(0, "选择事项"), PERSONAL_AFFIAIRS_LEAVE(1, "事假"), SICK_LEAVE(2,
			"病假"), DAYS_OFF_LEAVE(3, "调休"), ANNUAL_LEAVE(4, "年休假"), MARITAL_LEAVE(
			5, "婚假"), BIRTH_VACATION(6, "生育假"), FUNERAL_LEAVE(7, "丧假"), OVERTIME_LEAVE(
			8, "加班"), OUTDOOR_LEAVE(9, "外勤"), EVECTION_LEAVE(10, "出差"), OTHER_LEAVE(
			11, "其他");

	private final Integer value;

	private final String desc;

	private LeaveTypeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static LeaveTypeEnum getDescOfValue(Integer value) {
		LeaveTypeEnum result = LeaveTypeEnum.SELECT_LEAVE;
		for (LeaveTypeEnum e : LeaveTypeEnum.values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}

		return result;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
