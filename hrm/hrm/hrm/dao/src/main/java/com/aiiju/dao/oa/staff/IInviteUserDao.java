package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.InviteUser;
/**
 * 邀请dao
 * @author 维斯
 * 
 * 2017年2月14日 	下午3:01:48
 */
public interface IInviteUserDao {

	/**
	 * 统计总数
	 * @author 维斯
	 * @return
	 */
	int countInviteUser(Map<String,Object> map);
	
    /**
     * 批量删除
     * @author 维斯
     * @param map
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 添加
     * @author 维斯
     * @param record
     * @return
     */
    int insertInviteUser(Map<String,Object> map);
    
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:08:43
     */
    List<InviteUser> selectByInviteUsers(Map<String,Object> map);
    
    /**
     * 修改邀请状态
     * @author 维斯
     * @param map
     * @return
     * 2017年2月14日 	下午3:03:20
     */
    int updateInviteStatus(InviteUser inviteUser);
	
}
