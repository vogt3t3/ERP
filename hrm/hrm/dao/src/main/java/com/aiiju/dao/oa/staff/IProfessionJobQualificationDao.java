package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.ProfessionJobQualification;
/**
 * 职业资格dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:01:27
 */
public interface IProfessionJobQualificationDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:07:18
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
     * 2016年10月18日  上午10:07:15
     */
    int insert(ProfessionJobQualification record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:07:11
     */
    List<ProfessionJobQualification> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:07:07
     */
    ProfessionJobQualification selectByUserId(Map<String ,Object> map);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:07:04
     */
    int updateByExample(ProfessionJobQualification record);

}