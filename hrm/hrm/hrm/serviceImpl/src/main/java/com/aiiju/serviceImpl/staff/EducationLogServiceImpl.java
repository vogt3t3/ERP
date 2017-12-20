package com.aiiju.serviceImpl.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.EducationLog;
import com.aiiju.dao.oa.staff.IEducationLogDao;
import com.aiiju.service.staff.IEducationLogService;
import com.aiiju.util.enums.Eduational;
import com.alibaba.fastjson.JSONObject;
@Service("educationLogService")
@Transactional
public class EducationLogServiceImpl implements IEducationLogService {

	@Autowired
	private IEducationLogDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addEducationLog(Map<String, Object> map) {
		String obj=map.get("educationLog").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		EducationLog educationLog = JSONObject.parseObject(obj, EducationLog.class);
		educationLog.setUserId(Long.parseLong(userId));
		educationLog.setCompanyId(Long.parseLong(companyId));
		return dao.insert(educationLog);
	}

	@Override
	public List<EducationLog> getEducationLogs(Map<String, Object> map) {
		List<EducationLog> list= dao.selectByExample(map);
		for (EducationLog educationLog : list) {
			if(educationLog.getEducationType()!=null){
				educationLog.setEducationTypeName(educationLog.getEducationType().toString());
			}
			if(educationLog.getEducationLevel()!=null){
				educationLog.setEducation(Eduational.getName(educationLog.getEducationLevel().toString()));
			}
		}
		return list;
	}

	@Override
	public EducationLog getEducationLogById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateEducationLog(Map<String, Object> map) {
		String obj=map.get("educationLog").toString();
		EducationLog educationLog = JSONObject.parseObject(obj, EducationLog.class);
		return dao.updateByExample(educationLog);
	}

}
