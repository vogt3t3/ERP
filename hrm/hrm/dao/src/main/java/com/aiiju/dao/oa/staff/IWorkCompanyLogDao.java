package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.WorkCompanyLog;
/**
 * 工作经历dao 
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:14:38
 */
public interface IWorkCompanyLogDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:09:10
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
     * 2016年10月18日  上午10:09:07
     */
    int insert(WorkCompanyLog record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:09:04
     */
    List<WorkCompanyLog> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:09:01
     */
    WorkCompanyLog selectByUsrId(Map<String ,Object> map);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:08:56
     */
    int updateByExample(WorkCompanyLog record);

}