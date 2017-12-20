package com.crm.service;

import java.util.List;

import com.crm.model.Sys_user_t;
/**
 * 系统用户信息管理
 * @author yuduan
 *
 */
public interface UsersService extends BaseService {
 
	/**
	 * 系统用户-保存更新
	 * @param u 用户
	 */
	void saveUser(Sys_user_t u);
	
	
	/**
	 *  系统用户-删除
	 * @param u
	 */
	void delUser(Sys_user_t u);
	/**
	 * 系统用户-查询
	 * @param id 用户序号
	 */
	List<Sys_user_t> showUser(int id);
	
	
	Sys_user_t findUserByUserName(String username);
}
