package com.crm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.model.Sys_right_t;
import com.crm.model.Sys_role_t;
import com.crm.model.Sys_user_t;
import com.crm.service.RightService;

@Service("RightService")
public class RightServiceImpl  extends BaseServiceImpl implements RightService{

	/**
	 * 添加系统权限
	 */
	public void saveSysRight(Sys_right_t s){
		saveOrUpdate(s);
	}
	
	/**
	 * 删除系统权限
	 */
	public void deleteSysRight(Sys_right_t s)
	{
		delete(s);
	}
	
	/**
	 * 修改系统权限
	 */
	public void modSysRight(Sys_right_t s)
	{
		saveOrUpdate(s);
	}
	
	/**
	 * 获取系统权限
	 * @return
	 */
	public List<Sys_right_t> getSysRight(){
		return getQuery("from Sys_right_t s order by s.sys_right_t.right_id").list();
	}
	
	/**
	 * 获取权限的顶级模块
	 */
	public List<Sys_right_t> getTopSysRight(){
		return getQuery("from Sys_right_t s where s.sys_right_t=null").list();
	}
	
	
	/**
	 * 添加角色
	 */
	public void saveRole(Sys_role_t s){
		saveOrUpdate(s);
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(Sys_role_t s){
		delete(s);
	}
	
	/**
	 * 修改角色
	 */
	public void modRole(Sys_role_t s){
		saveOrUpdate(s);
	}
	
	/**
	 * 获取角色
	 * @return
	 */
	public List<Sys_role_t> getRole(){
		return getQuery("from Sys_role_t s").list();
	}
	
	/**
	 * 获取用户
	 * @return
	 */
	public List<Sys_user_t> getUser(){
		return getQuery("from Sys_user_t s").list();
	}
	
	/**
	 * 保存用户
	 * @return
	 */
	public void saveUser(Sys_user_t s){
		saveOrUpdate(s);
	}
	
}

