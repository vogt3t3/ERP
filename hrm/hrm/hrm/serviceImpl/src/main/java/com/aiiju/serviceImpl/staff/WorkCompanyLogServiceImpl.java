package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.WorkCompanyLog;
import com.aiiju.dao.oa.staff.IWorkCompanyLogDao;
import com.aiiju.service.staff.IWorkCompanyLogService;
import com.alibaba.fastjson.JSONObject;
@Service("workCompanyLogService")
@Transactional
public class WorkCompanyLogServiceImpl implements IWorkCompanyLogService {

	@Autowired
	private IWorkCompanyLogDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addWorkCompanyLog(Map<String, Object> map) {
		String obj=map.get("workCompanyLog").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		WorkCompanyLog log = JSONObject.parseObject(obj, WorkCompanyLog.class);
		log.setUserId(Long.parseLong(userId));
		log.setCompanyId(Long.parseLong(companyId));
		return dao.insert(log);
	}

	@Override
	public Map<String, Object> getWorkCompanyLogs(Map<String, Object> map) {
		Map<String, Object> workCompanyLogs = new HashMap<String, Object>();
		workCompanyLogs.put("workCompanyLogs", dao.selectByExample(map));
		return workCompanyLogs;
	}

	@Override
	public WorkCompanyLog getWorkCompanyLogById(Map<String, Object> map) {
		return dao.selectByUsrId(map);
	}

	@Override
	public int updateWorkCompanyLog(Map<String, Object> map) {
		String obj=map.get("workCompanyLog").toString();
		WorkCompanyLog log = JSONObject.parseObject(obj, WorkCompanyLog.class);
		return dao.updateByExample(log);
	}

}
