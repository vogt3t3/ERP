package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.WorkCompanyLog;
/**
 * 工作经历Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:26:10
 */
public interface IWorkCompanyLogService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:30:08
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:30:05
     */
    int addWorkCompanyLog(Map<String,Object> map);
    /**
     * 查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:30:02
     */
    Map<String, Object> getWorkCompanyLogs(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:59
     */
    WorkCompanyLog getWorkCompanyLogById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:57
     */
    int updateWorkCompanyLog(Map<String,Object> map);
}
