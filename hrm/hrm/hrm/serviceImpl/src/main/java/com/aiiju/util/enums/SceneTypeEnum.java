package com.aiiju.util.enums;
/**
 * 工作台模块：场景类型枚举常量
 * @author 小辉
 *
 */
public enum SceneTypeEnum {
	DAYPAPER(1, "日报"), 
	TASK(2, "任务"), 
	SHENPI(3, "审批"),
	NOTICE(4, "公告"),
	REPLY(5, "回复");
	
	private final int value;
	private final String desc;

	private SceneTypeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	
	public static String getValue(Integer id) {
		String result = null;
        for (SceneTypeEnum e: SceneTypeEnum.values()) {
            if (e.getValue()==id.intValue()) {
                return e.getDesc();
            }
        }
        
        return result;
    }
}
