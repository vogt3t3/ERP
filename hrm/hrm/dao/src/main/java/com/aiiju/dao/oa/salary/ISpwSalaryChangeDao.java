package com.aiiju.dao.oa.salary;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.salary.SpwSalaryBasic;
import com.aiiju.bean.oa.salary.SpwSalaryBasicLog;

/**
 * 薪资调整DAO
 * @author BZ
 * 2016-12-19
 */
public interface ISpwSalaryChangeDao {

	/**
	 * 获取当前月份调薪操作记录列表总数（请求参数：companyId, userId-是操作人ID）
	 * @param params
	 * @return
	 */
	public int getCurMonthBasicPayLogListCount(Map<String, Object> params);
	
	/**
	 * 获取当前月份调薪操作记录列表总数（请求参数：companyId, userId-是操作人ID）
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBasicLog> getCurMonthBasicPayLogList(Map<String, Object> params);
	
	/**
	 * 获取当前月份薪资情况（参数companyId,deptId,userDraw，isConfirm-未定薪 1，已定薪2）
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBasicLog> getCurMonthSalaryInfo(Map<String,Object> params);
	
	/**
	 * 判断是否已经设置过初始薪资  0否
	 * @param params
	 * @return
	 */
	public int checkIsInit(Map<String,Object> params);
	
	/**
	 * 获取当前月份薪资情况- 计数
	 * @param params
	 * @return
	 */
	public int getCurMonthSalaryInfoCount(Map<String,Object> params);
	
	/**
	 * 获取调薪记录（参数companyId,setUserId-查询对象ID）
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBasicLog> getAdjustSalaryLog(Map<String,Object> params);
	
	/**
	 * 插入调薪操作记录
	 * @param params
	 * @return
	 */
	public int addSalaryLog(Map<String,Object> params);
	
	/**
	 * 设置初始薪资，添加薪资记录
	 * @param params
	 * @return
	 */
	public int addSalary(Map<String,Object> params);
	
	/**
	 * 获取调薪记录列表（用于调薪统计，请求参数：companyId,deptId(为空时代表查询全公司),生效时间）
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBasicLog> getSalaryBasicLogList(Map<String, Object> params);
	
	/**
	 * 获取调薪记录列表总数（用于调薪统计，请求参数：companyId,deptId(为空时代表查询全公司),生效时间）
	 * @param params
	 * @return
	 */
	public int getSalaryBasicLogListCount(Map<String, Object> params);
	
	/**
	 * 获取生效时间为近三天的“最新”薪资调整数据   用作定时器刷新时使用   (请求参数：pswKey，后期有需要可以把近三天改为近几天，这个可以当做参数放到配置文件，如果有必要的话。)
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBasic> getRecentSalaryInfo(Map<String, Object> params);
	
	/**
	 * 定时器刷新工资信息。
	 * @param params
	 * @return
	 */
	public int updateSalaryInfo(Map<String, Object> params);
	
	/**
	 * 删除调薪记录
	 * @param params
	 * @return
	 */
	public int deleteSalaryLog(Map<String, Object> params);
}


