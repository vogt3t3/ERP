package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.ProfessionSkillQualification;
/**
 * 专业技术资格Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:23:42
 */
public interface IProfessionSkillQualificationService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:28:51
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:48
     */
    int addProfessionSkillQualification(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:45
     */
    Map<String, Object> getProfessionSkillQualifications(Map<String,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:43
     */
    ProfessionSkillQualification getProfessionSkillQualificationById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:28:40
     */
    int updateProfessionSkillQualification(Map<String,Object> map);
}
