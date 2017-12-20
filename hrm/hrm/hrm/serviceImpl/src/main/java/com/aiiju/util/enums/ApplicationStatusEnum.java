package com.aiiju.util.enums;

/**
 * @ClassName: StepEnum
 * @Description: 加入公司申请状态类型枚举类
 * @author 哪吒
 * @date 2016年8月28日 上午10:09:40
 * 
 */
public enum ApplicationStatusEnum {

	PENDING(1, "待审核"), AGREE(2, "已通过"), DISAGREE(3, "已拒绝");

	private final Integer value;

	private final String desc;

	private ApplicationStatusEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static ApplicationStatusEnum getDescOfValue(Integer value) {
		ApplicationStatusEnum result = ApplicationStatusEnum.PENDING;
		for (ApplicationStatusEnum e : ApplicationStatusEnum.values()) {
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
