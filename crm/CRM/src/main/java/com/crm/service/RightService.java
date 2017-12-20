package com.crm.service;

import java.util.List;

import com.crm.model.Sys_right_t;
import com.crm.model.Sys_role_t;
import com.crm.model.Sys_user_t;

public interface RightService extends BaseService{

	
	/**
	 * 添加系统权限
	 */
	void saveSysRight(Sys_right_t s);
	
	/**
	 * 删除系统权限
	 */
	void deleteSysRight(Sys_right_t s);
	
	/**
	 * 修改系统权限
	 */
	void modSysRight(Sys_right_t s);
	
	/**
	 * 获取系统权限
	 * @return
	 */
	List<Sys_right_t> getSysRight();
	
	/**
	 * 获取权限的顶级模块
	 */
	List<Sys_right_t> getTopSysRight();
	
	
	/**
	 * 添加角色
	 */
	void saveRole(Sys_role_t s);
	
	/**
	 * 删除角色
	 */
	void deleteRole(Sys_role_t s);
	
	/**
	 * 修改角色
	 */
	void modRole(Sys_role_t s);
	
	/**
	 * 获取角色
	 * @return
	 */
	List<Sys_role_t> getRole();
	
	
	/**
	 * 获取用户
	 * @return
	 */
	List<Sys_user_t> getUser();
	
	/**
	 * 保存用户
	 * @return
	 */
	void saveUser(Sys_user_t s);
}
