package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.workStage.MessagePushLogsPojo;

/**
 * 
 * @ClassName: IMessagePushLogsDao 
 * @Description: 消息推送数据访问层 
 * @author qiqi 
 * @date 2016年9月23日 上午10:01:58 
 *
 */

@Repository
public interface IMessagePushLogsDao {
	/**
	 * 推送消息时记录消息记录到数据库
	 * @param list
	 * @throws DAOException
	 */
	public void insertMessagePushLogs(List<Map<String, Object>> list) ;
	/**
	 * 删除公告的时候，也删除公告的推送消息 type为1
	 * @param announceId
	 */
	public void delMessagePushLogs(Map<String, Object> params);
	/**
	 * 根据条件搜索消息记录
	 * @param params
	 * @throws DAOException
	 */
	public List<MessagePushLogsPojo> getMessagePushLogsByParams(Map<String, Object> params);
	/**
	 * 根据条件搜索消息记录条数
	 * @param params
	 * @return
	 */
	public int getMessagePushLogsCountByParams(Map<String, Object> params);
	

}
