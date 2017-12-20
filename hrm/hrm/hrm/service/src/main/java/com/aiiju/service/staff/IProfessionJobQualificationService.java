package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.ProfessionJobQualification;
/**
 * 职业资格Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:23:28
 */
public interface IProfessionJobQualificationService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map 
	 * @return
	 * 2016年10月20日  下午6:28:34
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:31
     */
    int addProfessionJobQualification(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:28
     */
    Map<String, Object> getProfessionJobQualifications(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:26
     */
    ProfessionJobQualification getProfessionJobQualificationById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:23
     */
    int updateProfessionJobQualification(Map<String,Object> map);
}
