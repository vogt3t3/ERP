package com.aiiju.dao.oa.dept;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.PersonnelForce;

/** 
 * 部门人员编制数表dao
@Author  太一 
@Date 创建时间：2016年11月2日 下午1:48:13 
*/
public interface IDepartmentPersonNumDao {

	/**
	 * 获取一个部门的人员编制数信息
	 * @param: department
	 * @return:List<PersonnelForce>
	 */
	public List<PersonnelForce> getDepartmentPersonNum(Map<String, Object> department);
	/**
	 * 插入人员编制时间段是否已存在
	 * @param: department
	 * @return 如果大于0，则时间存在冲突
	 */
	public int existsDepartmentPersonNum(Map<String, Object> department);
	/**
	 * 新增人员编制
	 * @param: department
	 */
	public int addDepartmentPersonNum(Map<String, Object> department);
	/**
	 * 更新人员编制
	 * @param: department
	 */
	public int updateDepartmentPersonNum(Map<String, Object> department);
	/**
	 * 删除一条人员编制
	 * @param: department
	 */
	public int delDepartmentPersonNum(Map<String, Object> department);
}
