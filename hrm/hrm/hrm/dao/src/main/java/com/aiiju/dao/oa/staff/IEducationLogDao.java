package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.EducationLog;
/**
 * 教育经历dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午5:50:52
 */
public interface IEducationLogDao {
	/**
	 * 添加
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年10月18日  上午10:02:37
	 */
	int insert(EducationLog record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:04:57
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
     * 按参数更新 通过userid
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:05:01
     */
    int updateByExample(EducationLog record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:05:06
     */
    List<EducationLog> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:05:14
     */
    EducationLog selectByUserId(Map<String ,Object> map);

}