package com.aiiju.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.permission.MenuTree;
import com.aiiju.bean.oa.permission.Permit;
import com.aiiju.bean.oa.permission.PermitTree;
import com.aiiju.bean.oa.permission.Role;
import com.aiiju.bean.oa.permission.RoleManagerBo;
import com.aiiju.bean.oa.permission.UserRoleBo;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.permission.IMenuDao;
import com.aiiju.dao.oa.permission.IPermitDao;
import com.aiiju.dao.oa.permission.IRoleDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.IPermissionManageService;
import com.aiiju.util.business.TreeUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.aiiju.util.http.HttpClientDownload;
import com.aiiju.util.http.ReadJsonData;
import com.aiiju.util.page.Pagination;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 权限实现类
 * 
 * @author qiqi
 * @date 2016-10-26 11:11:11
 */
@Service("permissionManageService")
public class PermissionManageServiceImpl implements IPermissionManageService {
	
	@Autowired
	private IPermitDao permitDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IMenuDao menuDao;
	
	@Autowired
	private IUserDao userDao;
	
	//------------------------------------------------------------NZ--START-----------------------------------------------------------------
	
	/**
	 * 请求参数：roleId(多个用","隔开),userId
	 */
	@Override
	public Map<String, Object> setRoleForUser(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取roleIds
		if (StringUtils.isNotEmpty((String) params.get("roleId")) && StringUtils.isNotEmpty((String) params.get("userId"))) {
			String roleIds = (String) params.get("roleId");
			List<String> roleIdList = Arrays.asList(roleIds.split(","));
			// 查询数据库中符合条件的roleId,请求参数roleId
			params.put("roleIds", roleIds);
			List<Long> roleIdListFromDB = getRoleList(params);
			if (null != roleIdListFromDB && !roleIdListFromDB.isEmpty()) {
				if (CommonUtil.compareList(roleIdList, roleIdListFromDB)) {
					// perm_role_user批量新增数据
					try {
						params.put("roleIdList", roleIdList);
						roleDao.insertRoleOfUser(params);
					}catch (Exception e) {
						throw new BizException(BizExceptionMessage.DB_ERROR, "新增用户角色失败");
					}
				} else { // 角色中存在错误数据。eg：请求参数中的roleId在数据库中不存在
					throw new BizException(BizExceptionMessage.DB_ERROR, "存在非法数据");
				}
			} else { // 角色不存在
				throw new BizException(BizExceptionMessage.DB_ERROR, "数据库中无此角色");
			}
		} else {
			throw new BizException(BizExceptionMessage.PARAM_ERROR);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> addOrUpdateRole(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId = (String) params.get("id");
		if (StringUtils.equals("0", roleId)) { // 角色ID为0时，新增
			addOrUpdateRoleWithTransactional(params, "1");
		} else { // 角色ID不为0时，更新
			// 获取角色信息
			Role role = getRoleInfo(params);
			if (null != role) { // 角色存在
				// 是否是系统角色
				Map<String, Object> requestParams = new HashMap<String, Object>();
				requestParams.put("companyId", 0);
				List<Long> sysRoleIdList = getRoleList(requestParams);
				// 此处默认生产环境的系统角色是有初始化数据的，所以没对list做非空判断
				if (sysRoleIdList.contains(Long.valueOf(params.get("id").toString()))) {
					throw new BizException(BizExceptionMessage.DB_ERROR, "系统角色不允许修改");
				} else {
					params.put("roleId", roleId);
					addOrUpdateRoleWithTransactional(params, "2");
				}
			} else {
				throw new BizException(BizExceptionMessage.DB_ERROR, "该角色不存在");
			}
		}
		return resultMap;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addOrUpdateRoleWithTransactional(Map<String, Object> params, String type) {
		if (StringUtils.equals("1", type)) { // 新增角色
			// 新增角色信息并返回角色ID
			addRole(params);
			// 批量新增角色权限关联关系
			addRolePermit(params);
		} else if (StringUtils.equals("2", type)) { // 更新角色
			// 修改角色信息 perm_role
			updateRoleInfo(params);
			// 删除该角色原有的权限（请求参数：companyId,roleId）
			delRolePermit(params);
			// 批量新增角色权限信息 perm_permit_role
			addRolePermit(params);
		}
	}

	@Override
	public Map<String, Object> delRoleById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取系统角色
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("companyId", 0);
		List<Long> sysRoleList = getRoleList(requestParams);
		if (null != sysRoleList && !sysRoleList.isEmpty()) {
			// 和roleId做对比
			if (sysRoleList.contains(Long.valueOf(params.get("id").toString()))) { // 系统角色，不允许删除
				throw new BizException(BizExceptionMessage.DB_ERROR, "系统角色不允许删除");
			} else { // 非系统角色，可以删除
				params.put("roleId", params.get("id"));
				delRoleWithTransactional(params);
			}
		} else { // 系统角色不存在的情况，直接删？
			throw new BizException(BizExceptionMessage.DB_ERROR, "判断系统角色不存在，角色直接删除吗？");
		}
		
		return resultMap;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delRoleWithTransactional(Map<String, Object> params) {
		// 根据companyId和roleId删除perm_permit_role中的数据
		delRolePermit(params);
		// 根据roleId删除perm_role_user中的数据
		delRoleUser(params);
		// 删除perm_role中的数据
		delRole(params);
	}


	@Override
	public Map<String, Object> getRoleInfoById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Role role = new Role();
		try {
			role = roleDao.getRoleInfoById(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取角色信息失败");
		}
		resultMap.put("result", role);
		return resultMap;
	}
	
	/**
	 * 1、companyId为0时，获取系统内置角色；2、查询数据库中符合条件的roleId,请求参数roleId
	 * @return
	 */
	private List<Long> getRoleList(Map<String, Object> params) {
		List<Role> roleList = null;
		try {
			roleList = roleDao.getRoleInfoListByParams(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "查询系统角色失败");
		}
		
		List<Long> roleIdList = new ArrayList<Long>();
		if (null != roleList && !roleList.isEmpty()) {
			roleList.forEach(role -> roleIdList.add(role.getId()));
		} 
		return roleIdList;
	}
	
	/**
	 * 新增角色信息
	 * @param params
	 * @return
	 */
	private Map<String, Object> addRole(Map<String, Object> params) {
		// 新增角色信息并返回角色ID
		try {
			Role role = new Role();
			role.setRoleName(params.get("roleName") == null ? null : params.get("roleName").toString());
			role.setRemark(params.get("remark") == null ? null : params.get("remark").toString());
			if (StringUtils.isNotEmpty((String) params.get("companyId"))) {
				role.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			}
			if (StringUtils.isNotEmpty((String) params.get("parentId"))) {
				role.setParentId(Long.valueOf(params.get("parentId").toString()));
			}
			if (StringUtils.isNotEmpty((String) params.get("roleOrder"))) {
				role.setRoleOrder(Integer.valueOf(params.get("roleOrder").toString()));
			}
						
			roleDao.addRole(role);
			params.put("roleId", role.getId());
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "新增角色失败");
		}
		return params;
	}
	
	/**
	 * 批量新增角色权限关联关系
	 * @param params
	 */
	private void addRolePermit(Map<String, Object> params) {
		// 获取权限ID
		if (StringUtils.isNotEmpty((String) params.get("permitId"))) {
			String permitId = (String) params.get("permitId");
			String[] permitIds = permitId.split(",");
			params.put("permitIds", permitIds);
			// 批量新增角色权限
			try {
				roleDao.batchAddRolePermit(params);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "批量新增角色权限失败");
			}
		}
	}
	
	/**
	 * 根据ID获取角色信息（单条）
	 * @param params
	 * @return
	 */
	private Role getRoleInfo(Map<String, Object> params) {
		Role role = new Role();
		try {
			role = roleDao.getRoleInfoById(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "查询角色是否存在失败");
		}
		return role;
	}
	
	/**
	 * 根据id更新角色信息
	 * @param params
	 */
	private void updateRoleInfo(Map<String, Object> params) {
		try {
			Role role = new Role();
			role.setId(Long.valueOf(params.get("id").toString()));
			role.setRoleName(params.get("roleName") == null ? null : params.get("roleName").toString());
			role.setRemark(params.get("remark") == null ? null : params.get("remark").toString());
			if (StringUtils.isNotEmpty((String) params.get("companyId"))) {
				role.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			}
			if (StringUtils.isNotEmpty((String) params.get("parentId"))) {
				role.setParentId(Long.valueOf(params.get("parentId").toString()));
			}
			if (StringUtils.isNotEmpty((String) params.get("roleOrder"))) {
				role.setRoleOrder(Integer.valueOf(params.get("roleOrder").toString()));
			}
			roleDao.updateRole(role);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "更新角色信息失败");
		}
	}
	
