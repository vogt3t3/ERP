package com.aiiju.service.dept;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.PersonnelForce;

/** 
 * 部门的人员编制 Service
@Author  太一 
@Date 创建时间：2016年11月2日 下午1:44:37 
*/
public interface IDepartmentPersonNumService {
	/**
	 * 显示一个部门的人员编制人数信息
	 * @param: params
	 */
	public List<PersonnelForce> getDepartmentPersonNum(Map<String,Object> params);
	/**
	 * 修改一个部门的人员编制人数
	 * @param: params
	 */
	public int updateDepartmentPersonNum(Map<String,Object> params);
	/**
	 * 增加一个部门的人员编制人数
	 * @param: params
	 */
	public int addDepartmentPersonNum(Map<String,Object> params);
	/**
	 * 删除一个部门的一条人员编制人数
	 * @param: params
	 */
	public int delDepartmentPersonNum(Map<String,Object> params);
}
