package com.aiiju.util.enums;
/**
 * 
 * @ClassName: ExcelTemplateEnum 
 * @Description: EXCEL模板枚举类
 * @author BZ
 * @date 2016-12-1 17:07:41
 */

public enum ExcelTemplateEnum {

	EMPLOYEE_TEMPLATE("employee", "员工模板"),
	SALARY_TEMPLATE("salary", "薪资模板"),
	POSITION_TEMPLATE("position", "职位模板"),
	DUTY_TEMPLATE("duty", "职务模板"),
	DEPARTMENT_TEMPLATE("department", "组织模板");
	
	private final String value;
	
	private final String desc;
	
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
	private ExcelTemplateEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	public static String getName(String value) {
        for (ExcelTemplateEnum e : ExcelTemplateEnum.values()) {
            if (e.getValue().equals(value)) {
                return e.desc;
            }
        }
        return null;
    }
}
