package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.ReceivedReply;

/**
 * 
 * @ClassName: IReceivedReply 
 * @Description: 收到的回复DAO 
 * @author 琦玉
 * @date 2017年4月22日 
 *
 */

public interface IReceivedReplyDao {

	
	
	
	/**
	 * 查询收到的回复List
	 * @param params
	 */
	public List<ReceivedReply> getReceivedReplyList(Map<String, Object> params);

	
	/**
	 * 查询收到的回复List总数（分页使用）
	 * @param params
	 */
	public int getReceivedReplyListCount(Map<String, Object> params);
}
