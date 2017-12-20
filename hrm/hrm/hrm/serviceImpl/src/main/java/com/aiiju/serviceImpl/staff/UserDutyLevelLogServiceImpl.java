package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.staff.UserDutyLevelLog;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.staff.IUserDutyLevelLogDao;
import com.aiiju.service.staff.IUserDutyLevelLogService;
import com.alibaba.fastjson.JSONObject;
@Service("userDutyLevelLogService")
@Transactional
public class UserDutyLevelLogServiceImpl implements IUserDutyLevelLogService {

	@Autowired
	private IUserDutyLevelLogDao dao;
	@Autowired
	private IUserDao userDao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addUserDutyLevelLog(Map<String, Object> map) {
		String obj=map.get("userDutyLevelLog").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		UserDutyLevelLog log = JSONObject.parseObject(obj, UserDutyLevelLog.class);
		log.setUserId(Long.parseLong(userId));
		log.setCompanyId(Long.parseLong(companyId));
		dao.insert(log);
		int i=0;
		if(log.getStartDate().getTime()<=System.currentTimeMillis()){
			User user = new User();
			user.setId(Long.parseLong(userId));
			user.setDutyTypeId(log.getDutyTypeId());
			i=userDao.updateByExample(user);
		}
		return i;
	}

	@Override
	public Map<String, Object> getUserDutyLevelLogs(Map<String, Object> map) {
		Map<String, Object> userDutyLevelLogs = new HashMap<String, Object>();
		userDutyLevelLogs.put("userDutyLevelLogs", dao.selectByExample(map));
		return userDutyLevelLogs;
	}

	@Override
	public UserDutyLevelLog getUserDutyLevelLogById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateUserDutyLevelLog(Map<String, Object> map) {
		String obj=map.get("userDutyLevelLog").toString();
		UserDutyLevelLog log = JSONObject.parseObject(obj, UserDutyLevelLog.class);
		return dao.updateByExample(log);
	}

}
