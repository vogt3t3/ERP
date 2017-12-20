package com.aiiju.serviceImpl.workStage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.workStage.AttendancePojo;
import com.aiiju.bean.oa.workStage.AttendanceRuleForStat;
import com.aiiju.bean.oa.workStage.AttendanceRulePojo;
import com.aiiju.bean.oa.workStage.AttendanceStat;
import com.aiiju.dao.oa.workStage.IAttendanceDao;
import com.aiiju.service.workStage.IAttendanceService;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;

/**
 * 
 * @ClassName: AttendanceServiceImpl 
 * @Description: IAttendanceService 实现类
 * @author BZ
 * @date 2017年1月6日 上午11:02:53 
 *
 */
@Service("attendanceService")
public class AttendanceServiceImpl implements IAttendanceService {
	@Autowired
	private IAttendanceDao attendanceDao;

	@Override
	public Map<String, Object> getAttendanceByParams(Map<String, Object> params) {
		String startDate = params.get("startDate") == null ? null : params.get("startDate").toString();
		String endDate = params.get("endDate") == null ? null : params.get("endDate").toString();
		String pageNumStr = params.get("pageNum") == null ? null : params.get("pageNum").toString();
		String pageSizeStr = params.get("pageSize") == null ? null : params.get("pageSize").toString();
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || 
					StringUtils.isEmpty(pageNumStr) || StringUtils.isEmpty(pageSizeStr)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Integer num = attendanceDao.getAttendanceStatCount(params);
		int pageNum=Integer.parseInt(pageNumStr);
		int pageSize=Integer.parseInt(pageSizeStr);
		Pagination pt=new Pagination(num,pageNum,pageSize);
		params.put("pageSize", pageSize+"");
		params.put("startRow", pt.getStartPos()+"");
		
		//获取部分统计数据---除了‘上班未打卡’数据
		List<AttendanceStat> statList = attendanceDao.getAttendanceStat(params);
		
		//获取所选部门及用户的所有考勤规则
		List<AttendanceRuleForStat> ruleList = attendanceDao.getUserRuleList(params);
		
		Map<String,Object> resultMap = getAttendanceList(params,statList, ruleList,1);
		
		@SuppressWarnings("unchecked")
		List<AttendancePojo> rtnList = (List<AttendancePojo>) resultMap.get("rtnList");
		
		//返回前端结果集
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("result", rtnList);
		map.put("page", pt);
		return map;
	}
	@Override
	public HSSFWorkbook exportAttendanceExcel(Map<String, Object> params){
		//存放原始params，留做传给导出excel方法
		Map<String, Object> tempparams= new HashMap<String, Object>();
		tempparams.put("send_to_dept", params.get("send_to_dept"));
		tempparams.put("send_to_person", params.get("send_to_person"));
		tempparams.put("startDate", params.get("startDate"));
		tempparams.put("endDate", params.get("endDate"));
		
		//获取所选部门及用户的所有考勤规则
//		Map<Integer, AttendanceRulePojo> ruleMap = this.getRuleMap(params);
//		Map<String,Object> resultMap = getAttendanceList(params, ruleMap,2);
//		@SuppressWarnings("unchecked")
//		List<AttendancePojo> rtnList = (List<AttendancePojo>) resultMap.get("rtnList");
//		@SuppressWarnings("unchecked")
//		List<AttendancePojo> rtnDetailList = (List<AttendancePojo>) resultMap.get("rtnDetailList");
//		@SuppressWarnings("rawtypes")
//		List[] listArray=new List[]{rtnList,rtnDetailList};
//		//考勤统计表头
//		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
//		headers.put("sort", "序号");
//		headers.put("empNo", "工号");
//		headers.put("userName", "姓名");
//		headers.put("deptName", "部门");
//		headers.put("workDays", "工作天数（天）");
//		headers.put("attendanceDays", "出勤天数（天）");
//		headers.put("lateDays", "迟到（天）");
//		headers.put("leftDays", "早退(天)");
//		headers.put("onDutyForgetDays", "上班忘打卡(天)");
//		headers.put("offDutyForgetDays", "下班忘打卡(天)");
//		headers.put("overTimeDays", "加班(天)");
//		headers.put("normalDays", "正常打卡(天)");
//		//考勤详情表头
//		LinkedHashMap<String, String> headers1 = new LinkedHashMap<String, String>();
//		String startDate = params.get("startDate").toString();
//		String endDate = params.get("endDate").toString();
//		int dateCount = DateUtil.getDateCount(startDate, endDate)+1;
//		headers1.put("sort", "序号");
//		headers1.put("empNo", "工号");
//		headers1.put("userName", "姓名");
//		headers1.put("deptName", "部门");
//		headers1.put("in_out", "时间");
//		for(int i=0;i<dateCount;i++){
//			headers1.put("signDate"+i, DateUtil.getAnyDate(startDate, i, "yyyy-MM-dd"));	
//		}
//		@SuppressWarnings("rawtypes")
//		LinkedHashMap[] headersArray=new LinkedHashMap[]{headers,headers1};
//		HSSFWorkbook book = ExcelUtil.attendanceExportExcel("考勤("+DateUtil.getCurDay()+")", headersArray, listArray, tempparams);
//		return book;
		return null;
	}
	
	
	/**
	 * 获取考勤数据
	 * @param params
	 * @param ruleMap
	 * @param exportFlag  1：查询    2：导出
	 * @return
	 */
	private Map<String,Object> getAttendanceList(Map<String,Object> params,List<AttendanceStat> statList,List<AttendanceRuleForStat> ruleList,int exportFlag) {
		
		
//		List<AttendancePojo> rtnList = new ArrayList<AttendancePojo>();
//		List<Map<String,Object>> rtnDetailList = new ArrayList<Map<String,Object>>();
//		Iterator<Integer> iterator = ruleMap.keySet().iterator();
//		
//		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
//		if(exportFlag == 2){
//			detailList = attendanceDao.getAttendanceDetailList(params);
//		}
//		
//		int sort = 1;//序号
//		for(AttendanceStat as : statList){
//			Integer workDays = this.getWorkDays(params, arp);//根据规则获取工作天数
//			
//			Integer lateDays = this.getDaysFromRsList(lateDaysList, key);
//			Integer leftDays = this.getDaysFromRsList(leftDaysList, key);
//			Integer overTimeDays = this.getDaysFromRsList(overTimeDaysList, key);
//			Integer offDutyForgetDays = this.getDaysFromRsList(offDutyForgetDaysList, key);
//			Integer onDutyForgetDays = workDays - this.getDaysFromRsList(OnDutyDaysList, key);
//			Integer normalDays = this.getDaysFromRsList(normalDaysList, key);
//			//出勤天数 = 下班忘打卡 + 迟到 + 正常打卡 + 加班
////			Integer attendanceDays = offDutyForgetDays + lateDays + normalDays + overTimeDays;
//			//出勤天数 = 上班打卡天数
//			Integer attendanceDays = this.getDaysFromRsList(OnDutyDaysList, key);
//			
//			attendancePojo.setSort(sort);
//			attendancePojo.setUserId(key);
//			attendancePojo.setEmpNo(arp.getEmpNo());
//			attendancePojo.setUserName(arp.getUserName());
//			attendancePojo.setDeptId(arp.getDeptId());//仔细去看这个其实没什么用
//			attendancePojo.setDeptName(arp.getDeptName());
//			attendancePojo.setWorkDays(workDays);//工作天数
//			attendancePojo.setAttendanceDays(attendanceDays);//出勤天数
//			attendancePojo.setLateDays(lateDays);//迟到天数
//			attendancePojo.setLeftDays(leftDays);//早退天数
//			attendancePojo.setOnDutyForgetDays(onDutyForgetDays);//上班忘打卡天数
//			attendancePojo.setOffDutyForgetDays(offDutyForgetDays);//下班忘打卡天数
//			attendancePojo.setOverTimeDays(overTimeDays);//加班天数
//			attendancePojo.setNormalDays(normalDays);//正常打卡天数
//			rtnList.add(attendancePojo);
//			
//			//如果是到处的话，需要将考勤详细信息查询出来并导出
//			if(exportFlag == 2){
//				Map<String,Object> resultMap = new HashMap<String,Object>();
//				resultMap.put("sort",sort);
//				resultMap.put("userId",key);
//				resultMap.put("empNo", arp.getEmpNo());
//				resultMap.put("userName",arp.getUserName());
//				resultMap.put("deptName",arp.getDeptName());
//				for(Map<String, Object> m : detailList){
//					int days = DateUtil.getDateCount(params.get("startDate").toString(), params.get("endDate").toString()) + 1;
//					for(int i = 0; i < days; i++){
//						String date = DateUtil.getAnyDate(params.get("startDate").toString(), i, "yyyy-MM-dd");
//						//String weekNum = DateUtil.getWeek((Date)DateUtil.transFormDate(date, "yyyy-MM-dd"));
//						//日期  resultMap.put("signDate"+i,date);
//						//星期几  resultMap.put("signTimeWeek"+i,weekNum);
//						if(Integer.valueOf(m.get("userId").toString()).equals(key)&&date.equals(m.get("signDate"))){
//							if("in".equals(m.get("signType").toString())){
//								//上班打卡时间及理由
//								resultMap.put("signInTime"+date,m.get("signTime"));
//								resultMap.put("signInDescribe"+date, m.get("describe"));
//							}else if("out".equals(m.get("signType").toString())){
//								//下班打卡时间及理由
//								resultMap.put("signOutTime"+date,m.get("signTime"));
//								resultMap.put("signOutDescribe"+date, m.get("describe"));
//							}
//						}
//					}
//				}
//				rtnDetailList.add(resultMap);
//			}
//			sort++;
//		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
//		returnMap.put("rtnList", rtnList);
//		returnMap.put("rtnDetailList", rtnDetailList);
		return returnMap;
	}
	
