package com.aiiju.service.signRule;
import java.util.List;

import com.aiiju.bean.oa.workStage.SignInRulePojo;

/**
 * 
 * @ClassName: ISignInOutService
 * @Description: 考勤接口
 * @author 琪琪
 * @date 2016年9月9日 上午11:02:23 
 *
 */
public interface ISignInOutService {
	/**
	 * 查看上下班提醒开启的规则
	 * @param weekDay 当前星期几
	 * @return
	 */
	public List<SignInRulePojo> getSignRuleByParams(int weekDay);
	/**
	 * 根据ruleId推送考勤消息的userIds
	 * @param ruleId
	 * @param alertTime 分钟
	 * @param type
	 * @return
	 */
	public void sendSpecialUserByRuleId(int ruleId,int alertTime,int type);

}
