package com.ajy.web.job;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.workStage.SignInRulePojo;
import com.aiiju.service.signRule.ISignInOutService;
import com.aiiju.util.common.Constant;
import com.aiiju.util.common.DateUtil;

/**
 * 
 * @ClassName: MessagePushServiceJob
 * @Description:考勤签到签退定时器
 * @author 琪琪
 * @date 2016年9月9日 上午11:02:23 
 *
 */
@Service("messagePushServiceJob")
public class MessagePushServiceJob {
	private static Logger logger=Logger.getLogger(MessagePushServiceJob.class);
	@Resource
	private ISignInOutService signInOutService;
	/**
	 * 查询开启签到规则下用户，并推送考勤消息
	 */
	int i=1;
	public void signInOutByParams(){
		//获取当前系统时间
		Date curDate=new Date();
		//获取当前是星期几
		int weekDay=DateUtil.getWeekToNum(curDate);
		
		//查询开启的考勤规则,并且符合当前星期的
		List<SignInRulePojo> ruleList=signInOutService.getSignRuleByParams(weekDay);
		System.out.println("查询规则getSignRuleByParams次数"+i);
		i++;
		if(ruleList!=null&&!ruleList.isEmpty()){
			for(SignInRulePojo rule:ruleList){
				
				//上班签到
				if(rule.getIsSignInAlert()==Constant.NUM_ONE){
					//上班签到时间
					String inTime=rule.getSignInTime();
					//提前提醒时间
					int alertTime=rule.getSignInAlertTime();
					//当前时间大于等于inTime+beforeInTime,提醒
					try {
						int signInflag=DateUtil.compareTimeByCondition(alertTime,inTime);
						if(signInflag==Constant.NUM_ONE){
							//获取userId进行提醒
							signInOutService.sendSpecialUserByRuleId(rule.getId(),alertTime/60,Constant.NUM_ZERO);
						}
					} catch (ParseException e) {
						logger.equals("上班签到时，比较当前时间和提醒时间出错，compareTimeByCondition方法"+e.getMessage());
						e.printStackTrace();
					}
				}
				//下班签退
				if(rule.getIsSignOutAlert()==Constant.NUM_ONE){
					//班签退时间
					String outTime=rule.getSignOutTime();
					//提前提醒时间
					int alertTime=rule.getSignOutAlertTime();
					//当前时间大于等于inTime+beforeInTime,提醒
					try {
						int signOutflag=DateUtil.compareTimeByCondition(alertTime,outTime);
						if(signOutflag==Constant.NUM_ONE){
							//获取userId进行提醒
							signInOutService.sendSpecialUserByRuleId(rule.getId(),alertTime/60,Constant.NUM_ONE);
						}
					} catch (ParseException e) {
						logger.equals("下班签退时，比较当前时间和提醒时间出错，compareTimeByCondition方法"+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}

}
