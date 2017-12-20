package com.aiiju.service.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.ReceivedReply;

/**
 * 
 * @ClassName: IReplyService 
 * @Description: 回复业务接口层
 * @author 哪吒 
 * @date 2017年1月3日 上午9:23:09 
 *
 */

public interface IReplyService {
	
	/**
	 * 获取未读回复的数量（收到的回复右上角角标）
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUnreadReplyCount(Map<String, Object> params);

	/**
	 * 获取用户收到的回复列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getReplyList(Map<String, Object> params);
	
	/**
	 * 根据ID获取回复数据（单条）
	 * @param params
	 * @return
	 */
	public Map<String, Object> getReplyByPid(Map<String, Object> params);
	
	/**
	 * 根据业务id获取回复列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getReplyListByScene(Map<String, Object> params);
	
	/**
	 * 根据业务场景获取回复的统计信息（每个业务场景里面回复(n)，n的值）
	 * @param params
	 * @return
	 */
	public Map<String, Object> getReplyCountByScene(Map<String, Object> params);
	
	/**
	 * 新增回复
	 * @param params
	 */
	public Map<String, Object> addReply(Map<String, Object> params);
	
	/**
	 * 撤销回复
	 * @param params
	 */
	public Map<String, Object> cancelReply(Map<String, Object> params);
	
	/**
	 * 批量更新回复的状态
	 * @param params
	 * @return
	 */
	public Map<String, Object> batchUpdateReadStatus(Map<String, Object> params);
	
	
	/**
	 * 收到的回复列表
	 * @param params
	 * @return
	 */
	

	Map<String, Object> getReceivedReplyList(Map<String, Object> params);
}
