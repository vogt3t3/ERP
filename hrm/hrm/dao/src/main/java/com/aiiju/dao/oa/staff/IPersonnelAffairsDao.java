package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.PersonnelAffairs;
/**
 * 人事事务dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午5:58:05
 */
public interface IPersonnelAffairsDao {
	/**
	 * 添加
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年10月18日  上午10:06:58
	 */
	int insert(PersonnelAffairs record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:06:54
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
	 * 2016年10月18日  上午10:06:51
	 */
	int updateByExample(PersonnelAffairs record);
	/**
	 * 按条件查询
	 * @author 维斯
	 * @param example
	 * @return
	 * 2016年10月18日  上午10:06:46
	 */
    List<PersonnelAffairs> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:06:28
     */
    PersonnelAffairs selectByUserId(Map<String ,Object> map);

    /**
     * 统计单月转正 离职人数
     * @author 维斯
     * 2016年12月5日   下午2:33:24
     */
    int countWork(Map<String ,Object> map);
}