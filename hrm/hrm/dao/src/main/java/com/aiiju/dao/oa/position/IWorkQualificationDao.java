package com.aiiju.dao.oa.position;

import java.util.List;

import java.util.Map;

import com.aiiju.bean.oa.position.WorkQualification;
/**
 * 任职资格
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:14:46
 */
public interface IWorkQualificationDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:09:31
	 */
    int deleteById(Long id);
    /**
     * 添加
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:09:28
     */
    int insert(WorkQualification record);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:09:23
     */
    List<WorkQualification> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月18日  上午10:09:19
     */
    WorkQualification selectById(Long id);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:09:16
     */
    int updateByExample(WorkQualification record);

}