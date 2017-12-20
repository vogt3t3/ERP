package com.aiiju.util.enums;

/**
 * @ClassName: StepEnum
 * @Description: flow_info表中的状态类型枚举类
 * @author 哪吒
 * @date 2016年8月28日 上午10:09:40
 * 
 */
public enum StepEnum {

	PENDING(0, "待处理"), PROCESSING(1, "处理中"), AGREE(2, "已同意"), DISAGREE(3, "已拒绝");

	private final Integer value;

	private final String desc;

	private StepEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static StepEnum getDescOfValue(Integer value) {
		StepEnum result = StepEnum.PENDING;
		for (StepEnum e : StepEnum.values()) {
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
