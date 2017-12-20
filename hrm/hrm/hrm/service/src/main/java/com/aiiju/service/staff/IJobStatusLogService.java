package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.JobStatusLog;
/**
 * 任职情况Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:22:09
 */
public interface IJobStatusLogService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:27:45
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:42
     */
    int addJobStatusLog(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:39
     */
    Map<String, Object> getJobStatusLogs(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:35
     */
    JobStatusLog getJobStatusLogById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:32
     */
    int updateJobStatusLog(Map<String,Object> map);
}
