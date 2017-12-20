package com.aiiju.service.register;

import java.util.Map;

import com.aiiju.bean.oa.staff.User;
/**
 * 注册接口
 * @author qiqi
 * @data 2016-12-28 11:11:11
 */
public interface IRegisterService {
	/**
	 * 发送手机验证码
	 * @param params
	 * @return
	 */
	public Map<String,Object> sendCheckCode(Map<String, Object> params);
	/**
	 * 验证校验码
	 * @param params
	 * @return
	 */
	public Map<String, Object> validateCheckCode(Map<String, Object> params);
	
	/**
	 * 用户注册
	 * @param params
	 * @return
	 */
	public Map<String, Object> register(Map<String, Object> params);
	/**
	 * 用户登录
	 * @param params  phone  password
	 * @return
	 */
    public Map<String, Object> login(Map<String, Object> params);
    /**
     * 创建公司以及默认创建 顶级组织
     * @param params 
     * @return
     */
    public Map<String, Object> addCompany(Map<String, Object> params);
    /**
     * 忘记密码时，获取初始密码
     * @param params
     * @return
     */
    public Map<String,Object> getInitUserPwd(Map<String,Object> params);
    /**
     * 修改用户密码
     * @param params
     * @return
     */
    public Map<String,Object> updateUserPwd(Map<String,Object> params);
    /**
     * 创建一个扫码会话SessionId
     * @param params
     * @return
     */
    public Map<String,Object> createLoginSessionId(Map<String, Object> params);
    /**
     * 用户同意授权
     * @param params
     * @return
     */
    public Map<String,Object> agreeLoginBySessionId(Map<String,Object> params);
    /**
     * 用户拒绝授权
     * @param params
     * @return
     */
	public Map<String,Object> refuseLoginBySessionId(Map<String,Object> params);
	/**
	 * 尝试输出session
	 */
	public Map<String,Object> flushSession(Map<String,Object> params);
	
	
	/**
	 * 跟换手机号
	 */
	
	Map<String, Object> updatePhone(Map<String, Object> params);
	
	
	 
	
}
