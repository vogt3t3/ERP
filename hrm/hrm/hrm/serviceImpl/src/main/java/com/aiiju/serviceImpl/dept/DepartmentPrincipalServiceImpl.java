package com.aiiju.serviceImpl.dept;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.dept.DepartmentPrincipal;
import com.aiiju.dao.oa.dept.IDepartmentPrincipalDao;
import com.aiiju.service.dept.IDepartmentPrincipalService;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;

/** 
@Author  太一 
@Date 创建时间：2016年11月2日 下午2:49:20 
*/
@Service("departmentPrincipalService")
public class DepartmentPrincipalServiceImpl implements IDepartmentPrincipalService{
	@Autowired
	private IDepartmentPrincipalDao dao;

	@Override
	public List<DepartmentPrincipal> getDepartmentPrincipal(Map<String, Object> params) {
		return dao.getDepartmentPrincipal(params);
	}

	@Override
	public int addDepartmentPrincipal(Map<String, Object> params) {
		// 判断插入的时间段，是否已存在
		int exists = -1;
		exists = dao.existsDepartmentPrincipal(params);
		if (exists == 0) {
			exists = dao.addDepartmentPrincipal(params);
		} else if (exists > 0) {// 告知用户，插入时间段已存在
			throw new BizException(BizExceptionMessage.DATE_IS_EXIST);
		}
		return exists;
	}

	@Override
	public int updateDepartmentPrincipal(Map<String, Object> params) {
		// 判断插入的时间段，是否已存在
		int exists = -1;
		exists = dao.existsDepartmentPrincipal(params);
		if (exists == 0) {
			exists = dao.updateDepartmentPrincipal(params);
		} else if (exists > 0) {// 告知用户，插入时间段已存在
			throw new BizException(BizExceptionMessage.DATE_IS_EXIST);
		}
		return exists;
	}

	@Override
	public int delDepartmentPrincipal(Map<String, Object> params) {
		return dao.delDepartmentPrincipal(params);
	}
	
}
