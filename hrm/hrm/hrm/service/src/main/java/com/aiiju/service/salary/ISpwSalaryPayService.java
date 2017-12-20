package com.aiiju.service.salary;

import java.util.Map;

public interface ISpwSalaryPayService {
	/**
	 * 导入
	 * @author 维斯
	 *
	 * 2016年11月21日   上午10:06:56 
	 */
	Map<String,Object> leadSpwSalaryPays(Map<String,Object> params);
	/**
	 * 批量插入
	 * @author 维斯
	 *
	 * 2016年11月22日   上午10:37:41
	 */
	long inserts(Map<String,Object> params);
	/**
	 * 发送工资条(邮件和短信)
	 * @param params
	 */
	Map<String, Object> sendMessageOfSalary(Map<String, Object> params);
	
	/**
	 * 工资单发送历史记录--批次
	 * @param params
	 * @return
	 */
	public Map<String,Object> getSalaryBatchList(Map<String, Object> params);
	
	/** 工资单发送历史明细记录-----导入预览   通用接口
	 * @param params
	 * @return
	 */
	public Map<String,Object> getSalaryPayList(Map<String,Object> params);
	
	/**
	 * 获取我的工资单历史记录
	 * @param params
	 * @return
	 */
	public Map<String,Object> getMyHistorySalaryPayList(Map<String,Object> params);
	
	/**
	 * 获取我的工资单详细
	 * @param params
	 * @return
	 */
	public Map<String, Object> getMySalaryPay(Map<String,Object> params);
	
	/**
	 * 获取我的工资单详细--APP数据展示专用
	 * @param params
	 * @return
	 */
	public Map<String, Object> getMySalaryPayForApp(Map<String, Object> params);
	
	/**
	 * 发送验证码
	 * @param params
	 * @return
	 */
	public Map<String, Object> sendSMSOfCheckCode(Map<String, Object> params);
	
	/**
	 * 校验验证码
	 * @param params
	 * @return
	 */
	public Map<String, Object> validateCheckCode(Map<String, Object> params);
	
	/**
	 * 更新二级密码
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateSecondaryPwd(Map<String, Object> params);
	
	/**
	 * 判断是否存在二级密码，从而选择跳转到哪个页面。
	 * @param params
	 * @return
	 */
	public Map<String,Object> toSetOrInputPwd(Map<String,Object> params);
	
	/**
	 * 二级密码校验
	 * @param params
	 * @return
	 */
	public Map<String,Object> checkSecondPwd(Map<String,Object> params);
	
	/**
	 * 根据ID撤回薪资发送批次
	 * @param params
	 * @return
	 */
	public Map<String, Object> withdrawSalaryById(Map<String, Object> params);
	
	/**
	 * 薪资删除
	 * @param params
	 * @return
	 */
	public Map<String, Object> delSalaryInfo(Map<String, Object> params);
	
	/**
	 * 用户查询工资条时的验证码校验(后期可以和validateCheck方法合并)
	 * @param params
	 * @return
	 */
	public Map<String, Object> validateCheckCodeById(Map<String, Object> params);
	
}
