package com.aiiju.util.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 薪资模板
 * @author 维斯
 *
 * 2016年11月22日   下午3:13:03
 */
public enum SalaryPay {

	
	STAFFNO("staffNo","工号"),
	NAME("name","名字"),
	EMAIL("email","邮箱"),
	PHONE("phone","手机"),
	DEPTNAME("deptName","部门"),
	RELEASETIME("releaseTime","发放月份"),
	BASICPAY("basicPay","基本工资"),
	PERCENTAGE("percentage","提成"),
	YEARENDBONUSES("yearEndBonuses","年终奖"),
	LEAVEDAYS("leaveDays","请假天数"),
	LEAVEDEDUCTMONEY("leaveDeductMoney","请假扣减"),
	OVERTIMEPAY("overtimePay","加班费"),
	ATTENDANCEDEDUCT("attendanceDeduct","考勤扣减"),
	BONUS("bonus","奖金"),
	WELFARE("welfare","福利"),
	TOTALDEDUCT("totalDeduct","工资扣减"),
	SOCIALSECURITYPAYS("socialSecurityPays","社保个人"),
	PROVIDENTFUNDPAY("providentFundPay","公积金个人"),
	WITHHOLDINGTAX("withholdingTax","代扣个税"),
	NEEDPAYMENT("needPayment","实发工资");

	private String key;
	private String val;
	
	
	private SalaryPay(String val, String key) {
        this.key = key;
        this.val = val;
    }
    public static String getName(String val) {
        for (SalaryPay c : SalaryPay.values()) {
            if (c.getVal().equals(val)) {
                return c.key;
            }
        }
        return null;
    }
    public static String getIndex(String key) {
    	for (SalaryPay c : SalaryPay.values()) {
    		if (c.getKey().equals(key)) {
    			return c.val;
    		}
    	}
    	return null;
    }

	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public static List<String> getAll(){
		List<String> list = new ArrayList<String>();
		 for (SalaryPay c : SalaryPay.values()) {
			 list.add(c.getKey());
	        }
		return list;
		
	}
	
	public static Map<String,String> getAllKV(){
		Map<String,String> map = new LinkedHashMap<String, String>();
		
		map.put(STAFFNO.val, STAFFNO.key);
		map.put(NAME.val, NAME.key);
		map.put(EMAIL.val, EMAIL.key);
		map.put(PHONE.val, PHONE.key);
		map.put(DEPTNAME.val, DEPTNAME.key);
		map.put(RELEASETIME.val, RELEASETIME.key);
		map.put(BASICPAY.val, BASICPAY.key);
		map.put(PERCENTAGE.val, PERCENTAGE.key);
		map.put(YEARENDBONUSES.val, YEARENDBONUSES.key);
		map.put(LEAVEDAYS.val, LEAVEDAYS.key);
		map.put(LEAVEDEDUCTMONEY.val, LEAVEDEDUCTMONEY.key);
		map.put(OVERTIMEPAY.val, OVERTIMEPAY.key);
		map.put(ATTENDANCEDEDUCT.val, ATTENDANCEDEDUCT.key);
		map.put(BONUS.val, BONUS.key);
		map.put(WELFARE.val, WELFARE.key);
		map.put(TOTALDEDUCT.val, TOTALDEDUCT.key);
		map.put(SOCIALSECURITYPAYS.val, SOCIALSECURITYPAYS.key);
		map.put(PROVIDENTFUNDPAY.val, PROVIDENTFUNDPAY.key);
		map.put(WITHHOLDINGTAX.val, WITHHOLDINGTAX.key);
		map.put(NEEDPAYMENT.val, NEEDPAYMENT.key);
		
		return map;
		
	}
    
	public static void main(String[] args)  {
        System.out.println(getAllKV());
		
	}
	
}
