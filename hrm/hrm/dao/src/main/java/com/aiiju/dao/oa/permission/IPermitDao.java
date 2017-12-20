package com.aiiju.dao.oa.permission;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.permission.Permit;
import com.aiiju.bean.oa.permission.PermitTree;

/**
 * 
 * @ClassName: IPermitDao 
 * @Description: 权限接口访问层
 * @author 哪吒 
 * @date 2016年12月8日 下午2:56:54 
 *
 */

public interface IPermitDao {

	//获取用户权限
	public List<Permit> getUserPermission(Map<String,Object> params);
	
	//获取所有权限
	public List<PermitTree> getAllPermission(Map<String,Object> params);
	
	//获取权限匹配
	public int judgePermission(Map<String,Object> params);
}
