package com.aiiju.util.enums;

/**
 * 考勤签到结果
 * @author 小辉
 *
 * 2017年5月17日
 */
public enum SignResult {
	NOSIGN((byte)0, "未签"), 
	NORMAL((byte)1, "正常"), 
	LATE((byte)2, "迟到"), 
	EARLY((byte)3,"早退"),
	OVERTIME((byte)4,"加班");

	private final byte value;
	private final String desc;

	private SignResult(byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
