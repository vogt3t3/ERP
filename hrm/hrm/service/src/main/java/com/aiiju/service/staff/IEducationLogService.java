package com.aiiju.service.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.EducationLog;
/**
 * 教育经历Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:21:52
 */
public interface IEducationLogService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:27:27
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:24
     */
    int addEducationLog(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:21
     */
    List<EducationLog> getEducationLogs(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:18
     */
    EducationLog getEducationLogById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:15
     */
    int updateEducationLog(Map<String,Object> map);
}
