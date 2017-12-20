package com.aiiju.service.salary;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 薪资调整Service
 * @author BZ
 *
 */
public interface ISpwSalaryChangeService {
	
	/**
	 * 获取调整薪资日志的列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getBasicPayLogList(Map<String, Object> params);
	
	/**
	 * 获取当前月份薪资情况
	 * @param params
	 * @return
	 */
	public Map<String,Object> getCurMonthSalaryInfo(Map<String,Object> params);
	
	/**
	 * 调整薪资（参数isInit : 1 设置初始薪资，0 调薪）
	 * @param params
	 * @return
	 */
	public Map<String,Object> changeSalary(Map<String,Object> params);
	
	/**
	 * 查询调薪记录  - 调薪页面时右侧的调薪记录
	 * @param params
	 * @return
	 */
	public Map<String,Object> getAdjustSalaryLog(Map<String,Object> params);
	
	/**
	 * 获取调薪统计
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSalaryInfoCount(Map<String, Object> params);
	
	/**
	 * 获取调薪统计详情列表（用于数据展示）
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSalaryInfoCountDetail(Map<String, Object> params);
	
	/**
	 * 定时器刷新工资。  
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateSalary();
	
	/**
	 * 删除调薪记录
	 * @param params
	 * @return
	 */
	public Map<String, Object> deleteSalaryLog(Map<String,Object> params);
	
	/**
	 * 导出调薪统计报表
	 * @param params
	 * @return
	 */
	HSSFWorkbook exportSalaryCountExcel(Map<String, Object> params);
	
}
