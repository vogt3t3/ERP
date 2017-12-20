package com.aiiju.util.enums;

/**
 * @ClassName: FeeTypeEnum
 * @Description: 报销类型枚举类
 * @author 哪吒
 * @date 2016年8月28日 上午9:39:51
 * 
 */
public enum FeeTypeEnum {

	EMPTY(0, "无"), ENTERTAINMENT_COST(1, "招待费"), TRANSPORTATION_COST(2, "交通费"), ACCOMMODATION_COST(
			3, "住宿费"), ADMINISTRATIVE_COST(4, "办公费"), COMMUNICATION_COST(5,
			"通讯费"), SUBSIDY(6, "补贴"), OTHER(7, "其他");

	private final Integer value;

	private final String desc;

	private FeeTypeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static FeeTypeEnum getDescOfValue(Integer value) {
		FeeTypeEnum result = FeeTypeEnum.EMPTY;
		for (FeeTypeEnum e : FeeTypeEnum.values()) {
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

	public static void main(String[] args) {
		System.out.println(FeeTypeEnum.getDescOfValue(9).getDesc());

	}

}
