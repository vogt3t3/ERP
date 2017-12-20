package com.aiiju.dao.oa.register;
import java.util.Map;
import com.aiiju.bean.oa.register.RegisterUser;
/**
 * 用户dao
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public interface IRegisterUserDao {
	
	
    /**
     * 添加注册用户
     * @param registerUser
     * @return
     */
    public Long addRegisterUser(Map<String, Object> params);
    /**
     * 修改注册用户
     * @param params
     * @return
     */
    public int updateRegisterUser(Map<String, Object> params);
    
    /**
     * 根据手机号查询注册用户
     * @param params
     * @return
     */
    public RegisterUser getRegisterUserByParams(Map<String, Object> params);

}