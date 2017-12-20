package com.aiiju.constant;
/** 
 * @ClassName: InfoGroupEnum 
 * @Description: 信息集 ---枚举类
 * @author BZ 
 * @date 2016年10月18日 上午10:09:40 
 *  
 */
public enum InfoGroupEnum {
    
	ALL(0, "全部"),
    ORGINFO(1, "组织信息"),
    DUTYINFO(2, "职务信息"),
    POSITIONINFO(3, "职位信息"),
    EMPLOYEEINFO(4, "员工信息");
    
    private final Integer value;
    
    private final String desc;
    
    private InfoGroupEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static InfoGroupEnum getDescOfValue(Integer value) {
    	InfoGroupEnum result = InfoGroupEnum.ALL;
        for (InfoGroupEnum e: InfoGroupEnum.values()) {
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
