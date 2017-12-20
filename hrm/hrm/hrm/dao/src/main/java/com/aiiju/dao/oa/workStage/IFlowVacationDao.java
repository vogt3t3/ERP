package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.FlowVacation;
/**
 * 请假Dao
 * @author qiqi
 * @date 2016-12-28 11：11：11
 */
public interface IFlowVacationDao {
	/**
	 * 添加请假流程
	 * @param flowVacation
	 * @return
	 */
	public Long addFlowVacation(FlowVacation flowVacation);
	/**
	 * 获取请假对象
	 * @param params
	 * @return
	 */
	public FlowVacation getFlowVacationByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: getFlowVacationListByParams 
	 * @Description: 根据参数（类别、时间、发起人）获取请假数据列表
	 * @param params
	 * @return 
	 * @throws
	 */
	public List<FlowVacation> getFlowVacationListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: getTotalNumByParams 
	 * @Description: 根据参数（类别、时间、发起人）获取请假详情数据总数
	 * @param params
	 * @return 
	 * @throws
	 */
	public int getTotalNumByParams(Map<String, Object> params);
}
