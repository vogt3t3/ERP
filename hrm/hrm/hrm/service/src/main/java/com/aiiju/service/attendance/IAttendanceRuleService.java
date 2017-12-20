package com.aiiju.service.attendance;

import java.util.Map;

/**
 * 考勤规则的service
 * 2017.05.02
 * @author 小辉
 *
 */
public interface IAttendanceRuleService {
	
	/**
	 * 新增一条考勤规则
	 * @param params
	 * @return
	 */
	public Map<String,Object> addAttendanceRule(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取考勤规则列表
	 * @param params
	 * @return
	 */
	public Map<String,Object> getAttendanceRuleList(Map<String,Object> params) throws Exception;
	/**
	 * 修改考勤规则
	 * @param params
	 * @return
	 */
	public Map<String,Object> updateAttendanceRule(Map<String,Object> params) throws Exception;
	/**
	 * 查询单个考勤规则详情
	 * @param params
	 * @return
	 */
	public Map<String,Object> getAttendanceRuleDetail(Map<String,Object> params) throws Exception;
	/**
	 * 获取服务器的系统时间
	 * @param params
	 * @return
	 */
	public Map<String,Object> getCurrentServerTime(Map<String,Object> params);
	/**
	 * app端获取当前用户当天的考勤规则
	 * @param params
	 * @return
	 */
	public Map<String,Object> getSignRuleForApp(Map<String,Object> params);
	/**
	 * app端考勤签到签退接口
	 * @param params
	 * @return
	 */
	public Map<String,Object> signInOrOut(Map<String,Object> params) throws Exception;
	/**
	 * 删除考勤规则接口
	 * @param params
	 * @return
	 */
	public Map<String,Object> deleteAttendanceRule(Map<String,Object> params);
	/**
	 * app获取当前用户当天的考勤记录
	 * @param params
	 * @return
	 */
	public Map<String,Object> getSignRecords(Map<String,Object> params) throws Exception;
	
	/**
	 * 修改考勤规则状态为生效状态(此接口定时器用)
	 * @param params
	 * @return
	 */
	public int updateIsUsed();
}
