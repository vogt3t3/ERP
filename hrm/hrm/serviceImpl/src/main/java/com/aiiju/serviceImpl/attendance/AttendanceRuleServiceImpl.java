package com.aiiju.serviceImpl.attendance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aiiju.bean.oa.attendance.AttendanceRule;
import com.aiiju.bean.oa.attendance.SignRecord;
import com.aiiju.bean.oa.attendance.SignRuleDept;
import com.aiiju.bean.oa.attendance.SignRuleHistory;
import com.aiiju.bean.oa.attendance.SignRuleTime;
import com.aiiju.bean.oa.attendance.SignRuleTimeHistory;
import com.aiiju.bean.oa.attendance.SignRuleUser;
import com.aiiju.bean.oa.attendance.bo.AttendanceRuleBo;
import com.aiiju.bean.oa.attendance.bo.SignRecordBo;
import com.aiiju.bean.oa.attendance.bo.SignRuleForAppBo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.attendance.IAttendanceRuleDao;
import com.aiiju.dao.oa.attendance.ISignRecordDao;
import com.aiiju.dao.oa.attendance.ISignRuleDeptDao;
import com.aiiju.dao.oa.attendance.ISignRuleHistoryDao;
import com.aiiju.dao.oa.attendance.ISignRuleTimeDao;
import com.aiiju.dao.oa.attendance.ISignRuleTimeHistoryDao;
import com.aiiju.dao.oa.attendance.ISignRuleUserDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.attendance.IAttendanceRuleService;
import com.aiiju.serviceImpl.attendance.vo.AttendanceRuleVo;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.Distance;
import com.aiiju.util.enums.SignResult;
import com.aiiju.util.enums.SignTypeMark;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.http.StringUtils;
import com.alibaba.fastjson.JSONObject;

@Service("attendanceRuleService")
public class AttendanceRuleServiceImpl implements IAttendanceRuleService{
	public static final Logger logger = LoggerFactory.getLogger("attendanceRuleService");
	@Autowired
	private IAttendanceRuleDao attendanceRuleDao;
	@Autowired
	private ISignRuleDeptDao signRuleDeptDao;
	@Autowired
	private ISignRuleUserDao signRuleUserDao;
	@Autowired
	private IDepartmentDao departmentDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ISignRecordDao signRecordDao;
	@Autowired
	private ISignRuleTimeDao signRuleTimeDao;
	@Autowired
	private ISignRuleTimeHistoryDao signRuleTimeHistoryDao;
	@Autowired
	private ISignRuleHistoryDao signRuleHistoryDao;
	
	@Override
	@Transactional 
	public Map<String, Object> addAttendanceRule(Map<String, Object> params) throws Exception{
		
		if(MapUtils.isEmpty(params)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请求参数为空");
		}
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司Id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户Id为空");
		}else{
			try {
				String attendanceRuleStr = params.get("attendanceRule").toString();
				//1.将前端传来的JSON对象串转换成相应的VO对象
				AttendanceRuleVo ruleVo = JSONObject.parseObject(attendanceRuleStr, AttendanceRuleVo.class);
				List<Integer> ruleForDepIds = ruleVo.getRuleForDepId();
				List<Long> ruleForEmpIds = ruleVo.getRuleForEmpId();
				
				String[] splitDepts = null;
				List<Integer> checkDetpts = new ArrayList<Integer>();
				if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
					params.clear();
					params.put("companyId", companyId);
					StringBuilder currDepts = new StringBuilder();
					int i = 0;
					for (Integer detid : ruleForDepIds) {
						if(i==0||i==ruleForDepIds.size()){
							currDepts.append(detid.toString());
						}else{
							currDepts.append(",").append(detid.toString());
						}
						i++;
					}
					params.put("deptIds", currDepts.toString());
					//调用数据库函数 查询当前部门的所有子部门id集
					String deptChildIds = departmentDao.getDeptChildIds(params);
					splitDepts = deptChildIds.split(",");
					for (String detpid : splitDepts) {
						checkDetpts.add(new Integer(detpid));
					}
				}
				ruleVo.setRuleForDepId(checkDetpts);
				
				//2.对数据进行非空校验
				this.checkVoDataIsGood(ruleVo,companyId,userId,"add",null);
				
				//将VO对象中的属性复制到考勤规则表对应的实体中
				AttendanceRule attendanceRule = new AttendanceRule();
				BeanUtils.copyProperties(attendanceRule, ruleVo);
				attendanceRule.setCompanyId(Integer.parseInt(companyId));
				attendanceRule.setCreatorId(Long.parseLong(userId));
				attendanceRule.setIsUsed((byte)1);//新增默认为生效
				Date currDate = new Date();
				attendanceRule.setForceTime(currDate);//生效日期为当天
				attendanceRule.setCreateDate(currDate);
				attendanceRule.setUpdateDate(currDate);
				//保存考勤规则
				attendanceRuleDao.insertSelective(attendanceRule);
				
				//3.单独保存考勤规则时间
				SignRuleTime ruleTime = new SignRuleTime();
				//将VO中的考勤规则时间复制到ruleTime对象中
				BeanUtils.copyProperties(ruleTime, ruleVo);
				ruleTime.setCompanyId(attendanceRule.getCompanyId());
				ruleTime.setCreatorId(attendanceRule.getCreatorId());
				ruleTime.setCreateDate(currDate);
				ruleTime.setUpdateDate(currDate);
				ruleTime.setRuleId(attendanceRule.getId());
				signRuleTimeDao.insertSelective(ruleTime);
				
				//4.循环将考勤规则部门关系表生成
				if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
					params.clear();
					params.put("companyId", companyId);
					for (Integer pdetpId : ruleForDepIds) {
						params.put("deptIds", pdetpId);
						//调用数据库函数 查询当前部门的所有子部门id集
						String deptChildIds = departmentDao.getDeptChildIds(params);
						splitDepts = deptChildIds.split(",");
						for (String detpid : splitDepts) {
							SignRuleDept ruleDept = new SignRuleDept();
							ruleDept.setCompanyId(Integer.parseInt(companyId));
							ruleDept.setDeptId(Integer.parseInt(detpid));
							ruleDept.setPdetpId(pdetpId);//虚拟的父级部门id
							ruleDept.setRuleId(attendanceRule.getId());
							ruleDept.setCreateDate(currDate);
							signRuleDeptDao.insertSelective(ruleDept);
						}
					}
				}
						
