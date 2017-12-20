package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.LeaveUser;
/**
 * 离职员工
 * @author 维斯
 * 
 * 2017年2月14日 	上午9:46:51
 */
public interface ILeaveUserDao {

	/**
	 * 统计总数
	 * @author 维斯
	 * @return
	 */
	int countLeaveUser(Map<String,Object> map);
	
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
    int insertLeaveUser(Map<String,Object> map);
    
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:08:43
     */
    List<LeaveUser> selectByLeaveUsers(Map<String,Object> map);
	
}
