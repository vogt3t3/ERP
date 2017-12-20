package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.HistoryLog;

/**
 * 
 * @ClassName: IHistoryLogDao 
 * @Description: 消息日志业务数据访问层
 * @author 哪吒 
 * @date 2016年12月28日 下午6:05:53 
 *
 */

public interface IHistoryLogDao {
	/**
	 * 批量添加histroyLog对象
	 * @param log
	 * @return
	 */
	public Long addHistoryLog(List<HistoryLog> logs);
	/**
	 * 修改historyLog中的记录
	 * @param log
	 * @return
	 */
	public int updateHistoryLog(HistoryLog log);
	/**
	 * 根据条件查询审批流程记录
	 * @param params
	 * @return
	 */
	public List<HistoryLog> getHistoryLogs(Map<String,Object> params);

	/**
	 * 统计总数
	 * @author 维斯
	 * @param params
	 * @return
	 * 2017年1月6日 	下午4:10:48
	 */
	int countHistoryLog(Map<String,Object> params);
	/**
	 * 新增一条历史记录
	 * @param log
	 * @return
	 */
	int insertHistorySeclective(HistoryLog log);
	/**
	 * 根据条件修改任务历史记录
	 * 仅修改为已转发或已读
	 * @param map
	 */
	public int updateTaskHistoryByMap(Map<String, Object> map);
	
	
	
	
	/**
	 * 批量操作记录为已读(事件就是每个接收者对事件接收的记录)
	 */
	
	public void batchUpdateHistoryLog(Map<String, Object> params);
	
	
	
	/**
	 * HR主页面工作汇报未读数和待处理的统计数（2个分开返回）
	 */
	
	public Map<String,Object> getIncidentNum(Map<String, Object> params);
	
	/**
	 * 工作台情景的统计数（3个分开返回，不包含公告）
	 */
	
	public Map<String,Object> getScenesNum(Map<String, Object> params);
	
}
