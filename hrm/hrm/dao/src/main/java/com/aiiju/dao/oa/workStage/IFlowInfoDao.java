package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.FlowInfo;
/**
 * 审批Dao
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public interface IFlowInfoDao {
	/**
	 * 添加审批信息
	 * @param flowInfo
	 * @return
	 */
	public Long addFlowInfo(FlowInfo flowInfo);
	/**
	 * 更新审批信息
	 * @param flowInfo
	 * @return
	 */
	public int updateFlowInfo(FlowInfo flowInfo);
	/**
	 * 获取审批列表
	 * @param params
	 * @return
	 */
	public List<FlowInfo> getFlowInfoList(Map<String,Object> params);
	/**
	 * 获取审核列表条数
	 * @param params
	 * @return
	 */
	public int getFlowInfoCounts(Map<String,Object> params);
	/**
	 * 
	 * @param params
	 * @return
	 */
	public FlowInfo getFlowInfoByParams(Map<String, Object> params);
	/**
	 * 获取报销列表（请求参数，allUserList,companyId,flowType）
	 * @param params
	 * @return
	 */
	public List<FlowInfo> getFlowInfoListByParams(Map<String,Object> params);
	/**
	 * 获取报销列表条数（请求参数，allUserList,companyId,flowType）
	 * @param params
	 * @return
	 */
	public int getFlowInfoCountsByParams(Map<String,Object> params);
}
