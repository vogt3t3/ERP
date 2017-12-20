package com.aiiju.util.enums;

/**
 * @ClassName: UserApplyStepEnum
 * @Description: zds_user_apply表中的状态类型枚举类
 * @author 太一
 * @date 2016-9-23 16:02:05
 * 
 */
public enum UserApplyStepEnum {

	PENDING(0, "待审核"), AGREE(1, "已通过"), DISAGREE(2, "已拒绝"), CANCEL(3, "已取消");

	private final Integer value;

	private final String desc;

	private UserApplyStepEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static UserApplyStepEnum getDescOfValue(Integer value) {
		UserApplyStepEnum result = UserApplyStepEnum.PENDING;
		for (UserApplyStepEnum e : UserApplyStepEnum.values()) {
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
