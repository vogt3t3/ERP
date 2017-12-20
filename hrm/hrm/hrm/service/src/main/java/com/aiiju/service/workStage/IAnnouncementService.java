package com.aiiju.service.workStage;

import java.util.Map;
/**
 * 公告service
 * @author 维斯
 * 
 * 2017年1月5日 	下午3:57:31
 */
public interface IAnnouncementService {
	/**
	 * 获取公告列表
	 * @author 维斯
	 * @param map
	 * @return 
	 * 2017年1月3日 	下午2:37:05
	 */
	Map<String,Object> getAnnouncements(Map<String,Object> map);
	/**
	 * 更新公告
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月3日 	下午2:38:26
	 */
	boolean updateAnnouncement(Map<String,Object> map);
	/**
	 * 置顶公告
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月4日 	下午12:03:01
	 */
	boolean isTopAnnouncement(Map<String,Object> map);
	/**
	 * 添加公告
	 * @author 维斯
	 * @param map
	 * @return 0失败 1成功
	 * 2017年1月3日 	下午2:39:15
	 */
	int addAnnouncement(Map<String,Object> map);
	/**
	 * 删除公告
	 * @author 维斯
	 * @param map
	 * @return 0失败 1成功
	 * 2017年1月3日 	下午2:39:46
	 */
	boolean deleteAnnouncement(Map<String,Object> map);
	/**
	 * 单个公告详情
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月3日 	下午2:41:27
	 */
	Map<String,Object> getAnnouncementId(Map<String,Object> map);
}
