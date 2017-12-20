package com.aiiju.service.dept;

import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.aiiju.bean.oa.dept.DepartmentTreePojo;


/** 
@Author  太一 
@Date 创建时间：2016年10月18日 下午2:00:47 
*/
public interface IDepartmentService {
	/**
	 * 新增部门
	 * @param: params
	 */
	public int addDepartment(Map<String,Object> params);
	/**
	 * 修改部门（包括封存（含批量））
	 * @param: params
	 */
	public int updateDepartment(Map<String,Object> params);
	/**
	 * 删除部门
	 * @param: params
	 */
	public int delDepartment(Map<String,Object> params);
	/**
	 * 导入部门
	 * @param: params
	 */
	public Map<String,Object> importDepartment(Map<String,Object> params);
	/**
	 * 导出部门
	 * @param: params
	 */
	public HSSFWorkbook exportDepartment(Map<String,Object> params);
	/**
	 * 获取部门信息列表(可获取公司，可根据部门名等关键词查找)
	 * @param: params
	 */
	public Map<String, Object> getDepartmentList(Map<String,Object> params);
	/**
	 * 获取部门树状结构(员工，职务职位等公用接口)
	 * @param: params
	 */
	public List<DepartmentTreePojo> getDepartmentTreeByCompanyId(Map<String,Object> params);
	/**
	 * 获取部门架构图（主要是人员编制等）
	 * @param: params
	 */
	public List<DepartmentTreePojo> getDepartmentFramework(Map<String,Object> params);
	/**
	 * 下载部门列表模板excel文件
	 * @param params
	 */
	public HSSFWorkbook downDeptModelExcel(Map<String, Object> params);
	
	/**
	 *  获取组织树状结构---（组织下面可能包含员工，职位）
	 * @param params
	 * @return
	 */
	public List<DepartmentTreePojo> getCommonInfoByDepartmentTree(Map<String, Object> params);
	
	
	/**
	 *  手机注册会创建公司，该接口用来初始化该公司的部门
	 * @param params
	 * @return
	 */
	
	public void initDepartmentForHTTP(Map<String, Object> params);
	
	/**
	 *  该接口用来创建公司时修改顶级部门名
	 * @param params
	 * @return
	 */
	
	void updateDepartmentForHTTP(Map<String, Object> params);
	
	
	
}
