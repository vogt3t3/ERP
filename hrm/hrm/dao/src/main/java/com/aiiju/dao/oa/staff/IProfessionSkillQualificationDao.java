package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.ProfessionSkillQualification;
/**
 * 专业技术资格dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:01:41
 */
public interface IProfessionSkillQualificationDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:07:45
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
     * 2016年10月18日  上午10:07:41
     */
    int insert(ProfessionSkillQualification record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:07:37
     */
    List<ProfessionSkillQualification> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:07:32
     */
    ProfessionSkillQualification selectByUserId(Map<String ,Object> map);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:07:26
     */
    int updateByExample(ProfessionSkillQualification record);

}