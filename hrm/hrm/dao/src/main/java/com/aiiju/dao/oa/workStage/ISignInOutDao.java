package com.aiiju.dao.oa.workStage;
import java.util.List;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.workStage.SignInRulePojo;

public interface ISignInOutDao {
	/**
	 * 查看上下班提醒开启的规则
	 * @param weekDay 当前时间星期几
	 * @return
	 */
	public List<SignInRulePojo> getSignRuleByParams(int weekDay);
	/**
	 * 根据ruleId获取规则使用部门
	 * @param ruleId
	 * @return
	 */
	public List<Department> getDeptsByRuleId(int ruleId);
}
