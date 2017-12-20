package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.UserDutyLevelLog;
/**
 * 职工等级Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:25:47
 */
public interface IUserDutyLevelLogService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:29:49
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:46
     */
    int addUserDutyLevelLog(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:43
     */
    Map<String, Object> getUserDutyLevelLogs(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:40
     */
    UserDutyLevelLog getUserDutyLevelLogById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:37
     */
    int updateUserDutyLevelLog(Map<String,Object> map);
}
