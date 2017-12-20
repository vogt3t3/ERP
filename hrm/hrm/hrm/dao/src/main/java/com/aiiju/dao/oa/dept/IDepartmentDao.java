package com.aiiju.dao.oa.dept;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.dept.DepartmentTreePojo;
 
/** 
 * 部门dao
@Author  太一
@Date 创建时间：2016年10月18日 下午6:57:07 
*/
public interface IDepartmentDao {
	/**
	 * 新增部门信息(包括法人信息)
	 * @param: params
	 */
	public int addDepartment(Map<String, Object> department);
	
	/**
	 * 查询部门是否已经存在该名称
	 */
	public int getCntByDeptName(Map<String, Object> department);
	/**
	 * 修改部门信息(包括封存)
	 * @param: 
	 */
	public int updateDepartment(Map<String, Object> department);
	/**
	 * 获取一个部门的子部门数
	 * @param: department
	 * @return 没有子部门返回0
	 */
	public int getSubdeptCount(String deptId);
	/**
	 * 获取一个部门的子部门数
	 * @param: department
	 * @return 没有子部门返回0
	 */
	public int getEmpCount(String deptId);
	/**
	 * 删除部门（一个或多个）
	 * @param: department
	 * @return 
	 */
	public int delDepartmentByIds(Map<String, Object> department);
	
	/**
	 * 获取一个公司的部门树状结构
	 * @param: department
	 */
	public List<DepartmentTreePojo> getDepartmentTreeCompanyId(Map<String, Object> department);
	/**
	 * 获取部门信息列表(可获取公司，可根据部门名等关键词查找)
	 * @param: department
	 */
	public List<Department> getDepartmentList(Map<String, Object> department);
	/**
	 * 获取部门信息列表(APP 新版通讯录)
	 * @param: department
	 */
	public List<Department> getNewDepartmentList(Map<String, Object> department);
	/**
	 * 根据id获取一个部门的信息（递归时用，不要把根据关键字搜索部门合并）
	 * @param: department
	 */
	public Department getDepartmentById(Map<String, Object> department);
	/**
	 * 获取一个公司的部门架构(（主要是人员编制等）含子部门架构 )
	 * @param: department
	 */
	public List<DepartmentTreePojo> getDepartmentFramework(Map<String, Object> department);
	/**
	 * 获取部门列表总数（可获取公司、可根据部门名等关键词查找）
	 * @param: params
	 */
	public int getDepartmentListCount(Map<String, Object> params);
	
	/**
	 * 批量新增部门信息
	 * @param list
	 */
	public void batchAddDeptInfo(Map<String, Object> params);
	
	/**
	 * 批量更新部门信息
	 * @param list
	 */
	public void batchUpdateDeptParentId(List<Department> list);
	/**
	 * 获取顶级组织
	 * @author 维斯
	 * @param params
	 * @return
	 * 2016年12月16日   下午2:31:51
	 */
	public Department selectDepartmentByCompanyId(Map<String, Object> params);
	
	
	/**
	 * 
	 * @Description: 通过部门ID（多个用,隔开）   获得该部门下的所有子部门ID包括自己（已排重）
	 * @author 琦玉
	 * @param: 
	 * @return:
	 * @throws:
	 */
	public String getDeptChildIds(Map<String, Object> params);
}
