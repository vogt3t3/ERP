package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.LanguageAbilityLog;
/**
 * 语言能力记录
 * 
 * @author 维斯
 *	2016年10月17日	 下午5:55:15
 */
public interface ILanguageAbilityLogDao {
	/**
	 * 添加
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年10月18日  上午10:06:19
	 */
	int insert(LanguageAbilityLog record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:06:15
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
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:06:08
     */
    int updateByExample(LanguageAbilityLog record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:06:04
     */
    List<LanguageAbilityLog> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:05:59
     */
    LanguageAbilityLog selectByUserId(Map<String ,Object> map);

}