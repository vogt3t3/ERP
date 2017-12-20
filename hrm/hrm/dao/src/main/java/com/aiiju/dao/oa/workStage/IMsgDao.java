package com.aiiju.dao.oa.workStage;

import java.util.Map;

/**
 * 
 * @ClassName: IMsgDao 
 * @Description: 消息记录数据访问层
 * @author 哪吒 
 * @date 2016年12月28日 下午5:34:56 
 *
 */

public interface IMsgDao {

	/**
	 * 批量新增消息日志记录
	 * @param params
	 */
	public void batchAddMsgInfo(Map<String, Object> params);
	
	/**
	 * 批量更新msg状态
	 * @param params
	 */
	public void batchUpdateMsg(Map<String, Object> params);
}
