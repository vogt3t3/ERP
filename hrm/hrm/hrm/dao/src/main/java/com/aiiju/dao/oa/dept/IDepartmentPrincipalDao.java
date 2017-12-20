package com.aiiju.dao.oa.dept;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.DepartmentPrincipal;

/** 
 * 部门负责人表dao
@Author  太一 
@Date 创建时间：2016年11月1日 下午2:45:57 
*/
public interface IDepartmentPrincipalDao {
	/**
	 * 获取一个部门的部门负责人列表
	 * @param: department(department_id,company_id)
	 */
	public List<DepartmentPrincipal> getDepartmentPrincipal(Map<String, Object> department);
	/**
	 * 插入部门负责人时间段是否已存在
	 * @param: department
	 * @throws:Exception
	 * @return 如果大于0，则时间存在冲突
	 */
	public int existsDepartmentPrincipal(Map<String, Object> department);
	/**
	 * 新增部门负责人
	 * @param: department
	 */
	public int addDepartmentPrincipal(Map<String, Object> department);
	/**
	 * 更新部门负责人
	 * @param: department
	 */
	public int updateDepartmentPrincipal(Map<String, Object> department);
	/**
	 * 删除一条部门负责人
	 * @param: department
	 */
	public int delDepartmentPrincipal(Map<String, Object> department);
}
