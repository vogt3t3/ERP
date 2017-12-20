package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.JobStatusLog;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.staff.IJobStatusLogDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.staff.IJobStatusLogService;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.alibaba.fastjson.JSONObject;
@Service("jobStatusLogService")
@Transactional
public class JobStatusLogServiceImpl implements IJobStatusLogService {

	@Autowired
	private IJobStatusLogDao dao;
	@Autowired
	private IUserDao userDao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addJobStatusLog(Map<String, Object> map) {
		String obj=map.get("jobStatusLog").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		JobStatusLog jobStatusLog = JSONObject.parseObject(obj, JobStatusLog.class);
		jobStatusLog.setUserId(Long.parseLong(userId));
		jobStatusLog.setCompanyId(Long.parseLong(companyId));
		if(jobStatusLog.getEndDate()!=null&&jobStatusLog.getStartDate()!=null){
			if(jobStatusLog.getEndDate().getTime()<jobStatusLog.getStartDate().getTime()){
				throw new BizException(BizExceptionMessage.PROP_ERROR,"结束时间不能小于开始时间");
			}
		}
		String end=null;
		String start=null;
		List<JobStatusLog> jobs=dao.selectByExample(map);
		//判断之前的任职是否结束
		for (JobStatusLog jobStatusLog2 : jobs) {
			if(jobStatusLog2.getEndDate()==null){
				throw new BizException(BizExceptionMessage.PROP_ERROR,"上一份任职未结束");
			}
			end=DateUtil.transFormDate(jobStatusLog2.getEndDate(),"yyyy-MM-dd").toString();
			start=DateUtil.transFormDate(jobStatusLog.getStartDate(),"yyyy-MM-dd").toString();
			if(DateUtil.compareDate(end, start)){
				throw new BizException(BizExceptionMessage.PROP_ERROR,"上一份任职未结束");
			}
		}
		dao.insert(jobStatusLog);
		User user = new User();
		user.setId(Long.parseLong(userId));
		user.setPositionId(jobStatusLog.getPositionId());
		user.setDutyId(jobStatusLog.getDutyId());
		return userDao.updateByExample(user);	
	}

	@Override
	public Map<String, Object> getJobStatusLogs(Map<String, Object> map) {
		Map<String, Object> jobStatusLogs = new HashMap<String, Object>();
		jobStatusLogs.put("jobStatusLogs", dao.selectByExample(map));
		return jobStatusLogs;
	}

	@Override
	public JobStatusLog getJobStatusLogById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateJobStatusLog(Map<String, Object> map) {
		String obj=map.get("jobStatusLog").toString();
		JobStatusLog jobStatusLog = JSONObject.parseObject(obj, JobStatusLog.class);
		return dao.updateByExample(jobStatusLog);
	}

}
