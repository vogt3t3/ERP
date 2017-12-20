package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.workStage.Like;
import com.aiiju.bean.oa.workStage.WorkReport;

@Repository
public interface IWorkReportDao {
	
	/**
	 * 保存工作汇报
	 * @param params
	 * @return
	 */
	public Long saveWorkReport(WorkReport wr);
	
	/**
	 * 根据条件获取 我发送的 工作汇报列表
	 * @param params
	 * @return
	 */
	public List<WorkReport> getWorkReportList(Map<String,Object> params);
	
	/**
	 * 根据条件获取 我接收的 工作汇报列表
	 * @param params
	 * @return
	 */
	public List<WorkReport> getReceiveWorkReportList(Map<String,Object> params);
	
	/**
	 * 获取日报列表总数量
	 * @param params
	 * @return
	 */
	public int getWorkReportListTotalNum(Map<String, Object> params);
	
	/**
	 * 获取我接收的日报列表总数量
	 * @param params
	 * @return
	 */
	public int getReceiveWorkReportListTotalNum(Map<String, Object> params);
	
	/**
	 * 根据条件 模糊查询 我接收的 工作汇报列表
	 * @param params
	 * @return
	 */
	public List<WorkReport> queryReceiveWorkReportList(Map<String,Object> params);
	
	/**
	 * 根据条件查询我接收的日报列表总数量
	 * @param params
	 * @return
	 */
	public int queryReceiveWorkReportListTotalNum(Map<String, Object> params);
	
	/**
	 * 根据条件查询未读数
	 * @param params
	 * @return
	 */
	public Integer getUnReadNumByUserId(Map<String,Object> params);
	
	/**
	 * 手动点赞
	 * @param params
	 * @return
	 */
	public Long addLike(Like like);
	
	/**
	 * 手动取消点赞
	 * @param params
	 * @return
	 */
	public void deleteLike(Map<String,Object> params);
	
	
	/**
	 * 查看点赞，用于判断是否已经点赞
	 * @param params
	 * @return
	 */
	public Integer selectLike(Map<String,Object> params);
	
	
	
	/**
	 * 更新工作汇报
	 * @param params
	 * @return
	 */
	public void updateReport(Map<String,Object> params);
	
	/**
	 * 根据ID获取工作汇报信息
	 * @param params
	 * @return
	 */
	public WorkReport getWorkReportById(Map<String,Object> params);
	
	/**
	 * 查询工作汇报详情
	 * @param params
	 * @return
	 */
	public WorkReport selectReportDetail(Map<String, Object> params);
	
}
