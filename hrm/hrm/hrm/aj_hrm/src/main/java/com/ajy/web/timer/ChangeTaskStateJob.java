package com.ajy.web.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aiiju.bean.oa.workStage.TaskInfo;
import com.aiiju.service.workStage.ITaskInfoService;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.enums.TaskStep;

/**
 * '修改任务状态'定时器：
 * 此任务查询的开始时间默认设定为系统时间的前5天时间,
 * 查询的数据列表是从设定的开始时间到系统当前时间,
 * 一旦结束时间满足了条件,即会自动执行修改状态操作。
 * @author 小辉
 * @date 2017年3月24日 上午11:33:25 
 *
 */
@Controller("changeTaskStateJob")
public class ChangeTaskStateJob {
	
	private static Logger logger = LoggerFactory.getLogger(ChangeTaskStateJob.class);
	@Autowired
	private ITaskInfoService taskInfoService;
	
	public void changeTaskState(){
		logger.info("=========定时器修改任务的状态为已过期begin:"+DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"=========");
		Map<String,Object> map = new HashMap<String,Object>();
		//自定义查询起始时间,目前暂设定为系统时间的前5天时间    
		Calendar calendar = Calendar.getInstance(); 
		//得到前5天的时间 
	    calendar.add(Calendar.DAY_OF_MONTH, -5);		    
		map.put("startTime", calendar.getTime());
		Date systemTime = new Date();
		map.put("endTime", systemTime);
		map.put("step", TaskStep.PROCESSING.getValue());//只查询处理中的
		List<TaskInfo> list = taskInfoService.getTaskInfoListByMap(map);
		if(list !=null && list.size()>0){
			try {
				for (TaskInfo taskInfo : list) {
					//任务结束时间
					String endTime = taskInfo.getExpireDate();
					String currTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
					if(DateUtil.compareDate(currTime, endTime)){
						taskInfo.setStep(Integer.parseInt(TaskStep.OVERDUE.getValue()));
						Map<String,Object> updateMap = new HashMap<>();
						updateMap.put("id",taskInfo.getId());
						updateMap.put("step",Integer.parseInt(TaskStep.OVERDUE.getValue()));
						taskInfoService.updateTaskStep(updateMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("定时器修改任务的状态为已过期发生异常:"+new Date());
			}
		}
		logger.info("=========定时器修改任务的状态为已过期END:"+DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"=========");
	}
}
