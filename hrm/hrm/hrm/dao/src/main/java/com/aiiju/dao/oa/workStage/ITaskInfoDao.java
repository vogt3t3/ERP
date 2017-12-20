package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.TaskInfo;
/**
 * 任务
 * @author 维斯
 *
 * 2016年12月28日   下午2:37:40
 */
public interface ITaskInfoDao {
	/**
	 * 添加 返回主键
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
	int addTaskInfo(TaskInfo record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
    int deleteTaskInfoId(Long id);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年12月28日   下午2:35:21
     */
    int updateTaskInfo(Map<String,Object> map);
    /**
     * 自己接收到的任务
     * @author 维斯
     * @param 
     * @return
     * 2016年12月28日   下午2:35:21
     */
    List<TaskInfo> selectTaskInfoByMe(Map<String,Object> map);
    /**
     * 自己发布的任务
     * @author 维斯
     * @param map
     * @return
     * 2017年1月6日 	下午4:04:55
     */
    List<TaskInfo> getTaskInfoByMe(Map<String,Object> map);
    /**
     * 统计
     * @param map
     * @return
     */
    int countTaskInfoByMe(Map<String,Object> map);
    /**
     * 统计自己任务
     * @param map
     * @return
     */
    int countTask(Map<String,Object> map);
    /**
     *  通过id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年12月28日   下午2:35:21
     */
    TaskInfo selectTaskInfoId(int id);
    /**
     * 条件组合查询任务列表(不带分页)
     * @author 小辉
     * @param map
     * @return
     */
	List<TaskInfo> selectTaskInfoListByMap(Map<String, Object> map);
	/**
	 * 这个方法目前仅用于查询任务的回复总数 
	 * @param param
	 * @return
	 */
	List<TaskInfo> getTaskReplyNum(Map<String, Object> param);
}
