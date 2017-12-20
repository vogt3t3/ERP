package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.JobStatusLog;
/**
 * 任职情况记录表dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午5:53:19
 */
public interface IJobStatusLogDao {
	/**
	 * 添加
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年10月18日  上午10:05:35
	 */
	int insert(JobStatusLog record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:05:50
	 */
    int deleteById(Long id);
    /**
     * 通过员工id删除
     * @author 维斯
     *
     * 2016年11月18日   下午1:56:15
     */
    int deleteByUserId(String[] ids);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:05:47
     */
    int updateByExample(JobStatusLog record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:05:43
     */
    List<JobStatusLog> selectByExample(Map<String,Object> map);
    /**
     * 通过Id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:05:39
     */
    JobStatusLog selectByUserId(Map<String ,Object> map);

}