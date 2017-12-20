package com.aiiju.service.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.TaskInfo;
/**
 * 任务service
 * @author 维斯
 * 
 * 2017年1月5日 	下午3:57:16
 */
public interface ITaskInfoService {
	/**
	 * 获取任务列表
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月5日 	下午3:51:41
	 */
	Map<String,Object> getTaskInfos(Map<String,Object> map);
	
	/**
	 * 撤回任务
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月5日 	下午3:53:45
	 */
	boolean withdrawTaskInfos(Map<String,Object> map);
	/**
	 * 创建任务
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月5日 	下午3:54:26
	 */
	Map<String,Object> addTaskInfos(Map<String,Object> map);
	/**
	 * 转发任务
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月5日 	下午3:55:02
	 */
	boolean changeTaskInfos(Map<String,Object> map);
	/**
	 * 完成任务
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月9日 	上午10:58:29
	 */
	boolean finishTaskInfos(Map<String,Object> map);
	
	/**
	 * 任务详情
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月5日 	下午3:56:41
	 */
	Map<String,Object> getTaskInfoById(Map<String,Object> map);
	
	/**
	 * 多条件查询任务列表(不带分页)
	 * @author 小辉
	 * @param map
	 * @return
	 */
	List<TaskInfo> getTaskInfoListByMap(Map<String, Object> map);
	/**
	 * 给定时器使用的接口
	 * @author 小辉
	 * @param map
	 * @return
	 */
	boolean updateTaskStep(Map<String, Object> map);
	
	/**
	 * 开始处理任务(修改任务状态为处理中)
	 * @author 小辉
	 * @param task
	 * @return
	 */
	boolean startDealTask(Map<String, Object> map);
	
}
