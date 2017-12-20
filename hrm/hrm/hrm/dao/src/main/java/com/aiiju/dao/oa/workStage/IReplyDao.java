package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.Reply;

/**
 * 
 * @ClassName: IReplyDao 
 * @Description: 回复业务数据访问层
 * @author 哪吒 
 * @date 2016年12月28日 下午5:34:56 
 *
 */

public interface IReplyDao {

	/**
	 * 获取用户收到的回复列表
	 * @param params
	 * @return
	 */
	public List<Reply> getReplyList(Map<String, Object> params);
	
	/**
	 * 根据pid获取用户收到的回复列表
	 * @param params
	 * @return
	 */
	public List<Reply> getReplyListByPid(Map<String, Object> params);
	
	/**
	 * 根据pid获取用户收到的回复列表总数
	 * @param params
	 * @return
	 */
	public int getReplyListCountByPid(Map<String, Object> params);
	
	/**
	 * 根据id更新回复的状态（是否删除、是否同意、是否撤回）
	 * @param params
	 */
	public void updateReplyStatusById(Map<String, Object> params);
	
	/**
	 * 新增回复详细
	 * @param reply
	 */
	public Long addReplyInfo(Reply reply);
	
	/**
	 * 根据ID获取回复详情
	 * @param params
	 * @return
	 */
	public Reply getReplyById(Map<String, Object> params);
	
}
