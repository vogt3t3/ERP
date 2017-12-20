package com.aiiju.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.schedule.Schedule;
import com.aiiju.bean.oa.workStage.Announcement;
import com.aiiju.bean.oa.workStage.TaskInfo;
import com.aiiju.dao.oa.schedule.IScheduleDao;
import com.aiiju.dao.oa.workStage.IAnnouncementDao;
import com.aiiju.dao.oa.workStage.IHistoryLogDao;
import com.aiiju.service.IHomeService;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;

/**
 * 
 * @ClassName: HomeServiceImpl 
 * @Description: 首页业务逻辑实现层
 * @author 哪吒 
 * @date 2016年12月3日 上午11:44:40 
 *
 */

@Service("homeService")
public class HomeServiceImpl implements IHomeService {
	
	@Autowired
	private IScheduleDao scheduleDao;
	@Autowired
	private IHistoryLogDao historyLogDao;
	
	@Autowired
	private IAnnouncementDao announcementDao;

	@Override
	public Map<String, Object> addSchedule(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 根据公司ID和用户ID查询待办事项总数
		int count = 0;
		try {
			count = scheduleDao.getScheduleCountByUser(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取用户待办事项总数失败");
		}
		
		if (count >= 0 && count < 3) { // 可以新增
			try {
				Schedule schedule = new Schedule();
				schedule.setCompanyId(Long.valueOf(params.get("companyId").toString()));
				schedule.setUserId(Long.valueOf(params.get("userId").toString()));
				schedule.setContent(params.get("content").toString());
				scheduleDao.insertSchedule(schedule);
				resultMap.put("id", schedule.getId());
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "新增用户待办事项失败");
			}
		} else {
			throw new BizException(BizExceptionMessage.OVER_SCHEDULE_LIMIT);
		}
 
		return resultMap;
	}

	@Override
	public Map<String, Object> getScheduleListByParam(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Schedule> schedules = null;
		try {
			schedules = scheduleDao.getScheduleListByUser(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取用户待办事项列表失败");
		}
		
		resultMap.put("companyId", params.get("companyId"));
		resultMap.put("userId", params.get("userId"));
		resultMap.put("result", schedules);
		return resultMap;
	}

	@Override
	public Map<String, Object> delScheduleById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			scheduleDao.delScheduleById(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "删除用户待办事项失败");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> updateScheduleById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			scheduleDao.updateScheduleById(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "更新用户待办事项失败");
		}
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> comeHomePage(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			//获取工作汇报未读数和待处理统计数
			Map<String, Object> incidentNum = historyLogDao.getIncidentNum(params);
			resultMap.put("incidentNum", incidentNum);
			
			//获得工作汇报列表，先设定的是最新的3条
			params.put("recipient", params.get("userId"));
			params.put("startRow", 0);
			params.put("pageSize", 3);
			List<Announcement> announcements = announcementDao.getAnnouncements(params);
			for (Announcement announcement : announcements) {
				announcement.setCreateTime(announcement.getCreateTime().substring(5,10));
				announcement.setUpdateTime(announcement.getUpdateTime().substring(5,10));
			}
			
			
			
			resultMap.put("announcements", announcements);
			
			//给前端显示的日期和星期几
 
			//今天
			resultMap.put("today",DateUtil.getCurDay());
			resultMap.put("todayWeek",DateUtil.getNewWeek(new Date(),new Locale("zh", "CN")));
			
			//昨天
			Date yesterday = org.apache.commons.lang.time.DateUtils.addDays(new Date(), -1);

			resultMap.put("yesterday",DateUtil.formatDate(yesterday,"yyyy-MM-dd"));
			resultMap.put("yesterdayWeek",DateUtil.getNewWeek(yesterday,new Locale("zh", "CN")));
			
			
			
			//问候语
			String	greetings=null;
			Integer hours =Integer.valueOf(new SimpleDateFormat("HH").format(new Date()));
			if(hours>=0&&hours<12){
				greetings=	PropertiesUtil.getPropertyByKey("hrm.home.morning");
			}else if (hours>=12&&hours<18) {
				greetings=	PropertiesUtil.getPropertyByKey("hrm.home.nooning");
			}else{
				greetings=PropertiesUtil.getPropertyByKey("hrm.home.evening");
			}
		
			resultMap.put("greetings",greetings);
		} catch (Exception e) {
			throw new BizException("400", "主页查询失败");
		}
		
