package com.aiiju.util.enums;

/**
 * 签到类型标记
 * @author 小辉
 *
 * 2017年5月16日
 */
public enum SignTypeMark {
	SIGNIN("in", "签到"), 
	SIGNOUT("out", "签退"), 
	SIGNIN2("in2", "第二个签到"), 
	SIGNOUT2("out2", "第二个签退"),
	SIGNIN3("in3", "第三个签到"), 
	SIGNOUT3("out3", "第三个签退");

	private final String value;
	private final String desc;

	private SignTypeMark(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
