package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.ReplyLog;

/**
 * 
 * @ClassName: IReplyLogDao 
 * @Description: 回复日志业务数据访问层 
 * @author 哪吒 
 * @date 2016年12月28日 下午6:05:23 
 *
 */

public interface IReplyLogDao {

	/**
	 * 获取用户收到的回复列表总数
	 * @param params
	 * @return
	 */
	public int getReplyListCount(Map<String, Object> params);
	
	/**
	 * 获取未读回复的数量（收到的回复右上角角标）
	 * @param params
	 * @return
	 */
	public int getUnreadReplyCount(Map<String, Object> params);
	
	/**
	 * 更新回复日志记录的状态（已读，撤回，删除）,请求参数：companyId,userId
	 * @param params
	 */
	public void updateReplyLogStatus(Map<String, Object> params);
	
	/**
	 * 获取未读状态的回复日志记录列表
	 * @param params
	 * @return
	 */
	public List<ReplyLog> getReplyLogList(Map<String, Object> params);
	
	/**
	 * 更新回复日志记录的状态（已读，撤回，删除）,请求参数：companyId,replyId
	 * 注：不和上面的更新状态方法合并的理由，因为userId是存在session中的，必定存在的，而在这个方法中是不能有userId的，不然更新数据不对
	 * @param params
	 */
	public void updateReplyLogStatusByReplyId(Map<String, Object> params);
	
	/**
	 * 批量新增回复日志记录
	 * @param params
	 */
	public void batchAddReplyLogInfo(Map<String, Object> params);
	
	/**
	 * 批量更新状态
	 * @param params
	 */
	public void batchUpdateReplyLogStatus(Map<String, Object> params);
}