		return resultMap;
	}
	
	
	@Override
	public void dealSchedule(Map<String, Object> params) {
		String content =params.get("content")==null?"":params.get("content").toString();
		String timing =params.get("timing")==null?null:params.get("timing").toString();
		String id =params.get("id")==null?null:params.get("id").toString();
		
		
		
		
		//当内容为NULL，并有主键ID,删除记录
		if((content==null||StringUtils.isEmpty(content))&&!StringUtils.isEmpty(id)){
				try {
					scheduleDao.delScheduleById(params);
				} catch (Exception e) {
					throw new BizException(BizExceptionMessage.DB_ERROR, "删除用户待办事项失败");
				}
		
		//当内容不为NULL，并有主键ID,修改记录
		}else if (!content.trim().equals("")&&!StringUtils.isEmpty(id)){
			try {
				scheduleDao.updateScheduleById(params);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "更新用户待办事项失败");
			}
			
			//当内容不为NULL，并没有主键ID,新增记录	
		}else if (!content.trim().equals("")&&StringUtils.isEmpty(id)){
			try {
				if(StringUtils.isEmpty(timing)){
					throw new BizException(BizExceptionMessage.DB_ERROR, "没有指定时间");
				}
				
				Schedule schedule = new Schedule();
				schedule.setCompanyId(Long.valueOf(params.get("companyId").toString()));
				schedule.setUserId(Long.valueOf(params.get("userId").toString()));
				schedule.setContent(content);
				schedule.setTiming(timing);
				
				scheduleDao.insertSchedule(schedule);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "新增用户待办事项失败");
			}
		}
		
	
	}
	
	@Override
	public Map<String, Object> getSchedules(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		List<List<Map<String, Object>>> resultList = new ArrayList();
		
		String chooseDate =params.get("chooseDate")==null?null:params.get("chooseDate").toString();
		
		
		//当没有选择指定年月，默认为当前年月
		if(chooseDate==null){
	        chooseDate = DateUtil.getCurDate("yyyy-MM");
	        params.put("chooseDate",chooseDate);
		}
		
		List<Schedule> scheduleList = scheduleDao.getScheduleListByUser(params);
		
		//把每个事件的日作为KEY放入MAP中
		Map<Integer,Schedule> scheduleMap = new  HashMap<>();
		if(scheduleList!=null&&scheduleList.size()>0){
			for (Schedule schedule : scheduleList) {
				try {
					Date parse =	new SimpleDateFormat("yyyy-MM-dd").parse(schedule.getTiming());
					Calendar c = Calendar.getInstance();
					c.setTime(parse);
					scheduleMap.put((c.get(Calendar.DAY_OF_MONTH)), schedule);
				} catch (ParseException e) {
					throw new  BizException("400", "日期转化错误");
				}
			}
		}
		
		try {
			Date date = new SimpleDateFormat("yyyy-MM").parse(chooseDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DATE, 1);//把日期设置为当月第一天 
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);//得到本月第一天是星期几 (星期日-星期六 为1-7）
			dayOfWeek=dayOfWeek-1;
			if(dayOfWeek==0) dayOfWeek =7;//转化成(星期一-星期日 为1-7）
			
		    c.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		    int maxDate = c.get(Calendar.DATE);//得到最大天数（本月有几天）
		    
		    
		    c.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		    c.add(Calendar.DATE, -1);//取到上个月最后一天
		    int lastMaxDate = c.get(Calendar.DATE);
		    //本月的日
		    int day =1;
		    
		    //按前段返回值展示，以星期一为开头，不是存NULL，每个星期为1个数组；月末也按此规则
		    //每月的1号可能不是星期一，手动填充上个月月末开始，并添加非本月标记
		   int  num =  dayOfWeek-1;
		    List<Map<String, Object>> firstList = new ArrayList<>();
		    for(int x=0;x<num;x++){
		    	Map<String, Object> lastMouthMap = new HashMap<>();
		    	lastMouthMap.put("day", lastMaxDate--);	
		    	lastMouthMap.put("notMouth", 1);	
		    	firstList.add(0,lastMouthMap);
		    }
		    
		    //判断搜索的是否是当前日，如果是加上标记，让前段展示不同的样式
		    boolean marker =false;
		    int today =0;
		    if(chooseDate.equals(DateUtil.getCurDate("yyyy-MM"))){
		    	marker= true;
		    	today =DateUtil.getToDay();
		    }
		    
		    
		    //先存储第一个星期数组，并记录该数组到本月几号
		    for(int y=7;y>num;y--){
		    	Map<String, Object> firstMap = new HashMap<>();
	    			firstMap.put("day", day);	
			    	firstMap.put("schedule", scheduleMap.get(day) );
			    	if(marker){
			    		if(day==today){
			    			firstMap.put("toDay", 1 );	
			    		}
			    	}
			    	firstList.add(firstMap);
			    	day++;
		    }
		    
		    resultList.add(firstList);
		    
		    for(;day<=maxDate;day++){
		    	List<Map<String, Object>> weekList =new ArrayList<>();
		    	for(int z=1;z<=7;z++){
		    		Map<String, Object> listMap=new HashMap<>();

		    		 listMap.put("day", day);	
		    		 listMap.put("schedule", scheduleMap.get(day));
		    		 if(marker){
				    		if(day==today){
				    			listMap.put("toDay", 1 );	
				    		}
				    	}
		    		 weekList.add(listMap);
		    		 if(day==maxDate||z==7){
		    			 break;
		    		 }
		    		 day++;
		    	}
		    	 resultList.add(weekList);
		    }
		    
		    //最后一个星期数组长度可能没有7，手动填充下个月从1号开始，并添加非本月标记
		    int nextMouthDay =1 ;
		    List<Map<String, Object>> list = resultList.get(resultList.size()-1);
		    int size = list.size();		    
		    for(;size<7;size++){
		    	Map<String, Object> nextMouthMap = new HashMap<>();
		    	nextMouthMap.put("day", nextMouthDay++);	
		    	nextMouthMap.put("notMouth", 1);	
		    	list.add(nextMouthMap);
		    }
		    
		} catch (ParseException e) {
			throw new  BizException("400", "获得待处理事项列表失败");
		}
		resultMap.put("schedules", resultList);
		
		
		//前段需要把搜索的年月分开在返回
		String [] split =chooseDate.split("-");
		resultMap.put("year", split[0]);
		resultMap.put("mouth", split[1]);
		
		return resultMap;
		
	}
	
	
	@Override
	public Map<String, Object> workStageSuperscript(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			//获取工作汇报未读数和待处理统计数
			Map<String, Object> scenesNum = historyLogDao.getScenesNum(params);
			resultMap.put("scenesNum", scenesNum);	
		}catch (Exception e) {
			throw new  BizException("400", "获得待处理事项列表失败");
		}
		
		
		return resultMap;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws ParseException {
		TaskInfo t = new TaskInfo();
		t.setId(1);
		List l = new ArrayList<>();
		l.add(t);
		l.add(t);
		l.add(t);
		l.add(t);
		TaskInfo f= t;
		f.setId(2);;
		System.out.println(f);
		System.out.println(t);
		System.out.println(l.size());
	}
	
	
	
}
