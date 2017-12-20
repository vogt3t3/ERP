package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.LanguageAbilityLog;
/**
 * 语言Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:22:23
 */
public interface ILanguageAbilityLogService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:28:02
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:59
     */
    int addLanguageAbilityLog(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:56
     */
    Map<String, Object> getLanguageAbilityLogs(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:53
     */
    LanguageAbilityLog getLanguageAbilityLogById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:49
     */
    int updateLanguageAbilityLog(Map<String,Object> map);
}
