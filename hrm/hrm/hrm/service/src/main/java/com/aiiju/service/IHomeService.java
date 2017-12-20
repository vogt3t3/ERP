package com.aiiju.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: IHomeService 
 * @Description: 首页业务层接口
 * @author 哪吒 
 * @date 2016年12月3日 上午10:48:45 
 *
 */

@Repository
public interface IHomeService {

	/**
	 * 新增待办事项
	 * @param params
	 * @return
	 */
	public Map<String, Object> addSchedule(Map<String, Object> params);
	
	/**
	 * 根据公司ID和用户ID获取待办事项列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getScheduleListByParam(Map<String, Object> params);
	
	/**
	 * 根据ID删除待办事项
	 * @param params
	 * @return
	 */
	public Map<String, Object> delScheduleById(Map<String, Object> params);
	
	/**
	 * 根据ID更新待办事项
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateScheduleById(Map<String, Object> params);
	
	/**
	 * hr登录后主页的返回信息
	 * @param params
	 * @return
	 */
	
	
	
	public Map<String, Object> comeHomePage(Map<String, Object> params);

	
	/**
	 *	处理备忘录（增，删，改）
	 * @param params
	 * @return
	 */
	
	public void dealSchedule(Map<String, Object> params);
	
	/**
	 *	查看备忘录列表（带时间参数可搜索）
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSchedules(Map<String, Object> params);

	
	/**
	 *	工作台角标提示
	 * @param params
	 * @return
	 */
	public Map<String, Object> workStageSuperscript (Map<String, Object> params);
}
