package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.ReportRelation;
/**
 * 汇报关系Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:23:55
 */
public interface IReportRelationService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:29:12
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:08
     */
    int addReportRelation(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:05
     */
    Map<String, Object> getReportRelations(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:03
     */
    ReportRelation getReportRelationById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:56
     */
    int updateReportRelation(Map<String,Object> map);
}
