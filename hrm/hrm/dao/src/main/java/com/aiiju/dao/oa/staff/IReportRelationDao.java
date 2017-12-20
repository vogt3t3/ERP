package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.ReportRelation;
/**
 * 汇报关系dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:03:23
 */
public interface IReportRelationDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:08:07
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
     * 2016年10月18日  上午10:08:03
     */
    int insert(ReportRelation record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:07:59
     */
    List<ReportRelation> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:07:56
     */
    ReportRelation selectByUserId(Map<String ,Object> map);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:07:52
     */
    int updateByExample(ReportRelation record);

}