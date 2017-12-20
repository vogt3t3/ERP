package com.aiiju.dao.oa.permission;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.permission.Role;

/**
 * @description 角色Dao
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 */
public interface IRoleDao {
	/**
	 * 添加角色
	 * @param param
	 * @return
	 */
	int addRole(Role role);
	/**
	 * 删除角色
	 * @param param
	 * @return
	 */
	int delRole(Map<String,Object> param);
	/**
	 * 更新角色
	 * @param param
	 * @return
	 */
	int updateRole(Role role);
	/**
	 * 根据条件获取角色列表
	 * @param param
	 * @return
	 */
	List<Role> getRolesByParam(Map<String,Object> param);
	/**
	 * 根据userId获取角色
	 * @param param
	 * @return
	 */
	Role getRoleByUserId(Map<String,Object> param);
	
	/**
	 * 为用户设置角色
	 * @param params
	 */
	public void insertRoleOfUser(Map<String, Object> params);
	
	/**
	 * 获取角色信息(多条)
	 * @param params
	 */
	public List<Role> getRoleInfoListByParams(Map<String, Object> params);
	
	/**
	 * 根据ID获取角色信息(单条)
	 * @param params
	 * @return
	 */
	public Role getRoleInfoById(Map<String, Object> params);
	
	/**
	 * 批量新增角色权限
	 * @param params
	 */
	public void batchAddRolePermit(Map<String, Object> params);
	
	/**
	 * 根据companyId和roleId删除权限
	 * @param params
	 */
	public void delRolePermit(Map<String, Object> params);
	
	/**
	 * 删除角色用户关联关系（请求参数：roleId）
	 * @param params
	 */
	public void delRoleUser(Map<String, Object> params);
	
}
