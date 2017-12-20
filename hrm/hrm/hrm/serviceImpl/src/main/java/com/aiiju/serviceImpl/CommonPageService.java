package com.aiiju.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;

public abstract class CommonPageService{

	@Autowired
	private ICommonPageDao commonPageDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	/**
	 * 文件删除
	 * @param params
	 * @return
	 */
	public String delFileRecord(Map<String, Object> params) {
		String returnMsg = "删除成功";
		try {
			List<FileInfo> fiList = commonPageDao.getFileInfoList(params);
			if(fiList.size() > 0 && fiList != null){
				boolean isDel = ExcelUtil.delFile(fiList.get(0).getUrl());
				if(isDel){
					commonPageDao.delFileRecord(params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = "删除失败";
			throw new BizException(BizExceptionMessage.DB_ERROR,"删除失败");
		}
		return returnMsg;
	}
	
	/**
	 * 获取该组织的所有下级部门ID,包含自己
	 * @param params
	 * @param deptId
	 * @return
	 */
	public List<String> getSubDeptIdList(Map<String,Object> params,String deptId){
		List<String> subDeptIdList = new ArrayList<String>();
		//查询的部门
		List<Department> queryDepts = new ArrayList<Department>();
		Department queryDept = new Department();
		queryDept.setId(Integer.valueOf(params.get("deptId").toString()));
		queryDepts.add(queryDept);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("companyId", params.get("companyId").toString());
		//所有部门
		List<Department> allDepts = departmentDao.getDepartmentList(m);
		List<Department> showDepts = new ArrayList<Department>();
		DeptUtil.getAllDepts(queryDepts, allDepts, showDepts);
		for(Department dept : showDepts){
			subDeptIdList.add(dept.getId().toString());
		}
		return subDeptIdList;
	}
	
}
