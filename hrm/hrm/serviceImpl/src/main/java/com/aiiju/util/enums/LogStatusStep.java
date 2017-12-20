package com.aiiju.util.enums;

/**
 * 任务详情中过程的状态
 * @author 琦玉
 *
 * 2017年4月13日
 */
public enum LogStatusStep {
	PENDING("0", "待处理"), 
	PROCESSING("1", "处理中"), 
	COMPLETE("2", "已完成"), 
	DISAGREE("3","已拒绝"),
	FORWARDED("4","已转发"),
	RESCINDED("5", "已撤销"),
	OVERDUE("6","已过期"),
	START("7","发起者");
	
	private final String value;
	private final String desc;

	private LogStatusStep(String value, String desc) {
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
