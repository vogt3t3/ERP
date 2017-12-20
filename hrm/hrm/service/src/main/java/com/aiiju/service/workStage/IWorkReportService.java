package com.aiiju.service.workStage;

import java.util.Map;

import com.aiiju.bean.oa.workStage.WorkReport;

/**
 * 工作汇报Service
 * @author BZ
 *
 */
public interface IWorkReportService {
	
	/**
	 * 工作汇报列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getWorkReportList(Map<String, Object> params);
	
	/**
	 * 保存工作汇报
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveWorkReport(Map<String, Object> params);
	
	/**
	 * 点赞OR取消点赞
	 * @param params
	 * @return
	 */
	public Map<String, Object> changeLike(Map<String, Object> params);
	
	/**
	 * 撤回工作汇报
	 * @param params
	 * @return
	 */
	public Map<String, Object> withdrawReport(Map<String, Object> params);
	
	/**
	 * 获取树列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getTreeList(Map<String, Object> params);
	
	/**
	 * 添加草稿
	 * @param params
	 * @return
	 */
	public Map<String, Object> addDraft(Map<String, Object> params);
	
	/**
	 * 更新草稿
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateDraft(Map<String, Object> params);

	/**
	 * 已读
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateReadStatus(Map<String, Object> params);
	
	/**
	 * 批量操作日报为已读
	 * @param params
	 * @return
	 */
	public Map<String, Object> batchUpdateWorkReport(Map<String, Object> params);
	
	
	/**
	 * 查看工作汇报----详情(APP)
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public WorkReport getReportDetail(Map<String, Object> params) throws Exception;
}
