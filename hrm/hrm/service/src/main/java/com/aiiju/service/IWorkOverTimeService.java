package com.aiiju.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: IWorkOverTimeService
 * @Description: 工作加班记录接口
 * @author 琪琪
 * @date 2016年10月11日 上午11:02:23 
 *
 */
@Repository
public interface IWorkOverTimeService {
	/**
	 * 添加加班日志
	 * @param params
	 * @return
	 */
	public Map<String,Object> addWorkOverTimeLog(Map<String,Object> params);
	/**
	 * 修改加班日志
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateWorkOverTimeLog(Map<String,Object> params);
	/**
	 * 删除加班记录
	 * @param params
	 * @return
	 */
	public Map<String,Object> delWorkOverTimeLog(Map<String,Object> params);
	
	
	/**
	 * 
	 * @Title: getWorkOverTimeLogsByParams 
	 * @Description: 获取加班记录列表
	 * @param params
	 * @return 
	 * @throws
	 */
	public Map<String, Object> getWorkOverTimeLogsByParams(Map<String, Object> params);

}
