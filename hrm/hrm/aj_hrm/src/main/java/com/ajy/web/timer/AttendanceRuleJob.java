package com.ajy.web.timer;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aiiju.service.attendance.IAttendanceRuleService;
import com.aiiju.util.common.DateUtil;

/**
 * '修改考勤规则为生效状态'定时器：
 * 
 * 每天凌晨 1点 执行一次,将昨天修改的考勤规则的使用状态更改为生效
 * @author 小辉
 * @date 2017年5月25日 上午10:33:25 
 *
 */
@Controller("attendanceRuleJob")
public class AttendanceRuleJob {
	
	private static Logger logger = LoggerFactory.getLogger(AttendanceRuleJob.class);
	@Autowired
	private IAttendanceRuleService attendanceService;
	
	public void changeRuleToUsed(){
		Date systemTime = new Date();
		logger.info("=========定时器修改考勤规则的使用状态为生效begin:"+DateUtil.formatDate(systemTime, "yyyy-MM-dd HH:mm:ss")+"=========");
		attendanceService.updateIsUsed();
		logger.info("=========定时器修改考勤规则的使用状态为生效end:"+DateUtil.formatDate(systemTime, "yyyy-MM-dd HH:mm:ss")+"=========");
	}
}
