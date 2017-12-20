package com.aiiju.dao.oa.workStage;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.workStage.Announcement;
/**
 * 公告
 * @author 维斯
 *
 * 2016年12月28日   下午2:38:24
 */
public interface IAnnouncementDao {

	/**
	 * 添加 返回主键
	 * @author 维斯
	 * @param record
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
	int addAnnouncement(Announcement record);
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年12月28日   下午2:35:21
	 */
    int deleteAnnouncementId(Long id);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年12月28日   下午2:35:21
     */
    int updateAnnouncement(Announcement record);
    /**
     * 按条件查询
     * @author 维斯
     * @param 
     * @return
     * 2016年12月28日   下午2:35:21
     */
    List<Announcement> selectAnnouncement(Map<String ,Object> map);
    /**
     * 通过用户id查询
     * @author 维斯
     * @param id
     * @return
     * 2017年1月3日 	下午5:39:33
     */
    List<Announcement> getAnnouncements(Map<String ,Object> map);
    /**
     *  通过主键id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年12月28日   下午2:35:21
     */
    Announcement seleteAnnouncementId(Long id);
	
}
