package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.Memory;

/**
 * 
 * @ClassName: IMemoryDao 
 * @Description: 最近收件部门/人数据访问层
 * @author 哪吒 
 * @date 2016年12月28日 下午5:34:56 
 *
 */

public interface IMemoryDao {

	/**
	 * 查询用户行为数据总数（请求参数：companyId,userId,sceneType,sendToDept,sendToPerson）
	 * companyId,userId可以从session中获取
	 * @param params
	 * @return
	 */
	public int getMemoryInfoCount(Map<String, Object> params);
	
	/**
	 * 获取最近收件部门/人列表
	 * @param params
	 * @return
	 */
	public List<Memory> getMemoryInfoList(Map<String, Object> params);
	
	/**
	 * 获取当前status为1的用户行为记录
	 * @param params
	 * @return
	 */
	public Memory getCurMemoryInfo(Map<String, Object> params);
	
	/**
	 * 新增最近收件部门/人
	 * @param params
	 */
	public void addMemoryInfo(Map<String, Object> params);
	
	/**
	 * 更新用户行为记录的状态
	 * @param params
	 */
	public void updateMemoryInfoStatus(Map<String, Object> params);
	
	/**
	 * 根据ID删除用户行为记录
	 * @param params
	 */
	public void delMemoryInfoById(Map<String, Object> params);
}
