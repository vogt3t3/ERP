package com.aiiju.dao.oa.salary;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.salary.SpwSalaryBatch;

public interface ISpwSalaryBatchDao {

	/**
	 * 插入
	 * @author 维斯
	 *
	 * 2016年11月21日   下午5:04:57
	 */
	long insert(SpwSalaryBatch batch);
	
	/**
	 * 根据ID更新薪酬批次表的发送状态
	 * @param params
	 */
	void updateStatusById(Map<String, Object> params);
	
	/**
	 * 查询历史发送记录
	 */
	public List<SpwSalaryBatch> queryHistorySalaryBatch(Map<String,Object> params);

	/**
	 * 获取历史发送记录数据量
	 * @param params
	 * @return
	 */
	public int getSalaryBatchCountByParams(Map<String, Object> params);

	/**
	 * 获取我的历史记录数据量
	 * @param params
	 * @return
	 */
	public int getMyHistorySalaryPayListCount(Map<String, Object> params);
	/**
	 * 获取我的历史记录
	 * @param params
	 * @return
	 */
	public List<SpwSalaryBatch> getMyHistorySalaryPayList(Map<String, Object> params);
	/**
	 * 根据id删除
	 * @author 维斯
	 * 2016年12月3日   上午10:47:54
	 */
	int deleteById(Long id);
}
