package com.aiiju.dao.oa.duty;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.duty.AbilityRequire;
/**
 * 能力要求/能力素养 dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午5:50:32
 */
public interface IAbilityRequireDao {
	/**
	 * 添加
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年10月17日  下午7:05:13
	 */
	int insert(AbilityRequire record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月17日  下午7:05:19
	 */
    int deleteByParam(Map<String, Object> param);
    /**
     * 按参数更新 pid
     * @author 维斯
     * @param example
     * @return
     * 2016年10月17日  下午7:05:23
     */
    int updateByExample(AbilityRequire example);
   /**
    * 按条件查询
    * @author 维斯
    * @param example
    * @return
    * 2016年10月17日  下午7:05:29
    */
    List<AbilityRequire> selectByExample(Map<String,Object> map);
    /**
     * 通过Pid查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月17日  下午7:05:35
     */
    AbilityRequire selectById(Long id); 
    


}