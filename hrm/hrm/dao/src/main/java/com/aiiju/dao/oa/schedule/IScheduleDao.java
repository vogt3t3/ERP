package com.aiiju.dao.oa.schedule;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.schedule.Schedule;

/**
 * 
 * @ClassName: IScheduleDao 
 * @Description: 待办事项数据访问层
 * @author 哪吒 
 * @date 2016年12月3日 上午11:51:40 
 *
 */

public interface IScheduleDao {

	/**
	 * 根据公司ID和用户ID获取待办事项的数量
	 * @param params
	 * @return
	 */
	public int getScheduleCountByUser(Map<String, Object> params);
	
	/**
	 * 根据公司ID,用户ID,指定年月获取待办事项列表
	 * @param params
	 * @return
	 */
	public List<Schedule> getScheduleListByUser(Map<String, Object> params);
	
	/**
	 * 新增待办事项
	 * @param params
	 */
	public void insertSchedule(Schedule schedule);
	
	/**
	 * 根据ID更新待办事项
	 * @param params
	 */
	public void updateScheduleById(Map<String, Object> params);
	
	/**
	 * 根据ID删除待办事项（单条）
	 * @param params
	 */
	public void delScheduleById(Map<String, Object> params);
}
