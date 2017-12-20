package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.UserDutyLevelLog;
/**
 * 职工等级dao
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:24:31
 */
public interface IUserDutyLevelLogDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月20日  下午6:25:04
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
     * 添加
     * @author 维斯
     * @param record
     * @return
     * 2016年10月20日  下午6:25:01
     */
    int insert(UserDutyLevelLog record);
    /**
     * 条件查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:24:57
     */
    List< UserDutyLevelLog> selectByExample(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月20日  下午6:24:54
     */
    UserDutyLevelLog selectByUserId(Map<String ,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月20日  下午6:24:50
     */
    int updateByExample(UserDutyLevelLog record);
}