				if(ruleForEmpIds!=null&&ruleForEmpIds.size()>0){
					//5.保存考勤规则用户表
					for (Long empid : ruleForEmpIds) {
						SignRuleUser ruleUser = new SignRuleUser();
						ruleUser.setCompanyId(Integer.parseInt(companyId));
						ruleUser.setUserId(empid);
						ruleUser.setRuleId(attendanceRule.getId());
						ruleUser.setCreateDate(currDate);
						signRuleUserDao.insertSelective(ruleUser);
					}
				}
				resultMap.put("status", 200);
				resultMap.put("msg", "保存成功");
			}catch (Exception e) {
				resultMap.put("status", 500);
				resultMap.put("msg", "系统发生异常请稍候再试");
				throw e;
			}
		}
		return resultMap;
	}
	
	/**
	 * 校验前端传递的表单数据是否合法
	 * @param ruleVo
	 * @param companyId
	 * @param userId
	 */
	private void checkVoDataIsGood(AttendanceRuleVo ruleVo,String companyId,String userId,String type,String ruleId){
		if(StringUtils.isBlank(ruleVo.getName())){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"规则名称不能为空");
		}
		List<Integer> ruleForDepIds = ruleVo.getRuleForDepId();
		List<Long> ruleForEmpIds = ruleVo.getRuleForEmpId();
		if("add".equals(type)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("companyId", companyId);
			if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
				for (Integer deptId : ruleForDepIds) {
					params.put("deptId", deptId);
					List<SignRuleDept> ruleDepts = signRuleDeptDao.selectListByParams(params);
					if(ruleDepts!=null&&ruleDepts.size()>0){
						params.clear();
						params.put("id", deptId);
						Department dep = departmentDao.getDepartmentById(params);
						if(dep!=null){
							throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+dep.getName()+"】"+"已经有考勤规则");
						}
					}else{//此时查询部门里的员工是否有已经设置过的考勤规则
						//查询某个部门里的所有员工
						params.put("deptIdSignRule", deptId);
						List<User> userList = userDao.selectByExample(params);
						if(userList!=null&&userList.size()>0){
							for (User user : userList) {
								params.put("companyId", companyId);
								params.put("userId", user.getId());
								List<SignRuleUser> ruleUsers = signRuleUserDao.selectListByParams(params);
								if(ruleUsers!=null&&ruleUsers.size()>0){
									if(user!=null){
										throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
									}
								}
							}
						}
					}
				}
			}else{
				if(ruleForEmpIds!=null&&ruleForEmpIds.size()>0){
					for (Long empId : ruleForEmpIds) {
						params.put("companyId", companyId);
						params.put("userId", empId);
						List<SignRuleUser> ruleUsers = signRuleUserDao.selectListByParams(params);
						User user = null;
						if(ruleUsers!=null&&ruleUsers.size()>0){
							params.put("id", empId);
							user = userDao.seleteById(params);
							if(user!=null){
								throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
							}
						}else{//此时再次通过规则部门表看能否查到该员工的考勤
							params.put("id", empId);
							user = userDao.seleteById(params);
							params.remove("id");
							params.put("deptId", user.getDeptId());
							List<SignRuleDept> list = signRuleDeptDao.selectListByParams(params);
							if(list!=null&&list.size()>0){
								if(user!=null){
									throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
								}
							}
						}
					}
				}else{
					throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择考勤人员");
				}
			}
		}else if("edit".equals(type)){
			if((ruleForDepIds==null||ruleForDepIds.size()==0) && (ruleForEmpIds==null||ruleForEmpIds.size()==0)){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择考勤人员");
			}
			//修改时也要校验是否当前部门或用户已经有考勤规则(修改的校验逻辑与新增的不一样)
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("companyId", companyId);
			if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
				for (Integer deptId : ruleForDepIds) {
					params.put("deptId", deptId);
					List<SignRuleDept> ruleDepts = signRuleDeptDao.selectListByParams(params);
					if(ruleDepts!=null&&ruleDepts.size()>0){
						SignRuleDept signRuleDept = ruleDepts.get(0);
						if(!ruleId.equals(signRuleDept.getRuleId().toString())){
							params.clear();
							params.put("id", deptId);
							Department dep = departmentDao.getDepartmentById(params);
							if(dep!=null){
								throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+dep.getName()+"】"+"已经有考勤规则");
							}
						}
					}else{//此时查询部门里的员工是否有已经设置过的考勤规则
						//查询某个部门里的所有员工
						params.put("deptIdSignRule", deptId);
						List<User> userList = userDao.selectByExample(params);
						if(userList!=null&&userList.size()>0){
							for (User user : userList) {
								params.put("companyId", companyId);
								params.put("userId", user.getId());
								List<SignRuleUser> ruleUsers = signRuleUserDao.selectListByParams(params);
								if(ruleUsers!=null&&ruleUsers.size()>0){
									if(user!=null){
										throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
									}
								}
							}
						}
					}
				}
			}else{
				if(ruleForEmpIds!=null&&ruleForEmpIds.size()>0){
					for (Long empId : ruleForEmpIds) {
						params.put("companyId", companyId);
						params.put("userId", empId);
						List<SignRuleUser> ruleUsers = signRuleUserDao.selectListByParams(params);
						User user = null;
						if(ruleUsers!=null&&ruleUsers.size()>0){
							SignRuleUser signRuleUser = ruleUsers.get(0);
							if(!ruleId.equals(signRuleUser.getRuleId().toString())){
								params.put("id", empId);
								user = userDao.seleteById(params);
								if(user!=null){
									throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
								}
							}
						}else{//此时再次通过规则部门表看能否查到该员工的考勤
							params.put("id", empId);
							user = userDao.seleteById(params);
							params.remove("id");
							params.put("deptId", user.getDeptId());
							List<SignRuleDept> list = signRuleDeptDao.selectListByParams(params);
							if(list!=null&&list.size()>0){
								SignRuleDept signRuleDept = list.get(0);
								if(user!=null&&!ruleId.equals(signRuleDept.getRuleId().toString())){
									throw new BizException(BizExceptionMessage.EMPTY_ERROR,"【"+user.getName()+"】"+"已经有考勤规则");
								}
							}
						}
					}
				}else{
					throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择考勤人员");
				}
			}
		}
		if(StringUtils.isBlank(ruleVo.getWorkDay())){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择考勤工作日");
		}
		if(StringUtils.isBlank(ruleVo.getSignInTime())||StringUtils.isBlank(ruleVo.getSignOutTime())){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请填写完整的考勤时间段");
		}
		//校验考勤时段后者应当大于前者
		if(StringUtils.isNotBlank(ruleVo.getSignInTime())&&StringUtils.isNotBlank(ruleVo.getSignOutTime())){
			compareVoRuleTime(ruleVo.getSignInTime(),ruleVo.getSignOutTime());
		}
		if(StringUtils.isNotBlank(ruleVo.getSignInTime2())&&StringUtils.isNotBlank(ruleVo.getSignOutTime2())){
			compareVoRuleTime(ruleVo.getSignInTime(),ruleVo.getSignOutTime());
			compareVoRuleTime(ruleVo.getSignInTime2(),ruleVo.getSignOutTime2());
			checkTheSecond(ruleVo.getSignInTime2(),ruleVo.getSignOutTime());
		}
		if(StringUtils.isNotBlank(ruleVo.getSignInTime3())&&StringUtils.isNotBlank(ruleVo.getSignOutTime3())){
			compareVoRuleTime(ruleVo.getSignInTime(),ruleVo.getSignOutTime());
			compareVoRuleTime(ruleVo.getSignInTime2(),ruleVo.getSignOutTime2());
			compareVoRuleTime(ruleVo.getSignInTime3(),ruleVo.getSignOutTime3());
			checkTheSecond(ruleVo.getSignInTime2(),ruleVo.getSignOutTime());
			checkTheSecond(ruleVo.getSignInTime3(),ruleVo.getSignOutTime2());
		}
		if(StringUtils.isBlank(ruleVo.getPositionLon(),ruleVo.getPositionLat())){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择考勤地点");
		}
		if(StringUtils.isBlank(ruleVo.getPositionOffset())){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请选择允许偏差范围");
		}
	}
	
	/**
	 * 自定义时间比较规则：校验选择考勤时段后者要大于前者
	 * @param ruleTime
	 * @return
	 */
	private void compareVoRuleTime(String inTime,String outTime){
		//签到规则时间
		String[] splitIn = inTime.split(":");
		int inHour = Integer.parseInt(splitIn[0]);
		int inMin = Integer.parseInt(splitIn[1]);
		//签退规则时间
		String[] splitOut = outTime.split(":");
		int outHour = Integer.parseInt(splitOut[0]);
		int outMin = Integer.parseInt(splitOut[1]);
		if(outHour<inHour){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签退时间必须大于签到时间");
		}else if(outHour==inHour){
			if(outMin<inMin){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签退时间必须大于签到时间");
			}
		}
	}
	/**
	 * 校验第二组或第三组考勤时段要大于第一组或第二组
	 * @param inTime
	 * @param outTime
	 */
	private void checkTheSecond(String in2,String out1){
		String[] splitIn = in2.split(":");
		int inHour = Integer.parseInt(splitIn[0]);
		int inMin = Integer.parseInt(splitIn[1]);
		
		String[] splitOut = out1.split(":");
		int outHour = Integer.parseInt(splitOut[0]);
		int outMin = Integer.parseInt(splitOut[1]);
		if(inHour<outHour){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签到时间必须大于上一个时段的签退时间");
		}else if(outHour==inHour){
			if(inMin<outMin){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签到时间必须大于上一个时段的签退时间");
			}
		}
	}

	@Override
	public Map<String, Object> getAttendanceRuleList(Map<String, Object> params) throws Exception{
		if(MapUtils.isEmpty(params)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请求参数为空");
		}
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			try {
				//1.查询考勤规则列表
				params.put("isDel", 0);
				List<AttendanceRule> ruleList = attendanceRuleDao.selectAttendanceRuleList(params);
				List<AttendanceRuleBo> ruleBoList = new ArrayList<AttendanceRuleBo>();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("companyId", companyId);
				for (AttendanceRule attendanceRule : ruleList) {
					AttendanceRuleBo bo = new AttendanceRuleBo();
					List<SignRuleTime> ruleTimes = new ArrayList<SignRuleTime>();
					param.put("ruleId", attendanceRule.getId());
					param.put("isDel", 0);
					ruleTimes = signRuleTimeDao.selectListByParams(param);
					for (SignRuleTime signRuleTime : ruleTimes) {
						//将数字格式转换为周几显示
						String weekToString = getWeekToString(signRuleTime.getWorkDay());
						if(weekToString.startsWith(",")){
							weekToString = weekToString.substring(1,weekToString.length());
						}
						signRuleTime.setWorkDay(weekToString);
					}
					bo.setSignRuleTimeList(ruleTimes);
					//2.1查询考勤规则对应的部门名称
					List<SignRuleDept> ruleDepts = signRuleDeptDao.selectListByParams(param);
					StringBuilder ruleForDepOrEmp = new StringBuilder();
					List<Integer> deptIds = new ArrayList<Integer>();
					Map<String,Object> map1 = new HashMap<String,Object>();
					for (SignRuleDept signInRuleDept : ruleDepts) {
						if(signInRuleDept.getDeptId().intValue()==signInRuleDept.getPdetpId().intValue()){//只查询顶级部门id
							map1.put("id", signInRuleDept.getDeptId());
							Department depDB = departmentDao.getDepartmentById(map1);
							if(depDB!=null){
								ruleForDepOrEmp.append(depDB.getName()).append(",");
								deptIds.add(depDB.getId());
							}
						}
					}
					bo.setRuleForDepId(deptIds);
					
					//2.2.查询考勤规则对应的员工姓名
					List<SignRuleUser> ruleEmps = signRuleUserDao.selectListByParams(param);
					List<Long> empIds = new ArrayList<Long>();
					Map<String,Object> map2 = new HashMap<String,Object>();
					for (SignRuleUser signInRuleUser : ruleEmps) {
						map2.put("id", signInRuleUser.getUserId());
						User userDB = userDao.seleteById(map2);
						if(userDB!=null){
							ruleForDepOrEmp.append(userDB.getName()).append(",");
							empIds.add(userDB.getId());
						}
					}
					bo.setRuleForEmpId(empIds);
					String strName = ruleForDepOrEmp.toString();
					if(strName.endsWith(",")){
						strName = strName.substring(0,strName.length()-1);
					}
					bo.setRuleForDepOrEmp(strName);
					if(attendanceRule.getRuleType()==0){
						bo.setRuleTypeStr("常规考勤");
					}else if(attendanceRule.getRuleType()==1){
						bo.setRuleTypeStr("灵活考勤");
					}
					BeanUtils.copyProperties(bo, attendanceRule);
					ruleBoList.add(bo);
				}
				resultMap.put("status", 200);
				resultMap.put("msg", "考勤规则列表获取成功");
				resultMap.put("attendanceRuleList", ruleBoList);
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("status", 500);
				resultMap.put("msg", "系统发生异常请稍后重试");
				throw e;
			}
			
		}
		return resultMap;
	}
	
	/**
	 * 将考勤工作日由数字转换为周几显示
	 * @param workDay
	 * @return
	 */
	private String getWeekToString(String workDay) {
    	String[] split = workDay.split(",");
    	List<String> workDayList = Arrays.asList(split);
    	StringBuilder sb = new StringBuilder();
    	for (String string : workDayList) {
			if("1".equals(string)){
				sb.append("周一");
			}else if("2".equals(string)){
				sb.append(",周二");
			}else if("3".equals(string)){
				sb.append(",周三");
			}else if("4".equals(string)){
				sb.append(",周四");
			}else if("5".equals(string)){
				sb.append(",周五");
			}else if("6".equals(string)){
				sb.append(",周六");
			}else if("7".equals(string)){
				sb.append(",周日");
			}
		}
        return sb.toString();
    }

	@Override
	@Transactional
	public Map<String, Object> updateAttendanceRule(Map<String, Object> params) throws Exception{
		if(MapUtils.isEmpty(params)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"请求参数为空");
		}
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		String id = params.get("id") == null ? null : params.get("id").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司Id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户Id为空");
		}else if(StringUtils.isEmpty(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"考勤规则id为空");
		}else{
			try {
				String attendanceRuleStr = params.get("attendanceRule").toString();
				AttendanceRuleVo ruleVo = JSONObject.parseObject(attendanceRuleStr, AttendanceRuleVo.class);
				
				String[] splitDepts = null;
				List<Integer> checkDetpts = new ArrayList<Integer>();
				List<Integer> ruleForDepIds = ruleVo.getRuleForDepId();
				List<Long> ruleForEmpIds = ruleVo.getRuleForEmpId();
				if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
					params.clear();
					params.put("companyId", companyId);
					StringBuilder currDepts = new StringBuilder();
					int i = 0;
					for (Integer detid : ruleForDepIds) {
						if(i==0||i==ruleForDepIds.size()){
							currDepts.append(detid.toString());
						}else{
							currDepts.append(",").append(detid.toString());
						}
						i++;
					}
					params.put("deptIds", currDepts.toString());
					//调用数据库函数 查询当前部门的所有子部门id集
					String deptChildIds = departmentDao.getDeptChildIds(params);
					splitDepts = deptChildIds.split(",");
					for (String detpid : splitDepts) {
						checkDetpts.add(new Integer(detpid));
					}
				}
				ruleVo.setRuleForDepId(checkDetpts);
				//对数据进行非空校验
				this.checkVoDataIsGood(ruleVo,companyId,userId,"edit",id);
				int idPk = Integer.parseInt(id);
				int companyid = Integer.parseInt(companyId);
				long userid = Long.parseLong(userId);
				//1.1查询当前考勤规则的具体时间段
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("companyId", companyId);
				param.put("ruleId", id);
				param.put("isDel", 0);
				List<SignRuleTime> list = signRuleTimeDao.selectListByParams(param);
				for (SignRuleTime signRuleTime : list) {
					SignRuleTimeHistory timeHistory = new SignRuleTimeHistory();
					BeanUtils.copyProperties(timeHistory, signRuleTime);
					//1.2将具体的考勤时间段作为历史记录进行备份
					timeHistory.setCreateDate(new Date());
					timeHistory.setUpdateDate(new Date());
					timeHistory.setId(null);
					signRuleTimeHistoryDao.insertSelective(timeHistory);
				}
				
				//1.3将规则主表信息生成一条历史记录保存
				int ruleId = Integer.parseInt(id);
				AttendanceRule ruleDB = attendanceRuleDao.selectByPrimaryKey(ruleId);
				SignRuleHistory ruleHistory = new SignRuleHistory();
				BeanUtils.copyProperties(ruleHistory, ruleDB);
				ruleHistory.setRuleId(ruleId);
				ruleHistory.setCreateDate(new Date());
				ruleHistory.setId(null);
				signRuleHistoryDao.insertSelective(ruleHistory);
				
				//2.修改规则主表信息
				AttendanceRule attendanceRule = new AttendanceRule();
				//复制VO属性
				BeanUtils.copyProperties(attendanceRule, ruleVo);
				attendanceRule.setCompanyId(companyid);
				attendanceRule.setCreatorId(userid);
				attendanceRule.setUpdateDate(new Date());
				attendanceRule.setId(idPk);
				attendanceRule.setIsUsed((byte)2);//修改后第二天生效
				attendanceRule.setForceTime(DateUtil.getNextDate(new Date()));//生效时间
				attendanceRuleDao.updateByPrimaryKeySelective(attendanceRule);
				
				//3.修改具体的考勤时间段及工作日
				if(ruleVo.getRuleType()==0){//常规考勤
					
					//常规考勤的工作日的考勤时间段都一样(且只有一条),只需修改一条记录即可
					SignRuleTime ruleTimeDB = list.get(0);
					SignRuleTime updateRuleTime = new SignRuleTime();
					BeanUtils.copyProperties(updateRuleTime, ruleVo);
					updateRuleTime.setId(ruleTimeDB.getId());
					signRuleTimeDao.updateByPrimaryKeySelective(updateRuleTime);
					
				}else if(ruleVo.getRuleType()==1){//灵活考勤
					//灵活考勤不同的工作日,考勤时段会不一样,需修改多条记录  后期做 TODO
				}
				
				//4.先将原来的关系表删除(防止关系维护混乱,采用删除后建立新的关系)
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId", companyId);
				map.put("ruleId", id);
				signRuleDeptDao.deleteByCompanyIdAndRuleId(map);
				signRuleUserDao.deleteByCompanyIdAndRuleId(map);
				
				//5.保存考勤规则部门表
				if(ruleForDepIds!=null&&ruleForDepIds.size()>0){
					params.clear();
					params.put("companyId", companyId);
					for (Integer pdetpId : ruleForDepIds) {
						params.put("deptIds", pdetpId);
						//调用数据库函数 查询当前部门的所有子部门id集
						String deptChildIds = departmentDao.getDeptChildIds(params);
						splitDepts = deptChildIds.split(",");
						for (String detpid : splitDepts) {
							SignRuleDept ruleDept = new SignRuleDept();
							ruleDept.setCompanyId(Integer.parseInt(companyId));
							ruleDept.setDeptId(Integer.parseInt(detpid));
							ruleDept.setPdetpId(pdetpId);//虚拟的父级部门id
							ruleDept.setRuleId(attendanceRule.getId());
							ruleDept.setCreateDate(new Date());
							signRuleDeptDao.insertSelective(ruleDept);
						}
					}
				}			
				
				//6.插入新的考勤员工关系记录
				for (Long empid : ruleForEmpIds) {
					SignRuleUser newRecord = new SignRuleUser();
					newRecord.setCompanyId(companyid);
					newRecord.setUserId(empid);
					newRecord.setRuleId(idPk);
					newRecord.setCreateDate(new Date());
					signRuleUserDao.insertSelective(newRecord);
				}
				resultMap.put("status", 200);
				resultMap.put("msg", "修改成功");
			} catch (Exception e) {
				resultMap.put("status", 500);
				resultMap.put("msg", "系统发生异常请稍候再试");
				throw e;
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getAttendanceRuleDetail(Map<String, Object> params) throws Exception{
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		String id = params.get("id") == null ? null : params.get("id").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"hrm系统用户userId为空");
		}else if(StringUtils.isEmpty(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"考勤规则id为空");
		}else{
			//查询考勤规则基本信息
			AttendanceRule ruleDB = attendanceRuleDao.selectByPrimaryKey(Integer.parseInt(id));
			AttendanceRuleBo bo = new AttendanceRuleBo();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("companyId", companyId);
			param.put("ruleId", ruleDB.getId());
			//判断当前查到的考勤规则是否生效
			List<SignRuleTime> ruleTimes = new ArrayList<SignRuleTime>();
			param.put("isDel", 0);
			ruleTimes = signRuleTimeDao.selectListByParams(param);
			bo.setSignRuleTimeList(ruleTimes);
			//查询考勤规则对应的部门
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyId", companyId);
			map.put("ruleId", id);
			List<SignRuleDept> ruleDepts = signRuleDeptDao.selectListByParams(map);
			List<Integer> depts = new ArrayList<Integer>();
			for (SignRuleDept signInRuleDept : ruleDepts) {
				if(signInRuleDept.getDeptId().intValue()==signInRuleDept.getPdetpId().intValue()){
					depts.add(signInRuleDept.getDeptId());
				}
			}
			//查询考勤规则对应的员工
			List<SignRuleUser> ruleEmps = signRuleUserDao.selectListByParams(map);
			List<Long> emps = new ArrayList<Long>();
			for (SignRuleUser signInRuleUser : ruleEmps) {
				emps.add(signInRuleUser.getUserId());
			}
			try {
				BeanUtils.copyProperties(bo, ruleDB);
				bo.setRuleForDepId(depts);
				bo.setRuleForEmpId(emps);
				resultMap.put("status", 200);
				resultMap.put("msg", "查询成功");
				resultMap.put("ruleDetail", bo);
			}catch (Exception e) {
				throw e;
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getCurrentServerTime(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String time = DateUtil.getTime();
		resultMap.put("status", 200);
		resultMap.put("msg", "系统时间获取成功");
		resultMap.put("serverTime", System.currentTimeMillis());
		resultMap.put("serverTimeFormat", time);
		return resultMap;
	}

	@Override
	public Map<String, Object> getSignRuleForApp(Map<String, Object> params) {
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			AttendanceRule rule = null;
			int signType = 0;
			//1.判断当前用户是否有自己的考勤规则
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyId", companyId);
			map.put("userId", userId);
			List<SignRuleUser> ruleEmps = signRuleUserDao.selectListByParams(map);
			if(ruleEmps!=null&&ruleEmps.size()>0){//有
				SignRuleUser signInRuleUser = ruleEmps.get(0);
				//查询该用户的考勤规则基本信息
				rule = attendanceRuleDao.selectByPrimaryKey(signInRuleUser.getRuleId());
			}else{//无,说明当前用户的考勤规则要通过部门获取
				map.put("id", userId);
				User userDB = userDao.seleteById(map);
				map.put("deptId", userDB.getDeptId());
				List<SignRuleDept> ruleDept = signRuleDeptDao.selectListByParams(map);
				if(ruleDept!=null&&ruleDept.size()>0){
					SignRuleDept signRuleDept = ruleDept.get(0);
					rule = attendanceRuleDao.selectByPrimaryKey(signRuleDept.getRuleId());
				}
			}
			if(rule==null){
				resultMap.put("hasRule", 0);//表示没有考勤
				resultMap.put("serverTime",System.currentTimeMillis());
			}else{
				SignRuleForAppBo appBo = new SignRuleForAppBo();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("companyId", companyId);
				param.put("ruleId", rule.getId());
				param.put("workDay", "%"+DateUtil.getDayofweek(null)+"%");//只查询当天的
				try {
					SignRuleTime ruleTime = new SignRuleTime();
					//2.判断当前查到的考勤规则是否生效
					List<SignRuleTimeHistory> timeHistoryList = null;
					List<SignRuleTime> ruleTimes = null;
					if(rule.getIsUsed()==2){//未生效
						//从规则时间历史表查询该用户的考勤时段
						timeHistoryList = signRuleTimeHistoryDao.selectListByParams(param);
						if(timeHistoryList!=null&&timeHistoryList.size()>0){
							SignRuleTimeHistory signRuleTimeHistory = timeHistoryList.get(0);
							BeanUtils.copyProperties(ruleTime, signRuleTimeHistory);
						}
						
						//从规则主表的历史表查询修改前的数据
						List<SignRuleHistory> list = signRuleHistoryDao.selectListByMap(param);
						if(list!=null&&list.size()>0){
							SignRuleHistory signRuleHistory = list.get(0);
							BeanUtils.copyProperties(rule, signRuleHistory);
							//复制时,主键id会被覆盖,故重新赋值
							rule.setId(signRuleHistory.getRuleId());
						}
					}else if(rule.getIsUsed()==1){//生效
						param.put("isDel", 0);
						ruleTimes = signRuleTimeDao.selectListByParams(param);
						if(ruleTimes!=null&&ruleTimes.size()>0){
							ruleTime = ruleTimes.get(0);
						}
					}
					if((timeHistoryList==null||timeHistoryList.size()==0)&&(ruleTimes==null||ruleTimes.size()==0)){
						resultMap.put("hasRule", 0);//表示没有考勤
						resultMap.put("serverTime",System.currentTimeMillis());
					}else{
						appBo.setId(rule.getId());
						appBo.setLongitude(rule.getPositionLon());
						appBo.setLatitude(rule.getPositionLat());
						appBo.setOffset(rule.getPositionOffset());
						//3.查询用户当天是否有签到签退记录
						map.put("signDate", DateUtil.getCurDay());
						List<SignRecord> recordList = signRecordDao.selectRecordsByParams(map);
						if(recordList!=null&&recordList.size()>0){
							List<String> signTypeList = new ArrayList<String>();
							for (SignRecord signInRecord : recordList) {
								String signTypeStr = signInRecord.getSignType();
								signTypeList.add(signTypeStr);
							}
							//以下逻辑用于给手机端展示应当执行签到还是签退操作(包括是哪个考勤时段的签到或签退)
							if(signTypeList!=null&&signTypeList.size()>0){
								if(!signTypeList.contains(SignTypeMark.SIGNIN.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT.getValue())){
									signType = 1;//签到
								}else if(signTypeList.contains(SignTypeMark.SIGNIN.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT.getValue())){
									signType = 2;//签退
								}else if(!signTypeList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT2.getValue())){
									signType = 3;//签到2
								}else if(signTypeList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT2.getValue())){
									signType = 4;//签退2
								}else if(!signTypeList.contains(SignTypeMark.SIGNIN3.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT3.getValue())){
									signType = 5;//签到3
								}else if(signTypeList.contains(SignTypeMark.SIGNIN3.getValue())&&!signTypeList.contains(SignTypeMark.SIGNOUT3.getValue())){
									signType = 6;//签退3
								}
								if(signTypeList.contains(SignTypeMark.SIGNIN.getValue())&&signTypeList.contains(SignTypeMark.SIGNOUT.getValue())){
									if(StringUtils.isBlank(ruleTime.getSignInTime2())&&StringUtils.isBlank(ruleTime.getSignOutTime2())){
										signType = 20;//(当天的所有考勤已完成,无法继续签到或签退)
									}
								}
								if(signTypeList.contains(SignTypeMark.SIGNIN2.getValue())&&signTypeList.contains(SignTypeMark.SIGNOUT2.getValue())){
									if(StringUtils.isBlank(ruleTime.getSignInTime3())&&StringUtils.isBlank(ruleTime.getSignOutTime3())){
										signType = 20;//(当天的所有考勤已完成,无法继续签到或签退)
									}
								}
								if(signTypeList.contains(SignTypeMark.SIGNIN3.getValue())&&signTypeList.contains(SignTypeMark.SIGNOUT3.getValue())){
									signType = 20;//(当天的所有考勤已完成,无法继续签到或签退)
								}
							}else{
								signType = 1;//签到
							}
						}else{
							signType = 1;//签到
						}
						resultMap.put("hasRule", 1);//表示有考勤
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				resultMap.put("signType", signType);
				resultMap.put("ruleDetail", appBo);
				resultMap.put("serverTime",System.currentTimeMillis());
			}
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> signInOrOut(Map<String, Object> params) throws Exception{
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		String ruleId = params.get("ruleId") == null ? null : params.get("ruleId").toString();
		String longitude = params.get("longitude") == null ? null : params.get("longitude").toString();
		String latitude = params.get("latitude") == null ? null : params.get("latitude").toString();
		//签到地址
		String address = params.get("address") == null ? null : params.get("address").toString();
		//签到类型标记[1:签到 2:签退  3,4; 5,6]
		String signType = params.get("signType") == null ? null : params.get("signType").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		logger.info("=====================请求签到接口参数："+params.toString());
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isEmpty(ruleId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签到规则id为空");
		}else if(StringUtils.isEmpty(address)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签到地址为空");
		}else if(StringUtils.isEmpty(longitude)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"经度为空");
		}else if(StringUtils.isEmpty(latitude)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"纬度为空");
		}else if(StringUtils.isEmpty(signType)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"签到类型标记为空");
		}else{
			//签到状态  1:正常,2:迟到,3:早退, 4:加班
			byte signStatus = 1;
			//详细的签到结果,如迟到10分钟,早退一个小时
			String signResult = "";
			String ruleTime = "";
			Date currTime = new Date();
			String signTypeMark="";
			//1.通过ruleId查询考勤规则
			AttendanceRule ruleDB = attendanceRuleDao.selectByPrimaryKey(Integer.parseInt(ruleId));
			SignRuleHistory ruleHistory = null;
			if(ruleDB==null){
				throw new BizException(BizExceptionMessage.FAILURE,"对不起,您没有考勤规则");
			}else{
				if(ruleDB.getIsUsed()==2){
					Map<String, Object> query = new HashMap<String,Object>();
					query.put("companyId", companyId);
					query.put("ruleId", ruleId);
					List<SignRuleHistory> list = signRuleHistoryDao.selectListByMap(query);
					if(list!=null&&list.size()>0){
						ruleHistory = list.get(0);
					}
					if(ruleHistory==null){
						throw new BizException(BizExceptionMessage.FAILURE,"对不起,您没有考勤规则");
					}else{
						ruleDB = new AttendanceRule();
						BeanUtils.copyProperties(ruleDB, ruleHistory);
						ruleDB.setIsUsed((byte)2);
						ruleDB.setId(ruleHistory.getRuleId());
					}
				}
			}
			
			//2.比较签到地点是否在允许的偏差范围内
			Double distance = Distance.getDistance(Double.parseDouble(longitude),Double.parseDouble(latitude),Double.parseDouble(ruleDB.getPositionLon()),Double.parseDouble(ruleDB.getPositionLat()));
			double offset = ruleDB.getPositionOffset();
			int retval =  distance.compareTo(offset);
		    if(retval > 0) {
		        //表示当前签到地点超出了偏差范围
		    	throw new BizException(BizExceptionMessage.FAILURE,"您当前不在签到地点允许的偏差范围之内");
		    }else if(retval <= 0) {
		    	//查询用户当天的具体的考勤时段
		    	SignRuleTime currDayRuleTime = getDayRuleTime(ruleDB,null);
			
		    	//3.判断当天是否是正常的工作日(如：节假日后期优化考虑)
		    	String workDay = currDayRuleTime.getWorkDay();
		    	String[] split = workDay.split(",");
		    	List<String> workDayList = Arrays.asList(split);
		    	String currWeek = DateUtil.getWeekToNum(currTime)+"";
		    	if(!workDayList.contains(currWeek)){
		    		//分情况考虑:是加班或调休等等(后期做) TODO
		    	}
		    	
		    	//4.查询该用户当天的签到记录,判断他应该做哪个操作
		    	Map<String,Object> map = new HashMap<String,Object>();
		    	map.put("id", userId);
				User userDB = userDao.seleteById(map);
		    	map.put("companyId", companyId);
		    	map.put("deptId", userDB.getDeptId());
		    	map.put("userId", userId);
				map.put("signDate", DateUtil.getCurDay());
				List<SignRecord> recordList = signRecordDao.selectRecordsByParams(map);
				List<String> signTypeStrList = new ArrayList<String>();
				if(recordList!=null&&recordList.size()>0){
					for (SignRecord signInRecord : recordList) {
						String signTypeStr = signInRecord.getSignType();
						signTypeStrList.add(signTypeStr);
					}
				}
		    	//5.计算签到签退结果
				if("1".equals(signType)){
					//判断是否已经签到
					if(signTypeStrList.contains(SignTypeMark.SIGNIN.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签到,请勿重复操作");
					}
					String signInTime = currDayRuleTime.getSignInTime();
					if(compareSignTime(signInTime)){
						//表示当前签到时间早于或等于考勤规则时间,视为正常
						signStatus = SignResult.NORMAL.getValue();
						signResult = "正常";
					}else{
						//表示当前签到时间晚于考勤规则时间,视为迟到
						signStatus = SignResult.LATE.getValue();
						//计算上班迟到时间
						signResult = calSignInResult(signInTime);
					}
					signTypeMark = SignTypeMark.SIGNIN.getValue();
					ruleTime = currDayRuleTime.getSignInTime();
				}else if("2".equals(signType)){
					//签退之前先判断是否已经签到
					if(!signTypeStrList.contains(SignTypeMark.SIGNIN.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"请先完成签到操作");
					}else if(signTypeStrList.contains(SignTypeMark.SIGNOUT.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签退,请勿重复操作");
					}
					String signOutTime = currDayRuleTime.getSignOutTime();
					if(!compareSignTime(signOutTime)){
						//表示当前签退时间晚于或等于考勤规则时间,视为正常
						signStatus = SignResult.NORMAL.getValue();
						signResult = "正常";
					}else{
						//表示当前签退时间早于考勤规则时间,视为早退
						signStatus = SignResult.EARLY.getValue();
						//计算第1个签退早退时间
						signResult = calSignOutResult(signOutTime);
					}
					signTypeMark = SignTypeMark.SIGNOUT.getValue();
					ruleTime = currDayRuleTime.getSignOutTime();
				//第2个签到签退(有则执行)
				}else if("3".equals(signType)){
					if(signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签到,请勿重复操作");
					}
					//先判断第1个签到签退时间段是否已完成
					if(signTypeStrList.contains(SignTypeMark.SIGNIN.getValue())&&signTypeStrList.contains(SignTypeMark.SIGNOUT.getValue())){
						String signInTime2 = currDayRuleTime.getSignInTime2();
						if(StringUtils.isNotBlank(signInTime2)){
							if(compareSignTime(signInTime2)){
								signStatus = SignResult.NORMAL.getValue();
								signResult = "正常";
							}else{
								signStatus = SignResult.LATE.getValue();
								//计算第2个签到 迟到时间
								signResult = calSignInResult(signInTime2);
							}
						}
					}else{
						throw new BizException(BizExceptionMessage.FAILURE,"请先完成第1个考勤时段的签到或签退操作");
					}
					signTypeMark = SignTypeMark.SIGNIN2.getValue();
					ruleTime = currDayRuleTime.getSignInTime2();
				}else if("4".equals(signType)){
					if(signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签退,请勿重复操作");
					}
					if(signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())){
						String signOutTime2 = currDayRuleTime.getSignOutTime2();
						if(StringUtils.isNotBlank(signOutTime2)){
							if(!compareSignTime(signOutTime2)){
								signStatus = SignResult.NORMAL.getValue();
								signResult = "正常";
							}else{
								signStatus = SignResult.EARLY.getValue();
								//计算第2个签退 早退时间
								signResult = calSignOutResult(signOutTime2);
							}
						}
					}else{
						throw new BizException(BizExceptionMessage.FAILURE,"请先完成第2个考勤时段的签到");
					}
					signTypeMark = SignTypeMark.SIGNOUT2.getValue();
					ruleTime = currDayRuleTime.getSignOutTime2();
				//第3个签到签退(有则执行)
				}else if("5".equals(signType)){
					if(signTypeStrList.contains(SignTypeMark.SIGNIN3.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签到,请勿重复操作");
					}
					//先判断第2个签到签退时间段是否已完成
					if(signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())&&signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						String signInTime3 = currDayRuleTime.getSignInTime3();
						if(StringUtils.isNotBlank(signInTime3)){
							if(compareSignTime(signInTime3)){
								signStatus = SignResult.NORMAL.getValue();
								signResult = "正常";
							}else{
								signStatus = SignResult.LATE.getValue();
								//计算第3个签到 迟到时间
								signResult = calSignInResult(signInTime3);
							}
						}
					}else{
						throw new BizException(BizExceptionMessage.FAILURE,"请先完成第2个考勤时段的签到或签退操作");
					}
					signTypeMark = SignTypeMark.SIGNIN3.getValue();
					ruleTime = currDayRuleTime.getSignInTime3();
				}else if("6".equals(signType)){
					if(signTypeStrList.contains(SignTypeMark.SIGNOUT3.getValue())){
						throw new BizException(BizExceptionMessage.FAILURE,"您已签退,请勿重复操作");
					}
					if(signTypeStrList.contains(SignTypeMark.SIGNIN3.getValue())){
						String signOutTime3 = currDayRuleTime.getSignOutTime3();
						if(StringUtils.isNotBlank(signOutTime3)){
							if(!compareSignTime(signOutTime3)){
								signStatus = SignResult.NORMAL.getValue();
								signResult = "正常";
								//目前暂未考虑加班的情况,后期做
								//通过计算一整天所有的签退签退结果,判断是否满足加班条件
								//计算加班时间 TODO
							}else{
								signStatus = SignResult.EARLY.getValue();
								//计算第3个签退 早退时间
								signResult = calSignOutResult(signOutTime3);
							}
						}
					}else{
						throw new BizException(BizExceptionMessage.FAILURE,"请先完成第3个考勤时段的签到");
					}
					ruleTime = currDayRuleTime.getSignOutTime3();
					signTypeMark = SignTypeMark.SIGNOUT3.getValue();
				}
				
				//6.以上条件都满足之后生成一条考勤记录保存到数据库中
				SignRecord record = new SignRecord();
				record.setCompanyId(Integer.parseInt(companyId));
				record.setDeptId(userDB.getDeptId().intValue());
				record.setUserId(new Long(userId));
				record.setRuleId(ruleDB.getId());
				record.setSignType(signTypeMark);
				record.setPositionLon(longitude);//经度
				record.setPositionLat(latitude);//纬度
				record.setPosition(address);
				record.setSignStatus(signStatus);
				record.setSignResult(signResult);
				record.setRuleTime(ruleTime);
				record.setRuleType(ruleDB.getRuleType());
				record.setWorkDay((byte)DateUtil.getDayofweek(null));
				//具体签到时间
				record.setSignTime(currTime);
				//签到日期
				record.setSignDate(new Date());
				//记录生成时间
				record.setCreateDate(currTime);
				signRecordDao.insertSelective(record);
				resultMap.put("status", 200);
				resultMap.put("msg", "签到成功");
		    }
		}
		logger.info("=================请求签到接口结束====================");
		return resultMap;
	}
	
	/**
	 * 自定义时间比较规则：判断当前签到时间是否大于考勤规则时间
	 * 返回true：表示当前签到(签退)时间小于规则时间  
	 *    false:表示当前签到(签退)时间大于规则时间  
	 * @param ruleTime
	 * @return
	 */
	private boolean compareSignTime(String ruleTime){
		if(StringUtils.isNotBlank(ruleTime)){
			String[] split = ruleTime.split(":");
			String ruleTimeHour = split[0];//规则时间：小时
			String ruleTimeMin = split[1];//规则时间：分钟
			try {
				Calendar now = Calendar.getInstance();
				int hour = now.get(Calendar.HOUR_OF_DAY);//当前小时
				int minute = now.get(Calendar.MINUTE);//当前分钟
				if(StringUtils.isNotBlank(ruleTimeHour,ruleTimeMin)){
					int ruleHour = Integer.parseInt(ruleTimeHour);
					int ruleMin = Integer.parseInt(ruleTimeMin);
					if(hour>ruleHour){
						return false;
					}else if(hour==ruleHour){
						//比较分钟
						if(minute>ruleMin){
							return false;
						}else if(minute<=ruleMin){
							return true;
						}
					}else if(hour<ruleHour){
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 计算签到结果(迟到情况):
	 * 如迟到15分钟,迟到1个小时20分钟
	 * @param ruleTime
	 * @return
	 */
	private String calSignInResult(String ruleTime){
		String result="";
		if(StringUtils.isNotBlank(ruleTime)){
			String[] split = ruleTime.split(":");
			String ruleTimeHour = split[0];//规则时间：小时
			String ruleTimeMin = split[1];//规则时间：分钟
			try {
				Calendar now = Calendar.getInstance();
				int hour = now.get(Calendar.HOUR_OF_DAY);//当前小时
				int minute = now.get(Calendar.MINUTE);//当前分钟
				if(StringUtils.isNotBlank(ruleTimeHour,ruleTimeMin)){
					int ruleHour = Integer.parseInt(ruleTimeHour);
					int ruleMin = Integer.parseInt(ruleTimeMin);
					if(hour==ruleHour){
						//比较分钟
						if(minute>ruleMin){
							int m = minute-ruleMin;
							result = "迟到"+m+"分钟";
						}
					}else if(hour>ruleHour){
						int h = hour-ruleHour;
						int h_minute = h*60;
						int m = minute-ruleMin;//(包含负数情况)
						//迟到总的分钟数
						int total_min = h_minute+m;
						int H = total_min/60;//迟到小时数
						int M = total_min%60;//迟到分钟数
						if(total_min<60){
							result = "迟到"+total_min+"分钟";
						}else if(m==0||M==0){
							result = "迟到"+H+"小时";
						}else{
							result = "迟到"+H+"小时"+M+"分钟";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 计算签退结果(早退情况):
	 * 如早退15分钟,早退1个小时20分钟
	 * @param ruleTime
	 * @return
	 */
	private  String calSignOutResult(String ruleTime){
		String result="";
		if(StringUtils.isNotBlank(ruleTime)){
			String[] split = ruleTime.split(":");
			String ruleTimeHour = split[0];//规则时间：小时
			String ruleTimeMin = split[1];//规则时间：分钟
			try {
				Calendar now = Calendar.getInstance();
				int hour = now.get(Calendar.HOUR_OF_DAY);//当前小时
				int minute = now.get(Calendar.MINUTE);//当前分钟
				if(StringUtils.isNotBlank(ruleTimeHour,ruleTimeMin)){
					int ruleHour = Integer.parseInt(ruleTimeHour);
					int ruleMin = Integer.parseInt(ruleTimeMin);
					if(hour==ruleHour){
						//比较分钟
						if(minute<ruleMin){
							int m = ruleMin-minute;
							result = "早退"+m+"分钟";
						}
					}else if(hour<ruleHour){
						int h = ruleHour-hour;
						int h_minute = h*60;
						int m = ruleMin-minute;//(包含负数情况)
						//迟到总的分钟数
						int total_min = h_minute+m;
						int H = total_min/60;//迟到小时数
						int M = total_min%60;//迟到分钟数
						if(total_min<60){
							result = "早退"+total_min+"分钟";
						}else if(m==0||M==0){
							result = "早退"+H+"小时";
						}else{
							result = "早退"+H+"小时"+M+"分钟";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> deleteAttendanceRule(Map<String, Object> params) {
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		String ruleId = params.get("ruleId") == null ? null : params.get("ruleId").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else if(StringUtils.isEmpty(ruleId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"规则id为空");
		}else{
			try {
				//1.删除规则部门表记录
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId", companyId);
				map.put("ruleId", ruleId);
				signRuleDeptDao.deleteByCompanyIdAndRuleId(map);
				//2.删除规则用户表记录
				signRuleUserDao.deleteByCompanyIdAndRuleId(map);
				
				//3.删除规则时间表记录(逻辑删除)
				map.put("isDel", 1);
				signRuleTimeDao.updateByMap(map);
				//4.删除规则主表记录(逻辑删除)
				AttendanceRule record = new AttendanceRule();
				record.setId(Integer.parseInt(ruleId));
				record.setIsDel((byte)1);
				attendanceRuleDao.updateByPrimaryKeySelective(record);
				resultMap.put("status", 200);
				resultMap.put("msg", "删除成功");
			} catch (Exception e) {
				resultMap.put("status", 500);
				resultMap.put("msg", "服务器发生异常");
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getSignRecords(Map<String, Object> params) throws Exception{
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		String queryDate = params.get("queryDate") == null ? null : params.get("queryDate").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		logger.info("=====================请求getSignRecords接口参数："+params.toString());
		if(StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"公司id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"用户id为空");
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", userId);
			User userDB = userDao.seleteById(map);
			map.put("companyId", companyId);
	    	map.put("deptId", userDB.getDeptId());
	    	map.put("userId", userId);
			if(StringUtils.isNotBlank(queryDate)){
				map.put("signDate", queryDate);//某 天
			}else{
				map.put("signDate", DateUtil.getCurDay());//今天
			}
			map.remove("id");
			List<SignRecord> recordList = signRecordDao.selectRecordsByParams(map);
			//组装APP端需要的数据
			List<SignRecordBo> list1 = new ArrayList<SignRecordBo>();//第一个考勤时段
			List<SignRecordBo> list2 = new ArrayList<SignRecordBo>();//第二个考勤时段
			List<SignRecordBo> list3 = new ArrayList<SignRecordBo>();//第三个考勤时段
			List<List<SignRecordBo>> signRecordBoList = new ArrayList<List<SignRecordBo>>();
			
			List<String> signTypeStrList = new ArrayList<String>();
			if(recordList!=null&&recordList.size()>0){
				for (SignRecord signRecord : recordList) {
					String signTypeStr = signRecord.getSignType();
					signTypeStrList.add(signTypeStr);
				}
			}
			
			AttendanceRule rule = null;
			//判断当前用户是否有自己的考勤规则
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("companyId", companyId);
			map2.put("userId", userId);
			List<SignRuleUser> ruleEmps = signRuleUserDao.selectListByParams(map2);
			if(ruleEmps!=null&&ruleEmps.size()>0){//有
				SignRuleUser signInRuleUser = ruleEmps.get(0);
				//查询该用户的考勤规则基本信息
				rule = attendanceRuleDao.selectByPrimaryKey(signInRuleUser.getRuleId());
			}else{//无,说明当前用户的考勤规则要通过部门获取
				map2.put("deptId", userDB.getDeptId());
				List<SignRuleDept> ruleDept = signRuleDeptDao.selectListByParams(map2);
				if(ruleDept!=null&&ruleDept.size()>0){
					SignRuleDept signRuleDept = ruleDept.get(0);
					rule = attendanceRuleDao.selectByPrimaryKey(signRuleDept.getRuleId());
				}
			}
			if(rule==null){
				resultMap.put("records", signRecordBoList);
				resultMap.put("hasRule", 0);//无考勤规则
				return resultMap;
			}
			//查询用户某天具体的考勤时段
			SignRuleTime currDayRuleTime = getDayRuleTime(rule,queryDate);
			int num = 0;//考勤时段的个数
			if(currDayRuleTime!=null){
				if(StringUtils.isNotBlank(currDayRuleTime.getSignInTime3())&&StringUtils.isNotBlank(currDayRuleTime.getSignOutTime3())){
					num = 3;
				}else if(StringUtils.isNotBlank(currDayRuleTime.getSignInTime2())&&StringUtils.isNotBlank(currDayRuleTime.getSignOutTime2())){
					num = 2;
				}else if(StringUtils.isNotBlank(currDayRuleTime.getSignInTime())&&StringUtils.isNotBlank(currDayRuleTime.getSignOutTime())){
					num = 1;
				}
			}
			
			if(recordList==null||recordList.size()==0){//如果没有签到记录
				SignRecordBo bo1 = new SignRecordBo();
				//status:0表示未签
				bo1.setStatus(0);
				bo1.setSignType(1);
				bo1.setRuleTime(currDayRuleTime.getSignInTime());
				SignRecordBo bo2 = new SignRecordBo();
				//status:0表示未签
				bo2.setStatus(0);
				bo2.setSignType(2);
				bo2.setRuleTime(currDayRuleTime.getSignOutTime());
				
				SignRecordBo bo3 = new SignRecordBo();
				bo3.setStatus(0);
				bo3.setSignType(1);
				bo3.setRuleTime(currDayRuleTime.getSignInTime2());
				SignRecordBo bo4 = new SignRecordBo();
				bo4.setStatus(0);
				bo4.setSignType(2);
				bo4.setRuleTime(currDayRuleTime.getSignOutTime2());
				
				SignRecordBo bo5 = new SignRecordBo();
				bo5.setStatus(0);
				bo5.setSignType(1);
				bo5.setRuleTime(currDayRuleTime.getSignInTime3());
				SignRecordBo bo6 = new SignRecordBo();
				bo6.setStatus(0);
				bo6.setSignType(2);
				bo6.setRuleTime(currDayRuleTime.getSignOutTime3());
				//如果用户当天没有考勤记录,则根据考勤时段返回其对应的手机端展示的结果
				if(num==1){
					list1.add(bo1);
					list1.add(bo2);
					signRecordBoList.add(list1);
				}else if(num==2){
					list1.add(bo1);
					list1.add(bo2);
					list2.add(bo3);
					list2.add(bo4);
					signRecordBoList.add(list1);
					signRecordBoList.add(list2);
				}else if(num==3){
					list1.add(bo1);
					list1.add(bo2);
					list2.add(bo3);
					list2.add(bo4);
					list3.add(bo5);
					list3.add(bo6);
					signRecordBoList.add(list1);
					signRecordBoList.add(list2);
					signRecordBoList.add(list3);
				}
			}else{
				//第1个签到签退时间段
				for (SignRecord signRecord : recordList) {
					SignRecordBo bo = new SignRecordBo();
					if(signRecord.getSignType().equals(SignTypeMark.SIGNIN.getValue())){
						this.setSignRecordValue(signRecord,bo);
						bo.setSignType(1);
						list1.add(bo);
					}else if(signRecord.getSignType().equals(SignTypeMark.SIGNOUT.getValue())){
						this.setSignRecordValue(signRecord,bo);
						bo.setSignType(2);
						list1.add(bo);
					}
				}
				//补全第一个考勤时段的考勤记录(手机端展示用)
				if(signTypeStrList.contains(SignTypeMark.SIGNIN.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT.getValue())){
					SignRecordBo out = new SignRecordBo();
					out.setSignType(2);
					out.setStatus(0);
					out.setRuleTime(currDayRuleTime.getSignOutTime());
					list1.add(out);
				}
				signRecordBoList.add(list1);
				
				//第2个签到签退时间段
				for (SignRecord signInRecord : recordList) {
					SignRecordBo bo = new SignRecordBo();
					if(signInRecord.getSignType().equals(SignTypeMark.SIGNIN2.getValue())){
						this.setSignRecordValue(signInRecord,bo);
						bo.setSignType(1);
						list2.add(bo);
					}else if(signInRecord.getSignType().equals(SignTypeMark.SIGNOUT2.getValue())){
						this.setSignRecordValue(signInRecord,bo);
						bo.setSignType(2);
						list2.add(bo);
					}
				}
				//补全第2个考勤时段的考勤记录(手机端展示用)
				if(num==2){
					if(signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						SignRecordBo out2 = new SignRecordBo();
						out2.setSignType(2);
						out2.setStatus(0);
						out2.setRuleTime(currDayRuleTime.getSignOutTime2());
						list2.add(out2);
					}else if(!signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						SignRecordBo in2 = new SignRecordBo();
						in2.setSignType(1);
						in2.setStatus(0);
						in2.setRuleTime(currDayRuleTime.getSignInTime2());
						list2.add(in2);
						SignRecordBo out2 = new SignRecordBo();
						out2.setSignType(2);
						out2.setStatus(0);
						out2.setRuleTime(currDayRuleTime.getSignOutTime2());
						list2.add(out2);
					}
					signRecordBoList.add(list2);
				}
				
				//第3个签到签退时间段
				for (SignRecord signInRecord : recordList) {
					SignRecordBo bo = new SignRecordBo();
					if(signInRecord.getSignType().equals(SignTypeMark.SIGNIN3.getValue())){
						this.setSignRecordValue(signInRecord,bo);
						bo.setSignType(1);
						list3.add(bo);
					}else if(signInRecord.getSignType().equals(SignTypeMark.SIGNOUT3.getValue())){
						this.setSignRecordValue(signInRecord,bo);
						bo.setSignType(2);
						list3.add(bo);
					}
				}
				//补全第2和第3个考勤时段的考勤记录(手机端展示用)
				if(num==3){
					if(signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						SignRecordBo out2 = new SignRecordBo();
						out2.setSignType(2);
						out2.setStatus(0);
						out2.setRuleTime(currDayRuleTime.getSignOutTime2());
						list2.add(out2);
					}else if(!signTypeStrList.contains(SignTypeMark.SIGNIN2.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT2.getValue())){
						SignRecordBo in2 = new SignRecordBo();
						in2.setSignType(1);
						in2.setStatus(0);
						in2.setRuleTime(currDayRuleTime.getSignInTime2());
						list2.add(in2);
						SignRecordBo out2 = new SignRecordBo();
						out2.setSignType(2);
						out2.setStatus(0);
						out2.setRuleTime(currDayRuleTime.getSignOutTime2());
						list2.add(out2);
					}
					signRecordBoList.add(list2);
					
					if(signTypeStrList.contains(SignTypeMark.SIGNIN3.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT3.getValue())){
						SignRecordBo out3 = new SignRecordBo();
						out3.setSignType(2);
						out3.setStatus(0);
						out3.setRuleTime(currDayRuleTime.getSignOutTime3());
						list3.add(out3);
					}else if(!signTypeStrList.contains(SignTypeMark.SIGNIN3.getValue())&&!signTypeStrList.contains(SignTypeMark.SIGNOUT3.getValue())){
						SignRecordBo in3 = new SignRecordBo();
						in3.setSignType(1);
						in3.setStatus(0);
						in3.setRuleTime(currDayRuleTime.getSignInTime3());
						list3.add(in3);
						SignRecordBo out3 = new SignRecordBo();
						out3.setSignType(2);
						out3.setStatus(0);
						out3.setRuleTime(currDayRuleTime.getSignOutTime3());
						list3.add(out3);
					}
					signRecordBoList.add(list3);
				}
			}
			resultMap.put("hasRule", 1);
			resultMap.put("records", signRecordBoList);
		}
		logger.info("=====================请求getSignRecords接口结束");
		return resultMap;
	}
	
	/**
	 * 查询用户某天具体的考勤时段
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	private SignRuleTime getDayRuleTime(AttendanceRule rule,String date) throws Exception{
		SignRuleTime signRuleTime = new SignRuleTime(); 
		if(rule==null){
			return signRuleTime;
		}
    	Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", rule.getCompanyId());
		param.put("ruleId", rule.getId());
		param.put("workDay", "%"+DateUtil.getDayofweek(date)+"%");
		if(rule.getIsUsed()==2){//未生效
			//从规则时间历史表查询该用户的考勤时段
			List<SignRuleTimeHistory> timeHistoryList = signRuleTimeHistoryDao.selectListByParams(param);
			if(timeHistoryList!=null&&timeHistoryList.size()>0){
				SignRuleTimeHistory signRuleTimeHistory = timeHistoryList.get(0);
				BeanUtils.copyProperties(signRuleTime, signRuleTimeHistory);
			}
		}else if(rule.getIsUsed()==1){//生效
			param.put("isDel", 0);
			List<SignRuleTime> ruleTimes = signRuleTimeDao.selectListByParams(param);
			if(ruleTimes!=null&&ruleTimes.size()>0){
				signRuleTime = ruleTimes.get(0);
			}
		}
		return signRuleTime;
	}
	
	private void setSignRecordValue(SignRecord signRecord,SignRecordBo bo){
		//签到或签退时间
		String formatDate = DateUtil.formatDate(signRecord.getSignTime(), "HH:mm:ss");
		bo.setSignTime(formatDate);
		bo.setRuleTime(signRecord.getRuleTime());
		bo.setAddress(signRecord.getPosition());
		//签到结果 0：未签，1：正常，2：迟到，3：早退
		bo.setStatus(signRecord.getSignStatus());
	}

	@Override
	@Transactional
	public int updateIsUsed() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("forceTime", DateUtil.getCurDay()+"%");//生效日期
		map.put("isUsed", 2);//只查询未生效的
		map.put("isDel", 0);//查询未删除的
		List<AttendanceRule> list = attendanceRuleDao.selectAttendanceRuleList(map);
		if(list!=null&&list.size()>0){
			for (AttendanceRule attendanceRule : list) {
				AttendanceRule update = new AttendanceRule();
				update.setIsUsed((byte)1);//改为生效
				update.setId(attendanceRule.getId());
				update.setUpdateDate(new Date());
				attendanceRuleDao.updateByPrimaryKeySelective(update);
			}
			return 1;
		}
		return 0;
	}
}
