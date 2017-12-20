package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.staff.UserCount;
/**
 * 员工表dao
 * 
 * @author 维斯
 *	2016年10月17日	 下午6:13:31
 */
public interface IUserDao {
	
	/**
	 * 统计总数
	 * @author 维斯
	 * @return
	 * 2016年10月20日  下午4:14:54
	 */
	int countUser(Map<String,Object> map);
	/**
	 * 查询单个公司下的所有员工号
	 * @author 维斯
	 * 2016年11月29日   下午5:41:03
	 */
    List<String> selectUserNos(Long companyId);
    /**
     * 查询单个公司下的所有手机号
     * @author 维斯
     * @param companyId
     * @return
     * 2016年12月15日   下午8:33:22
     */
    List<String> selectUserPhone(Long companyId);
    /**
     * 批量删除
     * @author 维斯
     * @param map
     * @return
     * 2016年11月4日  下午3:12:00
     */
    int deleteByIds(String[] ids);
    /**
     * 添加
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:08:47
     */
    int insert(User record);
    
    /**
     * 批量插入
     * @author 维斯
     * 2016年11月29日   下午6:26:11
     */
    boolean inserts(List<User> users);
    /**
     * 按条件查询
     * @author 维斯
     * @param example
     * @return
     * 2016年10月18日  上午10:08:43
     */
    List<User> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param m
     * @return
     * 2016年10月18日  上午10:08:40
     */
    User seleteById(Map<String,Object> map);
    /**
     * 按参数更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月18日  上午10:08:36
     */
    int updateByExample(User record);
    /**
     * php登录id更新员工信息
     * @author 维斯
     * @param record
     * @return
     * 2016年12月27日   上午10:40:21
     */
    int updateByPHP(User record);
    /**
     * 性别统计 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年10月28日  下午4:06:02
     */
    List<UserCount> countSex(Map<String,Object> map);
    /**
     * 状态统计 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年10月28日  下午4:06:21
     */
    List<UserCount> countStatus(Map<String,Object> map);
    /**
     * 学历统计 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年10月28日  下午4:06:18
     */
    List<UserCount> countEduational(Map<String,Object> map);
    /**
     * 在职年份 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年10月28日  下午4:06:13
     */
    List<UserCount> countYear(Map<String,Object> map);
    /**
     * 职等 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年12月12日   下午3:04:21
     */
    List<UserCount> countDutyType(Map<String,Object> map);
    /**
     * 年龄统计 公司id 组织id
     * @author 维斯
     * @param count
     * @return
     * 2016年10月28日  下午4:06:07
     */
    List<UserCount> countAge(Map<String,Object> map);
    /**
     * 查询头像url
     * @author 维斯
     *
     * 2016年11月10日   下午5:29:01
     */
    List<FileInfo> selectUrl(List<Long> ids);
    
    /**
     * 统计在职人数
     * @author 维斯
     * 2016年12月5日   下午5:50:47
     */
    int countStatusNums(Map<String,Object> map);
    
    public void updateUserInfoByLoginUserId(User user);
    
    /**
     * 根据部门ID获取部门人数（deptId为空时查询公司全部人数）
     * @param params
     * @return
     */
    public int getUserCountByDeptId(Map<String, Object> params);
    
    /**
     * 根据loginUserId和手机号获取用户信息（updateUserForAPP方法专用）
     * @param params
     * @return
     */
    public User getUserForAPP(Map<String, Object> params);
    
    /**
     * 根据id更新用户信息（updateUserForAPP方法专用）
     * @param params
     */
    public void updateUserForAPP(User user);
    
    /**
     * 根据用户ID批量获取用户信息
     * @param params
     * @return
     */
    public List<User> selectUserListById(Map<String, Object> params);
    
    /**
     * 根据用户ID批量获取用户部门信息数组
     * @param params
     * @return
     */
    public String[] selectUserDeptIdArrayById(Map<String, Object> params);
    
    
    /**
     * 按条件查询(与selectByExample一致就是APP认为给予的废数据太多，重新设置一个映射MAP)
     * @author 琦玉
     * @param example
     * @return
     * 2017年4月17日  
     */
    List<User> selectNewByExample(Map<String,Object> map);
    /**
     * 根据公司id和loginUserId从oa_user表中查询登录用户
     * @param params
     * @return
     */
	User selectBycompanyIdAndLoginUserId(Map<String, String> params);
	

}