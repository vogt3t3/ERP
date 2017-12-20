package com.aiiju.util.enums;

/**
 * 任务状态
 * @author 小辉
 *
 * 2017年3月23日
 */
public enum TaskStep {
	PENDING("0", "待处理"), 
	PROCESSING("1", "处理中"), 
	COMPLETE("2", "已完成"), 
	DISAGREE("3", "已撤销"),
	OVERDUE("4","已过期");

	private final String value;
	private final String desc;

	private TaskStep(String value, String desc) {
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
