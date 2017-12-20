package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.SocietyRelations;
/**
 * 社会关系Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:24:10
 */
public interface ISocietyRelationsService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:29:31
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:28
     */
    int addSocietyRelations(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:25
     */
    Map<String, Object> getSocietyRelations(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:21
     */
    SocietyRelations getSocietyRelationsById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:29:18
     */
    int updateSocietyRelations(Map<String,Object> map);
}
