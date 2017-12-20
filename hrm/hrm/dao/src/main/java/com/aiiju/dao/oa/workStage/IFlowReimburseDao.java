package com.aiiju.dao.oa.workStage;
import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.FlowReimburse;

/**
 * 报销Dao
 * @author qiqi
 * @date 2016-12-28 11：11：11
 *
 */
public interface IFlowReimburseDao {
	/**
	 * 添加报销流程
	 * @param flowReimburse
	 * @return
	 */
	public Long addFlowReimburse(FlowReimburse flowReimburse);
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<FlowReimburse> getFlowReimburseByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: getFlowInfoListByParams 
	 * @Description: 根据参数（类别、时间、发起人）获取报销数据列表
	 * @param params
	 * @return 
	 * @throws
	 */
	public List<FlowReimburse> getFlowReimburseListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: getTotalNumByParams 
	 * @Description: 根据参数（类别、时间、发起人）获取报销详情数据总数
	 * @param params
	 * @return 
	 * @throws
	 */
	public int getTotalNumByParams(Map<String, Object> params);
}
