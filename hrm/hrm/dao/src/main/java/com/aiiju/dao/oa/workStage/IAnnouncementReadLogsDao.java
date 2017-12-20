package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.AnnouncementReadLogs;
/**
 * 公告记录
 * @author 维斯
 *
 * 2016年12月28日   下午2:38:11
 */
public interface IAnnouncementReadLogsDao {

	/**
	 * 添加 返回主键
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
	int addAnnouncementReadLogs(AnnouncementReadLogs record);
	/**
	 * 设置单个人员下公告置顶
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年1月4日 	下午1:34:47
	 */
	int isTopAndRead(Map<String,Object> map);
	/**
	 * 批量插入
	 * @author 维斯
	 * @param list
	 * @return
	 * 2017年1月4日 	上午10:39:36
	 */
	int insertAnnouncementReadLogs(List<AnnouncementReadLogs> list);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
    int deleteAnnouncementReadLogsId(Long id);
    /**
     * 统计单个人员总数
     * @author 维斯
     * @param map
     * @return
     * 2017年1月4日 	下午5:39:31
     */
    int  countAnnouncementReadLogs(Long recipient);
    /**
     *  通过id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年12月28日   下午2:35:21
     */
    AnnouncementReadLogs selectAnnouncementReadLogsId(Long id);
    /**
     * 统计读取记录
     * @param map
     * @return
     */
    List<AnnouncementReadLogs> countIsRead(Long announcementId);
	
}
