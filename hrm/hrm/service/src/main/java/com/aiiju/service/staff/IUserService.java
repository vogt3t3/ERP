package com.aiiju.service.staff;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.aiiju.bean.oa.staff.User;
/**
 * 
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:12:52
 */
public interface IUserService {
    /**
     * 批量删除
     * @author 维斯
     * @param map
     * @return
     * 2016年11月4日  下午3:25:04
     */
    int deleteByIds(Map<String,Object> map);
    /**
     * 添加 并返回主键
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:13:07
     */
    String addUser(Map<String,Object> map) ;
    /**
     * 大系统注册用户添加
     * @author 维斯
     * @param map
     * @return
     * 2016年12月9日   下午4:57:08
     */
    String addUserLogIn(Map<String,Object> map);
	/**
	 * 大系统注册用户更新
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年12月27日   上午10:15:08
	 */
	int updateUserLogIn(Map<String, Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:13:05
     */
    Map<String, Object> getUsers(Map<String,Object> map);
    /**
     * 通过公司id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:13:01
     */
    Map<String, Object> getUserById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:12:57
     */
    int updateUser(Map<String,Object> map);
    /**
     * 人员结构分析
     * @author 维斯
     * @param map
     * @return
     * 2016年10月25日  下午5:07:25
     */
    Map<String, Map<String, Integer>> getUserByDepartment(Map<String,Object> map);
    /**
     * 导出xls文件
     * @author 维斯
     * @param map
     * @return
     * 2016年10月27日  下午3:53:18
     */
    Workbook ExportExceByUsers(Map<String, Object> map);
    /**
     * 导出统计
     * @author 维斯
     * @param map
     * @return
     * @throws Exception
     * 2016年11月1日  下午1:48:54
     */
    Workbook exportExceByUsersCount(Map<String, Object> map) ;
    /**
     * 导出人员模板
     * @author 维斯
     * @return
     * 2016年11月1日  下午1:48:30
     * @throws Exception 
     */
	Workbook exportTemplate(Map<String, Object> map);
	/**
	 * 导入模板数据
	 * @author 维斯
	 * @param map
	 * 2016年11月2日  下午2:05:46
	 * @throws Exception 
	 */
	boolean leadTemlate(Map<String, Object> map);
	/**
	 * 统计人员在职 离职转正 当前月
	 * @author 维斯
	 * 2016年12月5日   下午5:31:53
	 */
	Map<String, Object> countWork(Map<String, Object> map);
	/**
	 * 人员人事事务添加 更新人员
	 * @author 维斯
	 * 2016年12月5日   下午5:33:14
	 */
	int updateAffairs(Map<String, Object> map);
	
	/**
	 * 登录时用--勿删
	 * @param map
	 * @return
	 */
	public User getUser(Map<String, Object> map)throws Exception;
	
	/**
	 * app注册时申请加入公司，新增/更新用户信息（此接口仅供aiijuDSB调用）
	 * @return
	 */
	public boolean updateUserForAPP(Map<String, Object> params);
	
	/**
	 * 查看离职员工
	 * @author 维斯
	 * @param map
	 * @return
	 * 2017年2月14日 	上午9:47:11
	 */
    Map<String, Object> getLeaveUsers(Map<String,Object> map);
    /**
     * 查看邀请员工
     * @author 维斯
     * @param map
     * @return
     * 2017年2月14日 	上午9:48:05
     */
    Map<String, Object> getInviteUsers(Map<String,Object> map);
    /**
     * 获取邀请详细信息
     * @author 维斯
     * @param map
     * @return
     * 2017年2月24日 	下午2:45:42
     */
    Map<String, Object> getInviteById(Map<String,Object> map);
    /**
     * 是否邀请
     * @author 维斯
     * @param map
     * @return
     * 2017年2月16日 	下午5:56:18
     */
    int isInviteUser(Map<String,Object> map);
    /**
     * 修改邀请状态
     * @author 维斯
     * @param map
     * @return
     * 2017年2月17日 	下午1:45:27
     */
    int isInviteStatus(Map<String,Object> map);
    
    /**
     * 重新入职
     * @author 维斯
     * @param map
     * @return
     * 2017年2月14日 	下午4:01:01
     */
    int entrant(Map<String,Object> map);
    
    /**
     * 新版通讯录（分成管理员数组，员工数组（无部门），员工数组（有部门））
     * @author 琦玉
     * @param map
     * @return
     * 2017年4月17日 	下午4:01:01
     * @throws Exception 
     */
    Map<String, Object> getNewUsers(Map<String, Object> map) throws Exception;
    
    /**
     * 修改登录用户的密码
     * @throws Exception 
     */
    Map<String, Object> updateLoginUserPwd(Map<String,Object> params) throws Exception;
    
    /**
     * 体验账号:一期实现方式,凡是注册用户均有15天免费体验的机会,
     * 此方法目前主要判断免费使用是否到期
     */
	Map<String, Object> freeIsOverTime(Map<String, Object> params);
	
	/**
	 * 根据公司id和loginUserId从oa_user表中查询登录用户
	 * @param map
	 * @return
	 */
	public User getUserBycompanyIdAndLoginUserId(Map<String, String> params);
	
}
