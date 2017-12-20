package com.aiiju.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: IPermissionManageService
 * @Description: 权限设置
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 *
 */
@Repository
public interface IPermissionManageService {
     
	//获取用户权限
	public Map<String,Object> getUserPermission(Map<String,Object> params);
	
	//获取权限树
	public Map<String,Object> getPermissionTree(Map<String,Object> params);
	
	//取得用户对于的菜单树
	public Map<String,Object> getUserMenuTree(Map<String,Object> params);
	
	//判断某操作是否有权限操作
	public Map<String,Object> judgePermission(Map<String,Object> params);
	
	/**
	 * 为用户设置角色
	 * @param params
	 * @return
	 */
	public Map<String, Object> setRoleForUser(Map<String, Object> params);
	
	/**
	 * 新增或设置角色
	 * @param params
	 * @return
	 */
	public Map<String, Object> addOrUpdateRole(Map<String, Object> params);
	
	/**
	 * 根据角色ID删除角色
	 * @param params
	 * @return
	 */
	public Map<String, Object> delRoleById(Map<String, Object> params);
	
	/**
	 * 根据角色ID获取角色信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getRoleInfoById(Map<String, Object> params);
	
	/**
	 * 获取当前登录用户的所有角色(AJUC新接口的调用方式)
	 * @param params
	 * @return
	 */
	public Map<String,Object> getUserRolesByAjuc(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取用户的菜单树(AJUC新接口的调用方式)
	 * @param params
	 * @return
	 */
	public Map<String,Object> getUserMenusByAjuc(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取用户的权限(AJUC新接口的调用方式)
	 * @param params
	 * @return
	 */
	public Map<String,Object> getUserPermissionByAjuc(Map<String,Object> params) throws Exception;
	
	/**
	 * 角色管理列表(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCompanyRolesList(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增一个角色(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> addCommonRole(Map<String, Object> params) throws Exception ;
	
	/**
	 * 编辑一个角色(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateRole(Map<String, Object> params) throws Exception;
	/**
	 * 删除一个角色(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> deleteRole(Map<String, Object> params) throws Exception;
	/**
	 * 获取员工授权列表信息(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> getEmpSetPermitList(Map<String, Object> params) throws Exception;
	/**
	 * 设置员工权限(新版UI)
	 * @param params
	 * @return
	 */
	public Map<String, Object> setEmpPermit(Map<String,Object> params) throws Exception;
	/**
	 * 编辑角色时弹窗---详情数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> roleDetail(Map<String, Object> params) throws Exception;
}
