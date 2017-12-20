package com.aiiju.dao.oa.workStage;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.workStage.AttendanceRuleForStat;
import com.aiiju.bean.oa.workStage.AttendanceRulePojo;
import com.aiiju.bean.oa.workStage.AttendanceStat;

@Repository
public interface IAttendanceDao {

	//根据所选部门和员工，查询用户及其规则列表
	public List<AttendanceRuleForStat> getUserRuleList(Map<String,Object> params);
	
	//获取考勤统计数据--总数
	public Integer getAttendanceStatCount(Map<String,Object> params);
	
	//获取考勤统计数据
	public List<AttendanceStat> getAttendanceStat(Map<String,Object> params);
}