	/**
	 * 根据companyId和roleId删除角色权限关联
	 * @param params
	 */
	private void delRolePermit(Map<String, Object> params) {
		try {
			roleDao.delRolePermit(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "删除角色权限关联关系失败");
		}
	}
	
	/**
	 * 根据roleId删除用户角色关联关系
	 * @param params
	 */
	private void delRoleUser(Map<String, Object> params) {
		try {
			roleDao.delRoleUser(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "删除用户角色关联关系失败");
		}
	}
	
	/**
	 * 根据ID删除角色信息
	 * @param params
	 */
	private void delRole(Map<String, Object> params) {
		try {
			roleDao.delRole(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "删除角色失败");
		}
	}
	
	//------------------------------------------------------------NZ--END-----------------------------------------------------------------

	// ------------------------------------------------------------BZ--START-----------------------------------------------------------------
	/**
	 * 获取用户权限
	 */
	@Override
	public Map<String, Object> getUserPermission(Map<String, Object> params) {
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Permit> permitList = new ArrayList<Permit>();
		if (StringUtils.isEmpty(userId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "用户ID为空");
		} else {
			permitList = permitDao.getUserPermission(params);
		}
		resultMap.put("result", permitList);
		return resultMap;
	}

	/**
	 * 获取权限树
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPermissionTree(Map<String, Object> params) {
		String only = params.get("only") == null ? null : params.get("only").toString();
		String permExcept = params.get("permExcept") == null ? null : params.get("permExcept").toString();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<PermitTree> allPermitList = new ArrayList<PermitTree>();
		if (!StringUtils.isEmpty(only)) {
			only = "[" + only +"]";//数据库存储格式[]
			params.put("only", only);
		}
		if (!StringUtils.isEmpty(permExcept)) {
			permExcept = "[" + permExcept +"]";
			params.put("permExcept", permExcept);
		}
		allPermitList = permitDao.getAllPermission(params);
		List<Object> treeList = (List<Object>) TreeUtil.getTree(allPermitList);
		resultMap.put("result", treeList);
		return resultMap;
	}

	/**
	 * 获取用户菜单树
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserMenuTree(Map<String, Object> params) {
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MenuTree> allPermitList = new ArrayList<MenuTree>();
		if (StringUtils.isEmpty(userId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "用户ID为空");
		} else {
			allPermitList = menuDao.getAllMenu(params);
		}
		List<Object> treeList = (List<Object>) TreeUtil.getTree(allPermitList);
		resultMap.put("result", treeList);
		return resultMap;
	}

	/**
	 * 判断某操作是否有权限操作
	 */
	@Override
	public Map<String, Object> judgePermission(Map<String, Object> params) {
		String permitAction = params.get("permitAction") == null ? null : params.get("permitAction").toString();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int cnt = 0;
		if (StringUtils.isEmpty(permitAction)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR);
		} else {
			cnt = permitDao.judgePermission(params);
		}
		resultMap.put("result", cnt);
		return resultMap;
	}
	// ------------------------------------------------------------BZ--END-----------------------------------------------------------------
	
	/**
	 * 前端新版UI对应的接口
	 * @since 2017-06-08
	 */
	@Override
	public Map<String, Object> getUserMenusByAjuc(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			//调用ajuc新接口获取当前用户的菜单树
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);//公司id
			paramArray.add(loginUserId);//当前登录人id
			paramArray.add("hrm");//表示查询当前用户在hrm系统所能看到的菜单树
			try {
				String userMenus = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcMenu","getMenu",paramArray);
				JSONObject json = JSONObject.parseObject(userMenus);
				String code = json.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = json.get("data");
					resultMap.put("status", HttpStatus.SC_OK);//200
					resultMap.put("message", "菜单获取成功！");
					if(obj!=null){
						JSONObject jsonobj = JSONObject.parseObject(obj.toString());
						resultMap.put("userMenus", jsonobj.get("children"));
					}
				}else{
					throw new BizException(code,json.getString("msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("500","服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getUserPermissionByAjuc(	Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			//调用ajuc新接口获取当前用户的权限列表
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(loginUserId);
			try {
				String userPermits = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcPermission","getUserPermits",paramArray);
				JSONObject json = JSONObject.parseObject(userPermits);
				String code = json.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = json.get("data");
					resultMap.put("status", HttpStatus.SC_OK);//200
					resultMap.put("message", "用户权限获取成功！");
					resultMap.put("userPermits", obj);
				}else{
					throw new BizException(code,json.getString("msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("500","服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getUserRolesByAjuc(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(loginUserId);
			try {
				String userRoles = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcPermission","getUserRoles",paramArray);
				JSONObject json = JSONObject.parseObject(userRoles);
				String code = json.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = json.get("data");
					resultMap.put("status", HttpStatus.SC_OK);//200
					resultMap.put("message", "角色获取成功！");
					resultMap.put("userRoles", obj);
				}else{
					throw new BizException(code,json.getString("msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("500","服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getCompanyRolesList(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(loginUserId);
			try {
				//公司角色列表
				String companyRoles = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","getCompanyRolesWithPermits",paramArray);
				JSONObject jsonRoles = JSONObject.parseObject(companyRoles);
				String code = jsonRoles.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = jsonRoles.get("data");
					//角色列表数组
					JSONArray roleArray = JSONArray.parseArray(obj.toString());
					
					String filePath = PropertiesUtil.getPropertyByKey("fliePath1");
					File file = new File(filePath);
					if(!file.exists()){
						//将PHP控制的所有权限数据集下载到服务器指定位置
						HttpClientDownload.download(PropertiesUtil.getPropertyByKey("php.permitJson"), filePath);
					}
					//读取JSON文件
					String jsonStr = ReadJsonData.readFile(filePath);
					//得到所有权限集的数组
					JSONArray permitArray = JSONArray.parseArray(jsonStr);
					JSONObject allPermits = (JSONObject) permitArray.get(0);
					//得到最外层权限集
					JSONArray childAarray = (JSONArray) allPermits.get("children");
					
					String filePath2 = PropertiesUtil.getPropertyByKey("fliePath2");
					File file2 = new File(filePath2);
					if(!file2.exists()){
						//将PHP控制的所有权限name-id对应的数据下载到服务器指定位置
						HttpClientDownload.download(PropertiesUtil.getPropertyByKey("php.permission_dict"), filePath2);
					}
					//读取JSON文件
					String jsonStr2 = ReadJsonData.readFile(filePath2);
					
					JSONObject permitDic = JSONObject.parseObject(jsonStr2);
					Set<Entry<String, Object>> permission_dict = permitDic.entrySet();
					List<String> nameList = new ArrayList<String>();
					//角色管理列表
					List<RoleManagerBo> roleList = new ArrayList<RoleManagerBo>();
					for (Object object : roleArray) {
						
						JSONObject jsonObj = JSONObject.parseObject(object.toString());
						JSONObject role = (JSONObject) jsonObj.get("role");
						List<Integer> list = (List<Integer>) jsonObj.get("permits");
						//获取权限name-id的name属性
						for (Integer id : list) {
							for (Entry<String, Object> entry : permission_dict) {
								Object value = entry.getValue();
								if(value.toString().equals(id.toString())){
									nameList.add(entry.getKey());
								}
							}
						}
						
						RoleManagerBo bo = new RoleManagerBo();
						bo.setId(role.getString("id"));
						bo.setCompanyId(role.getString("visit_id"));
						bo.setRoleName(role.getString("role_name"));
						String permitName = "";
						//通过逻辑比对算法找到当前角色对应的权限名称(用于列表显示)
						for (String name : nameList) {
							//第1层循环
							if(childAarray!=null){
								for (Object obj1 : childAarray) {
									JSONObject jo1 = JSONObject.parseObject(obj1.toString());
									String name1 = jo1.getString("name");
									if(name.equals(name1)){
										permitName = jo1.getString("text");
										bo.setPermitName(permitName);
									}
									//进行第2层循环,继续寻找匹配的数据
									JSONArray childAarray2 = (JSONArray)jo1.get("children");
									if(childAarray2!=null){
										for (Object obj2 : childAarray2) {
											JSONObject jo2 = JSONObject.parseObject(obj2.toString());
											String name2 = jo2.getString("name");
											if(name.equals(name2)){
												permitName = jo1.getString("text");
												//permitList.add(jo1);
												if(!bo.getPermitName().contains(permitName)){
													bo.setPermitName(bo.getPermitName()+" "+permitName);
												}
											}
											//进行第3层循环,继续寻找匹配的数据
											JSONArray childAarray3 = (JSONArray)jo2.get("children");
											if(childAarray3!=null){
												for (Object obj3 : childAarray3) {
													JSONObject jo3 = JSONObject.parseObject(obj3.toString());
													String name3 = jo3.getString("name");
													if(name.equals(name3)){
														permitName = jo1.getString("text");
														if(!bo.getPermitName().contains(permitName)){
															bo.setPermitName(bo.getPermitName()+" "+permitName);
														}
													}
												}
											}
										}
									}
								}
							}
							if("管理员".equals(bo.getRoleName())){
								bo.setPermitName("拥有所有权限 ");
							}else if("普通用户".equals(bo.getRoleName())){
								bo.setPermitName("基础权限");
							}
						}
						roleList.add(bo);
					}
					resultMap.put("roleList", roleList);
				}else{
					throw new BizException(code,jsonRoles.getString("msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("500","服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> roleDetail(Map<String, Object> params)throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String roleId = params.get("roleId") == null ? null : params.get("roleId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isBlank(roleId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"角色id为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(roleId);
			//获取某个角色对应的权限集
			String rolePermitIds = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","getRolePermits",paramArray);
			//权限id数组
			JSONObject idJson = JSONObject.parseObject(rolePermitIds);
			JSONArray permitIds = (JSONArray) idJson.get("data");
			
			String filePath = PropertiesUtil.getPropertyByKey("fliePath1");
			File file = new File(filePath);
			if(!file.exists()){
				//将PHP控制的所有权限数据集下载到服务器指定位置
				HttpClientDownload.download(PropertiesUtil.getPropertyByKey("php.permitJson"), filePath);
			}
			//读取JSON文件
			String jsonStr = ReadJsonData.readFile(filePath);
			//得到所有权限集的数组
			JSONArray permitMenusArray = JSONArray.parseArray(jsonStr);
			
			String filePath2 = PropertiesUtil.getPropertyByKey("fliePath2");
			File file2 = new File(filePath2);
			if(!file2.exists()){
				//将PHP控制的所有权限name-id对应的数据下载到服务器指定位置
				HttpClientDownload.download(PropertiesUtil.getPropertyByKey("php.permission_dict"), filePath2);
			}
			//读取JSON文件
			String jsonStr2 = ReadJsonData.readFile(filePath2);
			JSONObject permitDic = JSONObject.parseObject(jsonStr2);
			Set<Entry<String, Object>> permission_dict = permitDic.entrySet();
			
			List<String> nameList = new ArrayList<String>();
			for (Object id : permitIds) {
				for (Entry<String, Object> entry : permission_dict) {
					Object value = entry.getValue();
					if(value.toString().equals(id.toString())){
						nameList.add(entry.getKey());
					}
				}
			}
			
			JSONObject allPermits = (JSONObject) permitMenusArray.get(0);
			//得到最外层权限集
			JSONArray childAarray = (JSONArray) allPermits.get("children");
			//第1层循环
			if(childAarray!=null){
				for (Object obj1 : childAarray) {
					JSONObject jo1 = (JSONObject) JSONObject.toJSON(obj1);
					String name1 = jo1.getString("name");
					jo1.put("checked", false);
					//给前端返回被选中的权限菜单
					for (String name : nameList) {
						if(name1.equals(name)){
							jo1.put("checked", true);//被选中
						}
					}
					//权限ID
					for (Entry<String, Object> entry : permission_dict) {
						Object key = entry.getKey();
						if(name1.equals(key)){
							jo1.put("id", entry.getValue());//赋值id
						}
					}
					
					//第2层循环
					JSONArray childAarray2 = (JSONArray)jo1.get("children");
					if(childAarray2!=null){
						for (Object obj2 : childAarray2) {
							JSONObject jo2 = (JSONObject) JSONObject.toJSON(obj2);
							String name2 = jo2.getString("name");
							jo2.put("checked", false);
							for (String name : nameList) {
								if(name2.equals(name)){
									jo2.put("checked", true);//被选中
								}
							}
							//权限ID
							for (Entry<String, Object> entry : permission_dict) {
								Object key = entry.getKey();
								if(name2.equals(key)){
									jo2.put("id", entry.getValue());//赋值id
								}
							}
							//第3层循环
							JSONArray childAarray3 = (JSONArray)jo2.get("children");
							if(childAarray3!=null){
								for (Object obj3 : childAarray3) {
									JSONObject jo3 = (JSONObject) JSONObject.toJSON(obj3);
									String name3 = jo3.getString("name");
									jo3.put("checked", false);
									for (String name : nameList) {
										if(name3.equals(name)){
											jo3.put("checked", true);//被选中
										}
									}
									//权限ID
									for (Entry<String, Object> entry : permission_dict) {
										Object key = entry.getKey();
										if(name3.equals(key)){
											jo3.put("id", entry.getValue());//赋值id
										}
									}
								}
							}
						}
					}
				}
			}
			
			resultMap.put("checkedName", nameList);//被选中的name属性(英文)
			resultMap.put("permitMenus", childAarray);//公司所有的权限菜单
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> addCommonRole(Map<String, Object> params)throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String roleName = params.get("roleName") == null ? null : params.get("roleName").toString();
		String permitIds = params.get("permitIds") == null ? null : params.get("permitIds").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isBlank(roleName)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"角色名称为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(roleName);
			//此接口仅能保存角色名称
			String result = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","addCommonRole",paramArray);
			JSONObject json = JSONObject.parseObject(result);
			String code = json.getString("code");
			if("0".equals(code)){
				
				JSONObject role = (JSONObject) json.get("data");
				String roleId = role.getString("id");
				paramArray.add(roleId);
				paramArray.add(permitIds);//权限id数组
				if(StringUtils.isNotBlank(roleId)&&StringUtils.isNotBlank(permitIds)){
					//此接口用于更新某个角色的权限集
					String result2 = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","updateRolePermits",paramArray);
				}
			}else{
				throw new BizException(code,json.getString("msg"));
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> updateRole(Map<String, Object> params)throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String roleId = params.get("roleId") == null ? null : params.get("roleId").toString();
		String roleName = params.get("roleName") == null ? null : params.get("roleName").toString();
		String permitIds = params.get("permitIds") == null ? null : params.get("permitIds").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isBlank(roleId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"角色id为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(roleId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("role_name", roleName);
			paramArray.add(map);
			//此接口仅能更新角色名称
			String result = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","updateRole",paramArray);
			JSONObject json = JSONObject.parseObject(result);
			String code = json.getString("code");
			if("0".equals(code)){
				paramArray.add(permitIds);//权限id数组
				if(StringUtils.isNotBlank(roleId)&&StringUtils.isNotBlank(permitIds)){
					//此接口用于更新某个角色的权限集
					String result2 = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","updateRolePermits",paramArray);
				}
			}else{
				throw new BizException(code,json.getString("msg"));
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> deleteRole(Map<String, Object> params) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String roleId = params.get("roleId") == null ? null : params.get("roleId").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isBlank(roleId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"角色id为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			paramArray.add(roleId);
			String result = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","deleteRole",paramArray);
			JSONObject json = JSONObject.parseObject(result);
			String code = json.getString("code");
			if("0".equals(code)){
				resultMap.put("status", 200);
				resultMap.put("message", "删除成功");
			}else{
				throw new BizException(code,json.getString("msg"));
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getEmpSetPermitList(Map<String,Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String pageNum = params.get("pageNo") == null ? null : params.get("pageNo").toString();
		String pageSize = params.get("pageSize") == null ? null : params.get("pageSize").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyId", companyId);
			int count = userDao.countUser(map);
			Pagination pt = new Pagination(count, pageNum, pageSize);
			map.put("pageSize", pageSize);
			map.put("startRow", pt.getStartPos());
			
			//先查询员工列表
			List<User> empList = userDao.selectByExample(map);
			List<UserRoleBo> boList = new ArrayList<UserRoleBo>();
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);
			try {
				String roles = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcRole","getCompanyRoles",paramArray);
				JSONObject json = JSONObject.parseObject(roles);
				String code = json.getString("code");
				if("0".equals(code)){
					//单调AJUC的接口获取公司的管理员
					String companyAdmin = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","getAdminUser",paramArray);
					JSONObject adminJson = JSONObject.parseObject(companyAdmin);
					String code2 = adminJson.getString("code");
					String adminId = "";
					if("0".equals(code2)){
						JSONObject jsonData = (JSONObject) adminJson.get("data");
						//管理员id
						adminId = jsonData.getString("id");
					}
					
					JSONArray array = (JSONArray) json.get("data");
					for (User user : empList) {
						String roleIds = user.getRoleIds();
						String roleNames = "";
						if(StringUtils.isNotBlank(roleIds)){
							String[] split = roleIds.split(",");
							//根据角色id组装每个员工已分配的角色名称
							for (String roleId : split) {
								for (Object role : array) {
									JSONObject jsonRole = JSONObject.parseObject(role.toString());
									String id = jsonRole.getString("id");
									if(roleId.equals(id)){
										roleNames = roleNames + jsonRole.getString("role_name")+",";
									}
								}
							}
							if(roleNames.endsWith(",")){
								roleNames = roleNames.substring(0, roleNames.length()-1);
							}
							user.setUserRoles(roleNames);
						}
						if(user.getLoginUserId().toString().equals(adminId)){
							user.setUserRoles("管理员");
						}
						UserRoleBo bo = new UserRoleBo();
						BeanUtils.copyProperties(bo, user);
						boList.add(bo);
					}
					resultMap.put("status", HttpStatus.SC_OK);
					resultMap.put("message", "列表获取成功！");
					resultMap.put("empSetPermitList", boList);
				}else{
					throw new BizException(code,json.getString("msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("500","服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> setEmpPermit(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String empId = params.get("empId") == null ? null : params.get("empId").toString();
		String roleIds = params.get("roleIds") == null ? null : params.get("roleIds").toString();
		if(StringUtils.isBlank(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isBlank(loginUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isBlank(empId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"id为空");
		}else{
			User record = new User();
			record.setId(Long.parseLong(empId));
			record.setRoleIds(roleIds);
			userDao.updateByExample(record);
			resultMap.put("status",200);
			resultMap.put("message", "设置成功");
		}
		return resultMap;
	}
	
}