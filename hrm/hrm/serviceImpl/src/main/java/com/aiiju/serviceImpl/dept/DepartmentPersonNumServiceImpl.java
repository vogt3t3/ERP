package com.aiiju.serviceImpl.dept;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.dept.PersonnelForce;
import com.aiiju.dao.oa.dept.IDepartmentPersonNumDao;
import com.aiiju.service.dept.IDepartmentPersonNumService;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
/** 
@Author  太一 
@Date 创建时间：2016年11月2日 下午1:43:01 
*/
@Service("departmentPersonNumService")
public class DepartmentPersonNumServiceImpl implements IDepartmentPersonNumService{
	@Autowired
	private IDepartmentPersonNumDao dao;
	
	@Override
	public List<PersonnelForce> getDepartmentPersonNum(Map<String, Object> params) {
		return dao.getDepartmentPersonNum(params);
	}

	@Override
	public int updateDepartmentPersonNum(Map<String, Object> params) {
		// 判断插入的时间段，是否已存在
		int exists = -1;
		exists = dao.existsDepartmentPersonNum(params);
		if (exists == 0) {
			exists = dao.updateDepartmentPersonNum(params);
		} else if (exists > 0) {// 告知用户，插入时间段已存在
			throw new BizException(BizExceptionMessage.DATE_IS_EXIST);
		}
		return exists;
	}

	@Override
	public int addDepartmentPersonNum(Map<String, Object> params) {
		// 判断插入的时间段，是否已存在
		int exists = -1;
		exists = dao.existsDepartmentPersonNum(params);
		List<PersonnelForce> departmentPersonNum = dao.getDepartmentPersonNum(params);
		if (exists == 0) {
			exists = dao.addDepartmentPersonNum(params);
		} else if (exists > 0) {// 告知用户，插入时间段已存在
			throw new BizException(BizExceptionMessage.DATE_IS_EXIST);
		}
		return exists;
	}

	@Override
	public int delDepartmentPersonNum(Map<String, Object> params) {
		return dao.delDepartmentPersonNum(params);
	}

}
