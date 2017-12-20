package com.aiiju.service.workStage;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 审批流程接口
 * 
 * @author qiqi
 * @date 2017-01-03 11:11:11
 */
public interface IFlowInfoService {
	/**
	 * 发布审批
	 * 
	 * @param params
	 * @return 0成功
	 */
	public Map<String,Object> addFlowInfo(Map<String, Object> params);

	/**
	 * 处理审批
	 * 
	 * @param params
	 * @return
	 */
	public int dealFlowInfo(Map<String, Object> params);

	/**
	 * 撤回
	 * 
	 * @param params
	 * @return
	 */
	public int withdrawFlowInfo(Map<String, Object> params);

	/**
	 * 获取审批列表
	 * 
	 * @param params
	 *            type 1我收到的 2我发出的 3待我处理
	 * @return
	 */
	public Map<String, Object> getFlowInfoList(Map<String, Object> params);

	/**
	 * 待处理列表
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getToDoApplicationList(Map<String, Object> params);

	/**
	 * 获取详情
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFlowInfoDetailForApp(Map<String, Object> params);

	/**
	 * 获取请假或者报销选择框类型
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> getFlowInfoEnums(Map<String, Object> params);
	
	/**
     * 
     * @Title: getFlowInfoListByParams 
     * @Description: 根据参数（时间、发起人）获取报销详情数据
     * @param params
     * @return 
     * @throws
     */
    public Map<String, Object> getFlowReimburseListByParams(Map<String, Object> params);
    
    /**
     * 
     * @Title: exportFlowReimburseExcel 
     * @Description: 导出报销Excel文件
     * @param params
     * @return 
     * @throws
     */
    public HSSFWorkbook exportFlowReimburseExcel(Map<String, Object> params);
    
    /**
     * 
     * @Title: getFlowVacationListByParams 
     * @Description: 根据参数（时间、发起人）获取请假详情数据
     * @param params
     * @return 
     * @throws
     */
    public Map<String, Object> getFlowVacationListByParams(Map<String, Object> params);
    
    /**
     * 
     * @Title: exportFlowVacationExcel 
     * @Description: 导出请假Excel文件
     * @param params
     * @return 
     * @throws
     */
    public HSSFWorkbook exportFlowVacationExcel(Map<String, Object> params);
}