	/**
	 * 根据查询好的LIST结果去查询某个用户的各项考勤天数
	 * @param rsList
	 * @param key
	 * @return
	 */
	private Integer getDaysFromRsList(List<Map<String,Object>> rsList,Integer key){
		Integer days = 0;
		for(Map<String,Object> m : rsList){
			if(Integer.valueOf(m.get("userId").toString()).equals(key)){
				days = Integer.valueOf(m.get("days").toString());
			}
		}
		return days;
		
	}
	
	/**
	 * 获取工作天数
	 * @Description: 
	 * @param: 
	 * @return:
	 * @throws:
	 */
	public Integer getWorkDays(Map<String, Object> params, AttendanceRulePojo arp) {
		String startStr = params.get("startDate").toString();
		String endStr = params.get("endDate").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begDate = null;
		Date endDate = null;
		try {
			begDate = sdf.parse(startStr);
			endDate = sdf.parse(endStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BizException(BizExceptionMessage.PARAM_ERROR);
		}
		if (begDate.after(endDate))
			throw new BizException(BizExceptionMessage.PARAM_ERROR);
		// 总天数
		int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
		Calendar begCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		begCalendar.setTime(begDate);
		endCalendar.setTime(endDate);
		// 将jdk中（周日为1，周六为7）改为：周一为1，周日为7
		int beg = begCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (beg == 0)
			beg = 7;
		//将一段日期转换为1-7数值放入数组里
		int[] daysArray = new int[days];
		daysArray[0] = beg;
		for (int i = 1; i < days; i++) {
			if (7 == daysArray[i - 1])
				daysArray[i] = 1;
			else
				daysArray[i] = daysArray[i - 1] + 1;
		}
		// 公司规定的一周上班时间
//		String weekDays = arp.getWeekDays();
//		String[] weekDaysArray = weekDays.split(",");
//		int[] weekDaysInt=new int[weekDaysArray.length];
//		for(int i=0;i<weekDaysArray.length;i++){
//			weekDaysInt[i]=Integer.valueOf(weekDaysArray[i]);
//		}
		//总的工作天数
		int rs = 0;
//		for(int i=0;i<daysArray.length;i++){
//			for(int j=0;j<weekDaysInt.length;j++){
//				if(daysArray[i]==weekDaysInt[j])
//					rs++;
//			}
//		}
		return rs;
	}
}
