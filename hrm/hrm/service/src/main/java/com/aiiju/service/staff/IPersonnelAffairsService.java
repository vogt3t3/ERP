package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.PersonnelAffairs;
/**
 * 人事事物Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:22:45
 */
public interface IPersonnelAffairsService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:28:18
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:15
     */
    int addPersonnelAffairs(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:12
     */
    Map<String, Object> getPersonnelAffairss(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:09
     */
    PersonnelAffairs getPersonnelAffairsById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:07
     */
    int updatePersonnelAffairs(Map<String,Object> map);
}
