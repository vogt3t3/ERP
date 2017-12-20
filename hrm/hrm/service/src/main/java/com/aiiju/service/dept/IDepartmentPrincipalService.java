package com.aiiju.service.dept;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.DepartmentPrincipal;

/** 
 * 部门负责人service
@Author  太一 
@Date 创建时间：2016年11月2日 下午1:54:23 
*/
public interface IDepartmentPrincipalService {
	/**
	 * 显示一个部门组织负责人信息
	 * @param: params
	 */
	public List<DepartmentPrincipal> getDepartmentPrincipal(Map<String,Object> params);
	/**
	 * 增加一个部门组织负责人信息
	 * @param: params
	 */
	public int addDepartmentPrincipal(Map<String,Object> params);
	/**
	 * 修改一个部门组织负责人信息
	 * @param: params
	 */
	public int updateDepartmentPrincipal(Map<String,Object> params);
	/**
	 * 删除一个部门组织负责人信息
	 * @param: params
	 */
	public int delDepartmentPrincipal(Map<String,Object> params);
}
