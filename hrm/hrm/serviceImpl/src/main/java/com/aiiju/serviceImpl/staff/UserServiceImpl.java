package com.aiiju.serviceImpl.staff;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.aiiju.bean.common.BasicEnum;
import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.duty.PositionTypeInfo;
import com.aiiju.bean.oa.position.CompanyPositionInfo;
import com.aiiju.bean.oa.staff.InviteUser;
import com.aiiju.bean.oa.staff.LeaveUser;
import com.aiiju.bean.oa.staff.PersonnelAffairs;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.staff.UserCount;
import com.aiiju.dao.oa.common.IBasicEnumDao;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.duty.IPositionTypeInfoDao;
import com.aiiju.dao.oa.position.ICompanyPositionInfoDao;
import com.aiiju.dao.oa.staff.IContractAgreementDao;
import com.aiiju.dao.oa.staff.IEducationLogDao;
import com.aiiju.dao.oa.staff.IInviteUserDao;
import com.aiiju.dao.oa.staff.IJobStatusLogDao;
import com.aiiju.dao.oa.staff.ILanguageAbilityLogDao;
import com.aiiju.dao.oa.staff.ILeaveUserDao;
import com.aiiju.dao.oa.staff.IPersonnelAffairsDao;
import com.aiiju.dao.oa.staff.IProfessionJobQualificationDao;
import com.aiiju.dao.oa.staff.IProfessionSkillQualificationDao;
import com.aiiju.dao.oa.staff.IReportRelationDao;
import com.aiiju.dao.oa.staff.ISocietyRelationsDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.staff.IUserDutyLevelLogDao;
import com.aiiju.dao.oa.staff.IWorkCompanyLogDao;
import com.aiiju.service.IPermissionManageService;
import com.aiiju.service.staff.IUserService;
import com.aiiju.serviceImpl.CommonPageService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.common.ClazzByType;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.MD5;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.RecursionSort;
import com.aiiju.util.common.RemoveRepeatTools;
import com.aiiju.util.common.Result;
import com.aiiju.util.common.Tools;
import com.aiiju.util.enums.Certificate;
import com.aiiju.util.enums.Eduational;
import com.aiiju.util.enums.Formal;
import com.aiiju.util.enums.JobStatus;
import com.aiiju.util.enums.LogInError;
import com.aiiju.util.enums.Marital;
import com.aiiju.util.enums.UserXls;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.excel.CreateXLSForUser;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.exception.ExceptionUtil;
import com.aiiju.util.http.APIRequestUtil;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.aiiju.util.page.Pagination;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.internal.LinkedTreeMap;

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonPageService implements IUserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserDao dao;
	@Autowired
	private IContractAgreementDao contractDao;
	@Autowired
	private IEducationLogDao educationDao;
	@Autowired
	private IJobStatusLogDao jobDao;
	@Autowired
	private ILanguageAbilityLogDao languageDao;
	@Autowired
	private IPersonnelAffairsDao personnelDao;
	@Autowired
	private IProfessionJobQualificationDao jobQualificationDao;
	@Autowired
	private IProfessionSkillQualificationDao skillQualificationDao;
	@Autowired
	private IReportRelationDao relationDao;
	@Autowired
	private ISocietyRelationsDao societyRelationsDao;
	@Autowired
	private IUserDutyLevelLogDao dutyLevelLogDao;
	@Autowired
	private IWorkCompanyLogDao workCompanyDao;
	@Autowired
	private ICommonPageDao commonPageDao;
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IPermissionManageService permissionManageService;
	
	@Transactional
	@Override
	public int deleteByIds(Map<String, Object> map) {
		String id = map.get("id")==null?null:map.get("id").toString();
		
		//因现在的设计无这参数，需要通过ids去oa_user表找到员工的部门id后才能更新，加大数据库的使用，先在这做判断，前段重新设计后，当有这参数就跳过查询user表的步骤
		String pitchOnDeptId  = map.get("pitchOnDeptId")==null?null:map.get("pitchOnDeptId").toString();
		if(id==null){
			throw new BizException("300", "参数为空");
		}
		
		String[] ids = id.split(",");
		
		String[] pitchOnDeptIds =null;
		if(StringUtils.isEmpty(pitchOnDeptId)){
			//
			Map<String, Object> params= new HashMap<String, Object>();
			params.put("sendToPersonIds", ids);
			params.put("companyId", map.get("companyId").toString());
			 pitchOnDeptIds = dao.selectUserDeptIdArrayById(params);
				
			
		}else{
			//通过前段选中传递的pitchOnDeptId，来更新当前在编人数
			if(pitchOnDeptId==null){
				throw new BizException("300", "参数为空");
			}
			 pitchOnDeptIds = pitchOnDeptId.split(",");
			}
			
		// 在组织管理中批量删除员工前，先更新当前在编人数（可能是不同的部门，用循环）
			Map<String, Object> deptIDCount = RemoveRepeatTools.integrationCount(pitchOnDeptIds);
	
		for ( Map.Entry<String, Object> entry : deptIDCount.entrySet()) {
			Map<String, Object> updateMap= new HashMap<String, Object>();
			updateMap.put("deptId", entry.getKey());
			updateMap.put("curPersonNum", "-"+entry.getValue().toString());
			departmentDao.updateDepartment(updateMap);
		}
		
		//调用PHP接口进行删除
		map.put("visit_id", map.get("companyId").toString());
		String empLoginIds = map.get("empLoginId").toString();
		map.put("user_ids", empLoginIds);//被删除员工的id集合
		Result logInfo = APIRequestUtil.getResultFromPhpAPI("php.login.url", "php.deleteUser", map, "2", "1"); 
		//当php接口删除成功之后再删除oa_user表的用户信息	
		if("0".equals(logInfo.getError_code())){
			dao.deleteByIds(ids);
			contractDao.deleteByUserId(ids);
			educationDao.deleteByUserId(ids);
			jobDao.deleteByUserId(ids);
			languageDao.deleteByUserId(ids);
			personnelDao.deleteByUserId(ids);
			jobQualificationDao.deleteByUserId(ids);
			skillQualificationDao.deleteByUserId(ids);
			relationDao.deleteByUserId(ids);
			societyRelationsDao.deleteByUserId(ids);
			dutyLevelLogDao.deleteByUserId(ids);
			workCompanyDao.deleteByUserId(ids);
			return 1;
		}else{
			return 0;
		}
	}

	@Transactional
	@Override
	public String addUser(Map<String, Object> map) {
		logger.info("--------------------添加员工START---------------------------");
		String obj = map.get("user").toString();
		String companyId=map.get("companyId").toString();
		User user = JSONObject.parseObject(obj, User.class);
		user.setCompanyId(Long.parseLong(companyId));
		if(user.getDeptId()==null){
			throw new BizException(BizExceptionMessage.DETY_NAME_EXIST,"请选择部门");
		}		
		if(user.getUserNo()==null||user.getUserNo().equals("")){
			throw new BizException(BizExceptionMessage.DETY_NAME_EXIST,"没有员工号");
		}		
		Map<String,Object> maps=new HashMap<String, Object>();
		maps.put("companyId", companyId);
		maps.put("userNo", user.getUserNo());
		int num = dao.countUser(maps);
		if(num != 0&&user.getUserNo()!=null){
			//提示工号已存在
			throw new BizException(BizExceptionMessage.PROP_ERROR, LogInError.getName("-1"));
		}
		
		Map<String, Object> paramsMap= new HashMap<String, Object>();
		paramsMap.put("visit_id", user.getCompanyId());
		paramsMap.put("name", user.getName());
		paramsMap.put("phone", user.getPhone());
		//调用PHP添加登录用户的接口 oa_user/addUserWithNotification  该接口会生成一条用户记录保存到xdianshang_dsb库中的special_user表中
		Result logInfo = APIRequestUtil.getResultFromPhpAPI("php.login.url", "php.login.par", paramsMap, "1", "1");
		Object data = logInfo.getData();
		String msg = "";
		String loginUserId="";
		//此种情况说明该手机号在大系统中查到了用户,但是data返回为null
		if("005".equals(logInfo.getError_code())&&"1".equals(logInfo.getSub_code())){
			Map<String, Object> phoneMap = new HashMap<String, Object>();
	        phoneMap.put("phone", user.getPhone());
	        //通过手机号查询oa_user表中是否已经有用户存在
	        int countUser = this.dao.countUser(phoneMap);
	        if (countUser == 1) {
	          msg = "手机号已存在";
	          //提示手机号已存在
	          throw new BizException(BizExceptionMessage.PROP_ERROR, msg);
	        }
	    }
		if(data!=null&&!"".equals(data)) {
			//data不为null说明是正常的添加
			JSONObject userJson = (JSONObject)data;
			loginUserId = userJson.getString("id");
		}else if(data==null){
			//通过手机号重新调用ajuc的接口查询用户信息(主要用id)
			paramsMap.clear();
			paramsMap.put("phone", user.getPhone());
			Result result = APIRequestUtil.getResultFromPhpAPI("php.login.url", "php.getUserByPhone", paramsMap, "1", "1");
			if ("0".equals(result.getError_code())&&"0".equals(result.getSub_code())){
				Object data2 = result.getData();
				if(com.aiiju.util.http.StringUtils.isNotBlank(data2)){
					JSONArray array = (JSONArray)data2;
					JSONObject json = (JSONObject) array.get(0);
					loginUserId = json.getString("id");
				}
			}else{
				msg = LogInError.getName(logInfo.getError_code());
		        throw new BizException(BizExceptionMessage.PROP_ERROR, msg);
			}
		}
		user.setLoginUserId(Long.parseLong(loginUserId));
		//现在HRM和大系统的主键同步，不采用自增长，采用调用PHP后返回的ID值，loginuserid字段先不取消，可能SQL会有影响
		user.setId(Long.parseLong(loginUserId));
		if(user.getBirdthDay()!=null){
			long age=DateUtil.getDaySub(DateUtil.GLFormDate(user.getBirdthDay().toString()), DateUtil.getDay());
			user.setAge((int)age/365);
		}
		dao.insert(user);
		// 在组织管理中添加员工时，并更新当前在编人数 
		Map<String, Object> updateMap= new HashMap<String, Object>();
		updateMap.put("deptId", user.getDeptId());
		updateMap.put("curPersonNum", "1");
		departmentDao.updateDepartment(updateMap);
		msg  = LogInError.getName("1");
		logger.info("--------------------添加员工END---------------------------");
		return msg;
	}

	
	@Override
	public Map<String, Object> getUsers(Map<String, Object> map) {
		Map<String, Object> users = new HashMap<String, Object>();
		String from = map.get("from")==null?null:map.get("from").toString();
		
		map.remove("loginUserId");
		map.remove("phone");
		String id = map.get("id") == null ? null : map.get("id").toString();
		if(id!=null){
			Map<String, Object> idsMap = new HashMap<String, Object>();
			String[] ids=id.split(",");
			
			if(from==null||!from.equals("APP")){
				idsMap.put("ids", ids);
			}else {
				idsMap.put("loginUserIds", ids);	
			}
			List<User> list=dao.selectByExample(idsMap);
			users.put("user", list);
			return users;
		}
		
		//该部门及其子部门
		List<Department> showDepts = new ArrayList<Department>();
		//获取所有部门
		Map<String, Object> deptMap = new HashMap<String, Object>();
		deptMap.put("companyId", map.get("companyId"));
		deptMap.put("deptId", map.get("deptId"));
		List<Department> allDepts= departmentDao.getDepartmentList(deptMap);
		//获取当前查询的部门列表
		List<Department> curDepts=new ArrayList<Department>();
		Department curDept = new Department();
		if(map.get("deptId")!=null&&!"".equals(map.get("deptId"))){
			curDept.setId(Integer.parseInt(map.get("deptId").toString()));
		}else {
			Department dept=departmentDao.selectDepartmentByCompanyId(deptMap);
			curDept.setId(dept.getId());
		}
		curDepts.add(curDept);
		List<Department> deptList=DeptUtil.getAllDepts(curDepts, allDepts, showDepts);
		map.put("deptIds", deptList);
		
		//APP 不传分页参数时，给所有信息,分页对象为null
		Pagination pt = null;
		if(map.get("from")==null||!map.get("from").toString().equals("APP")
					||(map.get("from").toString().equals("APP")
							&&!StringUtils.isEmpty(map.get("pageNum")==null?null:map.get("pageNum").toString())
							&&!StringUtils.isEmpty(map.get("pageSize")==null?null:map.get("pageSize").toString()))){
			
			int count = dao.countUser(map);// 查询总数
			int pageNum = Integer.parseInt(map.get("pageNum").toString());
			int pageSize = Integer.parseInt(map.get("pageSize").toString());
			pt = new Pagination(count, pageNum, pageSize);
			// 添加条件
			map.put("pageSize", pageSize);
			map.put("startRow", pt.getStartPos());
		}
		
		map.put("status", 1);//在职
		
		List<User> list = dao.selectByExample(map);
		Jedis redis = jedisPool.getResource();
		for (User user : list) {
			if(user.getCertificateType()!=null&&!"".equals(user.getCertificateType())){
				user.setCertificateTypeName(Certificate.getName(user.getCertificateType().toString()));
			}
			if(user.getEduational()!=null&&!"".equals(user.getEduational())){
				user.setEduational(Eduational.getName(user.getEduational()));
			}
			if(user.getFormalFace()!=null&&!"".equals(user.getFormalFace())){
				user.setFormal(Formal.getName(user.getFormalFace().toString()));
			}
			if(user.getMaritalStatus()!=null&&!"".equals(user.getMaritalStatus())){
				user.setMarital(Marital.getName(user.getMaritalStatus().toString()));
			}
			if(user.getNation()!=null&&!"".equals(user.getNation())){
				user.setNationName(redis.get("4_"+user.getNation()));
			}
			user.setUserDrawName(user.getUserDraw());
			user.setStatus(JobStatus.getName(user.getStatus()));
		}
		redis.close();
		users.put("users", list);
		users.put("page", pt);
		return users;

	}

	@Override
	public Map<String, Object> getUserById(Map<String, Object> map) {
		Map<String, Object> mapUser = new HashMap<String, Object>();
//		String id = map.get("id").toString();
		String id = map.get("id") == null ? null : map.get("id").toString();
		//APP查看选中人的详细信息的loginUserId
		String pitchOnUserID = map.get("pitchOnUserID") == null ? null : map.get("pitchOnUserID").toString();
		
		if(StringUtils.isEmpty(id)&&StringUtils.isEmpty(pitchOnUserID)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}else{
			Map<String,Object> m = new HashMap<>();
			
			//APP 需要选中人的详细
			if (map.get("from")!=null&&map.get("from").toString().equals("APP")) {
				m.put("id", Long.parseLong(pitchOnUserID)) ;
			}else{
				m.put("id", Long.parseLong(id)) ;
				
			}
			mapUser.put("user", dao.seleteById(m));
		}
		return mapUser;
	}
	@Transactional
	@Override
	public int updateUser(Map<String, Object> map) {
		String obj = map.get("user").toString();
		String companyId=map.get("companyId")==null?null:map.get("companyId").toString();
		User user = JSONObject.parseObject(obj, User.class);
		user.setCompanyId(Long.parseLong(companyId));
		if(!StringUtils.isEmpty(user.getTwoLevelPwd())){
			user.setTwoLevelPwd(MD5.md5(user.getTwoLevelPwd()));
		}
		if(user.getBirdthDay()!=null){
			long age=DateUtil.getDaySub(DateUtil.GLFormDate(user.getBirdthDay().toString()), DateUtil.getDay());
			user.setAge((int)age/365);
		}
		
		Map<String,Object> maps=new HashMap<String, Object>();
		maps.put("companyId",companyId);
		maps.put("userNo", user.getUserNo());
		List<User> list=dao.selectByExample(maps);
		
		if(list==null||list.size()==0){//说明修改了员工号(则用id去查询)
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("id", user.getId());
			User userDB = dao.seleteById(map2);
			if(userDB!=null&&user.getDeptId().longValue()!=userDB.getDeptId().longValue()){//说明修改了部门
				//A部门减少在编员工数
				Map<String, Object> reduceMap= new HashMap<String, Object>();
				reduceMap.put("deptId", userDB.getDeptId());
				reduceMap.put("curPersonNum", "-1");
				departmentDao.updateDepartment(reduceMap);
				
				//B部门增加在编员工数
				Map<String, Object> addMap= new HashMap<String, Object>();
				addMap.put("deptId", user.getDeptId());
				addMap.put("curPersonNum", "1");
				departmentDao.updateDepartment(addMap);
			}
		}else if(list!=null&&list.size()>=1){
			for (User user2 : list) {
				if(user2!=null&&user.getId().longValue()!=user2.getId().longValue() && user.getUserNo().equals(user2.getUserNo())){
					throw new BizException(BizExceptionMessage.USERNO_IS_EXIST,"员工号已存在");
				}
			}
		}
		//TODO 这里调用php更新角色接口
		if(user.getRoleIds()!=null&&map.get("php")!=null){
			Map<String, Object> paramsMap= new HashMap<String, Object>();
			paramsMap.put("visit_id", user.getCompanyId());
			paramsMap.put("user_id", user.getLoginUserId());
			paramsMap.put("roles", user.getRoleIds());
			Result logInfo=APIRequestUtil.getResultFromPhpAPI("php.login.url", "php.role.parme", paramsMap, "2", "1");
			if(!"0".equals(logInfo.getError_code())){
				throw new BizException(BizExceptionMessage.PROP_ERROR, LogInError.getName(logInfo.getError_code()));
			}
		}
	
		
		
		return dao.updateByExample(user);
	}

	@Autowired
	private IPositionTypeInfoDao positDao;

	@Override
	public Map<String, Map<String, Integer>> getUserByDepartment(Map<String, Object> map) {
		Map<String, Map<String, Integer>> mapall = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> sexmap = new HashMap<String, Integer>();

		if(map.get("deptId")!=null&&!"".equals(map.get("deptId"))){
			Map<String, Object> maps= new HashMap<String, Object>();
			maps.put("companyId", map.get("companyId"));
			//该部门及其子部门
			List<Department> showDepts = new ArrayList<Department>();
			//获取所有部门
			List<Department> allDepts= departmentDao.getDepartmentList(maps);
			//获取当前查询的部门列表
			List<Department> curDepts=new ArrayList<Department>();
			Department curDept = new Department();
			curDept.setId(Integer.parseInt(map.get("deptId").toString()));
			curDepts.add(curDept);
			List<Department> list=DeptUtil.getAllDepts(curDepts, allDepts, showDepts);
			map.put("deptIds", list);
		}
		if (map.get("sex") != null&&!"".equals(map.get("sex"))) {
			map.put("status", 1);//在职
			logger.info("-----------------------统计性别START----------------------------");
			List<UserCount> sexs = dao.countSex(map);
			logger.info("-----------------------"+sexs.size()+"----------------------------");
			for (UserCount userCount : sexs) {
				logger.info("-----------------------"+userCount.toString()+"----------------------------");
				if (1==userCount.getSex()) {
					if(userCount.getNums()!=null){
						sexmap.put("男", userCount.getNums());// 性别
					}else {
						sexmap.put("男", 0);// 性别
					}
				} else if (2==userCount.getSex()){
					if(userCount.getNums()!=null){
						sexmap.put("女", userCount.getNums());// 性别
					}else {
						sexmap.put("女", 0);// 性别
					}
				}
			}
			mapall.put("性别", sexmap);
			logger.info("-----------------------统计性别END----------------------------");
		}
		if (map.get("status") != null&&!"".equals(map.get("status"))) {
			//TODO 
			logger.info("-----------------------统计状态START----------------------------");
			Map<String, Integer> jobmap = new HashMap<String, Integer>();
			List<UserCount> statuss = dao.countStatus(map);
			logger.info("-----------------------"+statuss.size()+"----------------------------");
			for (UserCount userCount : statuss) {
				int muns=0;
				if(userCount.getNums()!=null){
					muns=userCount.getNums();
				}
				logger.info("-----------------------"+userCount.toString()+"----------------------------");
				jobmap.put(JobStatus.getName(userCount.getStatus()),muns);
			}
			mapall.put("状态", jobmap);
			logger.info("-----------------------统计状态END----------------------------");
		}
		if (map.get("eduationalLevel") != null&&!"".equals(map.get("eduationalLevel"))) {
			map.put("status", 1);//在职
			logger.info("-----------------------统计学历START----------------------------");
			Map<String, Integer> eduatmap = new HashMap<String, Integer>();
			// 学历 高中 职高/大专/本科/研究生/博士 
			List<UserCount> eduationalLevels = dao.countEduational(map);
			logger.info("-----------------------"+eduationalLevels.size()+"----------------------------");
			for (UserCount userCount : eduationalLevels) {
				int muns=0;
				if(userCount.getNums()!=null){
					muns=userCount.getNums();
				}
				logger.info("-----------------------"+userCount.toString()+"----------------------------");
				eduatmap.put(Eduational.getName(userCount.getEduationalLevel()),muns);
			}
			mapall.put("学历", eduatmap);
			logger.info("-----------------------统计学历END----------------------------");
		}
		if (map.get("dutyTypeId") != null&&!"".equals(map.get("dutyTypeId"))) {
			map.put("status", 1);//在职
			logger.info("-----------------------统计职等START----------------------------");
			Map<String, Integer> posmap = new HashMap<String, Integer>();
			List<UserCount> dutyTypeIds = dao.countDutyType(map);
			logger.info("-----------------------"+dutyTypeIds.size()+"----------------------------");
			Map<String, Object> pos = new HashMap<String, Object>();
			pos.put("type", 2);
			pos.put("companyId", map.get("companyId"));
			List<PositionTypeInfo> list = positDao
					.getDutyLevelListByParams(pos);
			for (PositionTypeInfo positionTypeInfo : list) {
				for (UserCount uc : dutyTypeIds) {
					int muns=0;
					if(uc.getNums()!=null){
						muns=uc.getNums();
					}
					if (positionTypeInfo.getId() == uc.getDutyTypeId()) {
						logger.info("-----------------------"+uc.toString()+"----------------------------");
						posmap.put(positionTypeInfo.getName(), muns);// 职等
					}else {
						posmap.put(positionTypeInfo.getName(), 0);// 职等
					}
				}
			}
			mapall.put("职等", posmap);
			logger.info("-----------------------统计职等END----------------------------");
		}
		if (map.get("year") != null&&!"".equals(map.get("year"))) {
			map.put("status", 1);//在职
			logger.info("-----------------------统计在职年限START----------------------------");
			Map<String, Integer> jobAagemap = new HashMap<String, Integer>();
			List<UserCount> Years = dao.countYear(map);
			logger.info("-----------------------"+Years.size()+"----------------------------");
			int yearNums2 = 0;
			int yearNums3 = 0;
			int yearNums4 = 0;
			int yearNums5 = 0;
			int yearNums6 = 0;
			for (UserCount userCount : Years) {
				logger.info("-----------------------"+userCount.toString()+"----------------------------");
				if(userCount.getNian()!=null){
					int nums = (int)DateUtil.getDaySub(
							userCount.getNian(),DateUtil.getDay()) / 365;
					if (nums < 1 && nums >= 0) {
						yearNums2+=userCount.getNums();
					} else if (nums >= 1 && nums < 3) {
						yearNums3+=userCount.getNums();
					} else if (nums >= 3 && nums < 5) {
						yearNums4+=userCount.getNums();
					} else if (nums >= 5 && nums < 10) {
						yearNums5+=userCount.getNums();
					} else {
						yearNums6+=userCount.getNums();
					}
				}
			}
			jobAagemap.put("0~1年", yearNums2);
			jobAagemap.put("1~3年", yearNums3);
			jobAagemap.put("3~5年", yearNums4);
			jobAagemap.put("5~10年", yearNums5);
			jobAagemap.put("10年以上", yearNums6);
			mapall.put("在职年限", jobAagemap);
			logger.info("-----------------------统计在职年限END----------------------------");
		}
		if (map.get("age") != null&&!"".equals(map.get("age"))) {
			map.put("status", 1);//在职
			logger.info("-----------------------统计年龄START----------------------------");
			Map<String, Integer> agemap = new HashMap<String, Integer>();
			List<UserCount> ages = dao.countAge(map);
			logger.info("-----------------------"+ages.size()+"----------------------------");
			int ageNums2 = 0;
			int ageNums3 = 0;
			int ageNums4 = 0;
			int ageNums6 = 0;
			int ageNums5 = 0;
			for (UserCount userCount : ages) {
				logger.info("-----------------------"+userCount.toString()+"----------------------------");
				if(userCount.getAge()!=null){
					if (userCount.getAge() >17 && userCount.getAge() <30) {
						ageNums2 += userCount.getNums();
					} else if (userCount.getAge() >29 && userCount.getAge() <40) {
						ageNums3 += userCount.getNums();
					}
					if (userCount.getAge() >39 && userCount.getAge() <50) {
						ageNums4 += userCount.getNums();
					} else if (userCount.getAge() >49 && userCount.getAge() <60) {
						ageNums5 += userCount.getNums();
						agemap.put("50~59岁", ageNums5);
					} else if(userCount.getAge()>59){
						ageNums6 += userCount.getNums();
					}
				}
			}
			agemap.put("18~29岁", ageNums2);
			agemap.put("30~39岁", ageNums3);
			agemap.put("40~49岁", ageNums4);
			agemap.put("60岁以上", ageNums6);
			mapall.put("年龄", agemap);
			logger.info("-----------------------统计年龄END----------------------------");
			}
		return mapall;
	}

	@Override
	public Workbook ExportExceByUsers(Map<String, Object> map) {
		//去除session部分信息
		map.remove("loginUserId");
		map.remove("phone");
//		// 年龄段
//		int startAge = 18;
//		int endAge = 30;
//		if(map.get("age")!=null){
//			switch (map.get("age").toString()) {
//			case "1":
//				break;
//			case "2": 
//				startAge = 30;
//				endAge = 40;
//				break;
//			case "3": 
//				startAge = 40;
//				endAge = 100; 
//				break;
//			}
//			map.remove("age");
//			map.put("startAge", startAge);
//			map.put("endAge", endAge);
//		}
		
		if(map.get("deptId")!=null){
			Map<String, Object> maps= new HashMap<String, Object>();
			maps.put("companyId", map.get("companyId"));
			//该部门及其子部门
			List<Department> showDepts = new ArrayList<Department>();
			//获取所有部门
			List<Department> allDepts= departmentDao.getDepartmentList(maps);
			//获取当前查询的部门列表
			List<Department> curDepts=new ArrayList<Department>();
			Department curDept = new Department();//departmentDao.getDepartmentById(params);
			curDept.setId(Integer.parseInt(map.get("deptId").toString()));
			curDepts.add(curDept);
			List<Department> list=DeptUtil.getAllDepts(curDepts, allDepts, showDepts);
			List<Integer> deptIds=new ArrayList<Integer>();
			for (Department department : list) {
				deptIds.add(department.getId());
			}
			map.put("deptIds", deptIds);
		}
		map.put("status", 1);//在职
		List<User> list = dao.selectByExample(map);
		//获取池
		Jedis redis=jedisPool.getResource();
		for (User user : list) {
//			user.setSexName(user.getSex());
			if(user.getCertificateType()!=null&&!"".equals(user.getCertificateType())){
				user.setCertificateTypeName(Certificate.getName(user.getCertificateType().toString()));
			}
			if(user.getEduational()!=null&&!"".equals(user.getEduational())){
				user.setEduational(Eduational.getName(user.getEduational()));
			}
			if(user.getFormalFace()!=null&&!"".equals(user.getFormalFace())){
				user.setFormal(Formal.getName(user.getFormalFace().toString()));
			}
			if(user.getMaritalStatus()!=null&&!"".equals(user.getMaritalStatus())){
				user.setMarital(Marital.getName(user.getMaritalStatus().toString()));
			}
			if(user.getNation()!=null&&!"".equals(user.getNation())){
				String nation=redis.hget("nationList",user.getNation().toString());
				BasicEnum basicEnum=JSONObject.parseObject(nation, BasicEnum.class);
				user.setNationName(basicEnum.getName());
			}
			user.setStatus(JobStatus.getName(user.getStatus()));
			if(user.getRegularDate()!=null){
				user.setRegular(DateUtil.GLFormDate(user.getRegularDate().toString()));
			}
		}
		Workbook wb = null;
		if ("2".equals(map.get("type"))) {
			 wb = CreateXLSForUser.fastCreateXLS(list);
		}else {
			wb = CreateXLSForUser.createXLS(list);
		}
		return wb;
	}

	@Autowired
	private IDepartmentDao deotDao;

	@Override
	public Workbook exportExceByUsersCount(Map<String, Object> map){
		Map<String, Map<String, Integer>> maps = this.getUserByDepartment(map);
		Map<String, Object> pos = new HashMap<String, Object>();
		pos.put("type", 2);
		pos.put("companyId", map.get("companyId"));
		List<PositionTypeInfo> list = positDao.getDutyLevelListByParams(pos);
		
		Map<String, Object> id = new HashMap<String, Object>();
		id.put("id", Integer.parseInt(map.get("deptId").toString()));
		Department department = deotDao.getDepartmentById(id);
		Workbook wb = CreateXLSForUser.userCount(maps, department.getName(),list.size());
		return wb;
	}
	
	@Autowired 
	private ICompanyPositionInfoDao posititonDao;
	@Autowired
    private JedisPool jedisPool;

	@Override
	public Workbook exportTemplate(Map<String, Object> map) {

		List<String> certificate = Certificate.getAll(); // 证件类型
		List<String> eduational = Eduational.getAll(); // 学历
		List<String> formal = Formal.getAll(); // 政治面貌
		List<String> jobStatus = JobStatus.getAll(); // 在职状态
		List<String> marital = Marital.getAll(); // 婚姻状态
		List<String> postlist = new ArrayList<String>(); // 职位
		String[] sex={"男","女"};
		List<CompanyPositionInfo> post=posititonDao.getPositionInfoListByParams(map);
		for (CompanyPositionInfo companyPositionInfo : post) {
			postlist.add(companyPositionInfo.getName());
		}
		
		//获取所有部门
		List<Department> allDepts= departmentDao.getDepartmentList(map);
		//获取当前查询的部门列表
		List<String> deptName=new ArrayList<String>();
		for (Department department : allDepts) {
			deptName.add(department.getName());
		}	
		//获取池
		Jedis redis=jedisPool.getResource();
		List<String> nations = new ArrayList<String>();
		//从redis获取所以民族
		List<BasicEnum> mapJedis=  this.getNation("nationList");
		for (BasicEnum basicEnum : mapJedis) {
			nations.add(basicEnum.getName());
		}
		String[] params= UserXls.getAll().toArray(new String[UserXls.getAll().size()]);
		Workbook wb =null;
		if("1".equals(map.get("type").toString())){//普通导出
			wb =CreateXLSForUser.exportXls(jobStatus, marital, certificate ,eduational,nations, postlist, formal, sex,params, deptName);
		}else if("2".equals(map.get("type").toString())){//快速维护中的导出
			wb =CreateXLSForUser.fastEmpXLS(postlist, deptName);
		}
		redis.close();
		return wb;
	}

	@Autowired
	private ICompanyPositionInfoDao posDao;
	@SuppressWarnings("unchecked")
	@Override
	public boolean leadTemlate(Map<String, Object> map){
		logger.info("-----------------------导入人员START----------------------------");
		String companyId=map.get("companyId").toString();
		// 获取文件名
		List<InputStream> streamList =  (List<InputStream>) map.get("streamList");
		// 声明Excel数据存放的数组
		ArrayList<ArrayList<Object>> users = null;
		try {
			users = ExcelUtil.readRows(streamList.get(0));
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.READ_FILE_FAIL,"读取文件失败");
		}
		String[] userY =null;
		//获取表头 
		if("1".equals(map.get("type").toString())){//普通导入
			userY=UserXls.getAll().toArray(new String[UserXls.getAll().size()]);
		}else if("2".equals(map.get("type").toString())){//快速维护中的导入
			userY=new String[]{"姓名","电话号码","部门","职位","员工编号"};
		}
		List<String> top = new ArrayList<String>((Arrays.asList(userY)));
		if(!CommonUtil.compareList(top, users.get(0))){
			throw new BizException(BizExceptionMessage.EXCEL_MODEL_ERROR,"表头字段错误");
		}
		users.remove(0);
		Jedis redis=jedisPool.getResource();
		
		Map<String, Object> add = new HashMap<String, Object>();
		add.put("companyId", companyId);  
		//获取所有部门
		List<Department> allDepts= departmentDao.getDepartmentList(add);
		List<CompanyPositionInfo> pos=posDao.getPositionInfoListByParams(add);
		logger.info("-----------------------获取所以部门"+pos.size()+"----------------------------");
		List<User> userList= new ArrayList<User>();
		User emp=null; 
		for (ArrayList<Object> obj : users) {
			emp=new User();
			Object[] user=obj.toArray(new Object[obj.size()]);
			for (int m=0;m< user.length;m++) {
				String val=user[m].toString();
				//字段类型值转换
				if("性别".equals(userY[m])){
					if("男".equals(val)){
						emp.setSex(1);
					}if("女".equals(val)) {
						emp.setSex(2);
					}
					continue;
				}
				if("部门".equals(userY[m])){
					for (Department department : allDepts) {
						if(department.getName().equals(val)){
							//这里忽略了部门上级  
							emp.setDeptId((long)department.getId());
							emp.setDeptName(val);
							break;
						}
					}
				}
				if("职位".equals(userY[m])){
					for (CompanyPositionInfo compos : pos) {
						if(compos.getName().equals(val)){
							emp.setPositionId(compos.getId());
							emp.setPositionName(val);
							break;
						}
					}
				}
				if(val!=null&&val.trim()!=""){
					if("证件类型".equals(userY[m])){
						val=Certificate.getIndex(val);
						emp.setCertificateType(Byte.parseByte(val));
						continue;
					}else if ("民族".equals(userY[m])) {
						val=redis.get("4_"+userY[m]);
						if(val!=null&&val!=""){
							emp.setNation(Integer.parseInt(val));
						}
						continue;
					}else if ("状态".equals(userY[m])) {
						val=JobStatus.getIndex(val);
						emp.setStatus(val);
						continue;
					}else if ("职位名称".equals(userY[m])) {
						CompanyPositionInfo info=new CompanyPositionInfo();
						info.setName(val);
						info.setCompanyId(Long.parseLong(companyId));
						CompanyPositionInfo positionInfo=posititonDao.getPositionInfoByName(info);
						emp.setPositionId(positionInfo.getId());
						emp.setPositionName(val);
						continue;
					}else if ("婚姻状况".equals(userY[m])) {
						val=Marital.getIndex(val);	
						emp.setMaritalStatus(Byte.parseByte(val));
						continue;
					}else if ("政治面貌".equals(userY[m])) {
						val=Formal.getIndex(val);
						emp.setFormalFace(Byte.parseByte(val));
						continue;
					}else if ("拟转正日期".equals(userY[m])) {
						if(val!=null&&!"".equals(val)){
							emp.setRegularDate(DateUtil.fomatDate(DateUtil.GLFormDate(val)));
						}
						continue;
					}else if ("联系方式".equals(userY[m])) {
						if(Tools.checkMobileNumber(val.trim())){
							emp.setPhone(val.trim());
							continue;
						}else {
							throw new BizException(BizExceptionMessage.PHONE_IS_EXIST,"手机格式错误");
						}
					}else if ("邮箱".equals(userY[m])) {
						if(Tools.checkEmail(val.trim())){
							emp.setEmail(val.trim());
							continue;
						}else {
							throw new BizException(BizExceptionMessage.EMIAL_IS_EXIST,"邮箱格式错误");
						}
					}
				}
				try {
					Field field;
					if("1".equals(map.get("type").toString())){
						field = emp.getClass().getDeclaredField(UserXls.getIndex(userY[m]));
						field.setAccessible(true);
						String type = field.getType().toString();//获取字段类型
						if (type.endsWith("String")) {
							field.set(emp,val) ;        //给属性设值  
						}else if(type.endsWith("int") || type.endsWith("Integer")){  
							field.set(emp,Integer.valueOf(val)) ;       
						}else if(type.endsWith("long") || type.endsWith("Long")){
							field.set(emp,Long.valueOf(val)) ;         
						}else if(type.endsWith("byte") || type.endsWith("Byte")){  
				 			field.set(emp,Byte.valueOf(val)) ;         
						}else if(type.endsWith("short") || type.endsWith("Short")){  
							field.set(emp,Short.valueOf(val)) ;         
						}      
					}else if ("2".equals(map.get("type").toString())) {
						String[] userPam= new String[]{"name","phone","deptName","positionName","userNo"};
						field = emp.getClass().getDeclaredField(userPam[m]);
						field.setAccessible(true);
						String type = field.getType().toString();//获取字段类型
						if (type.endsWith("String")) {
							field.set(emp,val) ;        //给属性设值  
						}else if(type.endsWith("int") || type.endsWith("Integer")){  
							field.set(emp,Integer.valueOf(val)) ;       
						}else if(type.endsWith("long") || type.endsWith("Long")){
							field.set(emp,Long.valueOf(val)) ;         
						}else if(type.endsWith("byte") || type.endsWith("Byte")){  
							field.set(emp,Byte.valueOf(val)) ;         
						}else if(type.endsWith("short") || type.endsWith("Short")){  
							field.set(emp,Short.valueOf(val)) ;         
						}      
					}
				} catch (Exception  e) {
					e.printStackTrace();
				}
			}
			emp.setCompanyId(Long.parseLong(companyId));
			userList.add(emp);
		}
		logger.info("-----------------------导入的人员"+userList.size()+"----------------------------");
		redis.close();
		//TODO  后期会改
		addUserForPhp(userList);
		return true;
	}
	
	
	private String addUserForPhp(List<User> userList) {
		Map<String,Object> maps=new HashMap<String, Object>();
		maps.put("companyId", userList.get(0).getCompanyId());
		//获取公司下所有员工号
		List<User> userNo = dao.selectByExample(maps);
		
		for (User user2 : userList) {
			if(user2.getDeptId()==null||"".equals(user2.getDeptId())){
				throw new BizException(BizExceptionMessage.DETY_NAME_EXIST,"没有部门");
			}
			if(user2.getUserNo()==null||"".equals(user2.getUserNo())){
				throw new BizException(BizExceptionMessage.DETY_NAME_EXIST,"没有员工号");
			}
			for (User user : userNo) {
				//对比是否有相同的员工号
				if(user.getUserNo().equals(user2.getUserNo())){
					throw new BizException(BizExceptionMessage.PROP_ERROR, LogInError.getName("-1"));
				}
			}
		}
		String m = null;
		logger.info("-----------------------准备执行PHP添加员工方法---------------------------");
		for (User user : userList) {
			Map<String, Object> paramsMap= new HashMap<String, Object>();
			paramsMap.put("visit_id", user.getCompanyId());
			paramsMap.put("name", user.getName());
			paramsMap.put("phone", user.getPhone());
			//调用php添加登录用户接口 存入登录用户id
			Result logInfo=APIRequestUtil.getResultFromPhpAPI("php.login.url", "php.login.par", paramsMap, "2", "1");
			if(!"0".equals(logInfo.getError_code())){
				String error= "";
				if("1".equals(logInfo.getSub_code().trim().toString())){
					error=LogInError.getName("004");
				}else{
					error=LogInError.getName(logInfo.getError_code());
				}
				throw new BizException(BizExceptionMessage.PROP_ERROR, error);
			}
			if (logInfo.getData()!=null&&!"".equals(logInfo.getData())) {
				@SuppressWarnings("unchecked")
				LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>)logInfo.getData();
				String id=dataMap.get("id").toString();
				String[] ids=id.split("\\.");
				
				//现在JHR和大系统的主键同步，不采用自增长，采用调用PHP后返回的ID值，loginuserid字段先不取消，可能SQL会有影响
				user.setId(Long.parseLong(ids[0]));
				user.setLoginUserId(Long.parseLong(ids[0]));
				if(user.getBirdthDay()!=null){
					long age=DateUtil.getDaySub(DateUtil.GLFormDate(user.getBirdthDay().toString()), DateUtil.getDay());
					user.setAge((int)age/365);
				}
				user.setIsAdmin(Byte.parseByte("1"));
				dao.insert(user);
				
				//变更在编人数
				Map<String, Object> updateMap= new HashMap<String, Object>();
				updateMap.put("deptId", user.getDeptId());
				updateMap.put("curPersonNum", "1");
				departmentDao.updateDepartment(updateMap);
				m="1";
			}else {
				Map<String,Object> phone= new HashMap<String, Object>();
				phone.put("phone", user.getPhone());
				int i=dao.countUser(phone);
				if(i==1){
					//创建邀请
					m="x";
				}
			}
		}
		logger.info("--------------------添加员工END---------------------------");
		return LogInError.getName(m);
	}

	@Override
	public Map<String, Object> countWork(Map<String, Object> map) {
		Map<String, Object> maps1=new HashMap<String, Object>();
		Map<String, Object> maps2=new HashMap<String, Object>();
		maps1.put("companyId", map.get("companyId").toString());
		maps1.put("type", 1);
		maps2.put("companyId", map.get("companyId").toString());
		maps2.put("type", 3);
		Long id = Long.parseLong(map.get("userId").toString());
		Map<String,Object> m = new HashMap<>();
		m.put("id", id) ;
		String userName = dao.seleteById(m).getName();
		//转正
		int work=personnelDao.countWork(maps1);
		//离职
		int leave=personnelDao.countWork(maps2);
		maps1.put("status", 1);
		int nums=dao.countStatusNums(maps1);
		Map<String, Object> maps=new HashMap<String, Object>();
		maps.put("work", work);
		maps.put("leave", leave);
		maps.put("nums", nums);
		maps.put("userName", userName);
		maps.put("dateTime", System.currentTimeMillis());
		return maps;
	}

	@Autowired
	private ILeaveUserDao leaveUserDao;
	@Override
	public int updateAffairs(Map<String, Object> map) {
		PersonnelAffairs affairs =JSONObject.parseObject(map.get("personnelAffairs").toString(), PersonnelAffairs.class);
		affairs.setUserId(Long.parseLong(map.get("empId").toString()));
		affairs.setCompanyId(Long.parseLong(map.get("companyId").toString()));
		personnelDao.insert(affairs);
		User user = new User();
		user.setId(affairs.getUserId());
		user.setDeptId(affairs.getDeptId());
		if(3==affairs.getType()){//离职
			user.setStatus("2");
			user.setCompanyId(0l);//离职后公司值为0
			Map<String,Object> m = new HashMap<>();
			m.put("id", Long.parseLong(map.get("empId").toString())) ;
			User u=dao.seleteById(m);//查询出离职员工添加到离职记录表
			LinkedHashMap<String, Object> leaveMap=ClazzByType.ConvertObjToMap(u);
			//将id转换为值
			leaveMap.put("userId", affairs.getUserId());
			leaveMap.put("deptId", leaveMap.get("deptName"));
			leaveMap.put("positionId", leaveMap.get("positionName"));
			leaveMap.put("dutyId", leaveMap.get("dutyName"));
			leaveMap.put("dutyTypeId", leaveMap.get("dutyTypeName"));
			leaveUserDao.insertLeaveUser(leaveMap);//将离职信息存入离职表
		}else if (1==affairs.getType()) {//转正
			user.setUserDraw((byte)1);
		}
		return dao.updateByExample(user);
	}
	//TODO
	@Override
	public String addUserLogIn(Map<String, Object> map) {
		logger.info("-----------------------新增用户START-----------------------------");
		String n = null;
		Map<String, Object> PHPmap= new HashMap<String, Object>();
		String companyId = map.get("companyId") == null ? null : map.get("companyId").toString();
		String companyName = map.get("companyName") == null ? null : map.get("companyName").toString();
		String phone = map.get("phone") == null ? null : map.get("phone").toString();
		String userName = map.get("userName") == null ? phone : map.get("userName").toString();
		//新增组织
		PHPmap.put("parentDeptId", "0");//默认新建是公司，parentDeptId = 0
		if(StringUtils.isEmpty(companyName) && StringUtils.isEmpty(companyId)
				&& StringUtils.isEmpty(phone)&& StringUtils.isEmpty(userName)){
			throw new BizException(BizExceptionMessage.PROP_ERROR, "参数为空");
		}
		PHPmap.put("name", companyName);
		PHPmap.put("companyId", companyId);
		departmentDao.addDepartment(PHPmap);
		User user = new User();
		
		//现在JHR和大系统的主键同步，不采用自增长，采用调用PHP后返回的ID值，loginuserid字段先不取消，可能SQL会有影响
		user.setId(Long.parseLong(map.get("loginUserId").toString()));
		user.setCompanyId(Long.parseLong(companyId));
		user.setDeptId(Long.parseLong(PHPmap.get("id").toString()));
		user.setPhone(phone);
		user.setName(userName);
		user.setUserNo("1");
		user.setLoginUserId(Long.parseLong(map.get("loginUserId").toString()));
		user.setIsActive((byte)1);
		int i = dao.insert(user);
		user.setJoinDate(new Date());
		
		
		if(i<1){
			throw new BizException(BizExceptionMessage.DB_ERROR, "新增用户失败。");
		}
		n="1";
		Map<String,Object> iphone= new HashMap<String, Object>();
		iphone.put("phone", user.getPhone());
		int m=dao.countUser(iphone);
		if(m==1){
			//创建邀请
			n="x";
		}
		logger.info("-----------------------新增用户END-----------------------------");
		return LogInError.getName(n);
	}
	@Autowired
	private IBasicEnumDao basicEnumDao;
	//获取民族
	@SuppressWarnings("rawtypes")
	private List<BasicEnum> getNation(String keyName){
		Jedis redis=jedisPool.getResource();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", 4);
		List<BasicEnum> list=null;
		//获取对应keyName的value
		Map<String, String> result=redis.hgetAll(keyName);
		
		if(result!=null&&!result.isEmpty()){
			//从redis取出list
			List<BasicEnum> redisList=new ArrayList<BasicEnum>();
	        for(Map.Entry entry: result.entrySet()) { 
	        	BasicEnum be= JSONObject.parseObject(entry.getValue().toString(),BasicEnum.class); 
	        	redisList.add(be);
	        } 
	        redis.close();
	        return redisList;
		}else{
			list=basicEnumDao.getBasicEnumListByParams(params);
			if(list!=null&&!list.isEmpty()){
				for(BasicEnum be:list){
					String id=be.getId().toString();
					//存储列表
					redis.hset(keyName,id,JSONObject.toJSONString(be));
				}
			}
			redis.close();
			return list;			
		}
	}
	
	/**
	 * 登陆时调用--勿删
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@Override
	public User getUser(Map<String, Object> map) throws Exception{
		List<User> list=dao.selectByExample(map);
		User user=null;
		if (list!=null&&!list.isEmpty()) {
			user=list.get(0);
			logger.info("------------登录-getUser"+user+"-------------");
			//更新登录状态 已激活
			if(user.getIsActive()!=null&&user.getIsActive().intValue()==0){
				User record=new User();
				record.setLoginUserId(Long.parseLong(map.get("loginUserId").toString()));
				record.setIsActive(Byte.parseByte("1"));
				dao.updateUserInfoByLoginUserId(record);
			}
			//查询当前登录用户的头像
			map.put("fId", map.get("userId"));
			map.put("businessId", 5);//5表示头像
			List<FileInfo> fileInfoList = commonPageDao.getFileInfoList(map);
			if(fileInfoList!=null&&fileInfoList.size()>0){
				FileInfo fileInfo = fileInfoList.get(0);
				//图片访问路径
				/*String httpFilePath = PropertiesUtil.getPropertyByKey("file_url");
				//用户头像(拼接成全路径)
				String userHeadImg = httpFilePath+fileInfo.getUrl();
				fileInfo.setUrl(userHeadImg);*/
				user.setFileInfo(fileInfo);
			}
			//1.调用ajuc接口获取当前用户的所有角色名称
			Map<String, Object> userRoles = permissionManageService.getUserRolesByAjuc(map);
			if(userRoles!=null&&userRoles.size()>0){
				Object status = userRoles.get("status");
				if("200".equals(status.toString())){
					Object obj = userRoles.get("userRoles");
					String roleNames = "";
					if(!StringUtils.isEmpty(obj)){
						net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(obj);
						int i=0;
						for(Object obj2 : array){
							if(obj2!=null){
								net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(obj2);
								if(!jsonObj.isNullObject()){
									if(i==0||i==array.size()){
										roleNames = roleNames+jsonObj.getString("role_name");
									}else{
										roleNames = roleNames+","+jsonObj.getString("role_name");
									}
									i++;
								}
							}
						}
					}
					user.setUserRoles(roleNames);//目前仅展示角色名称,逗号隔开
				}
			}
			//2.调用ajuc新接口获取公司的管理员
			List<Object> paramArray = new ArrayList<Object>();
			String companyId = map.get("companyId") == null ? null : map.get("companyId").toString();
			String loginUserId = map.get("loginUserId") == null ? null : map.get("loginUserId").toString();
			paramArray.add(companyId);
			try {
				String companyAdmins = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","getAdminUser",paramArray);
				JSONObject json = JSONObject.parseObject(companyAdmins);
				String code = json.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = json.get("data");
					//管理员
					JSONObject administrator = JSONObject.parseObject(obj.toString());
					Object adminId = administrator.get("id");
					if(adminId!=null&&adminId.toString().equals(loginUserId)){
						//说明当前登录用户是管理员身份
						user.setUserNature(1);
					}else{
						//普通用户
						user.setUserNature(2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}
	//TODO
	@Override
	public int updateUserLogIn(Map<String, Object> map) {
		User user = new User();
		user.setLoginUserId(Long.parseLong(map.get("id").toString()));//php用户id
		if(map.get("birdthday")!=null&&!"".equals(map.get("birdthday"))){
			user.setBirdthDay(DateUtil.fomatDate(map.get("birdthday").toString()));//生日
			long age=DateUtil.getDaySub(map.get("birdthday").toString(), DateUtil.getDay());
			user.setAge((int)age/365);
		}
		if(map.get("iphone")!=null&&!"".equals(map.get("iphone"))){
			user.setPhone(map.get("iphone").toString());//手机
		}
		if(map.get("name")!=null&&!"".equals(map.get("name"))){
			user.setName(map.get("name").toString());//名字
		}
		if(map.get("sex")!=null&&!"".equals(map.get("sex"))){
			user.setSex(Integer.parseInt(map.get("sex").toString()));//性别
		}
		if(map.get("email")!=null&&!"".equals(map.get("email"))){
			user.setEmail(map.get("email").toString());//邮箱
		}
		if(map.get("isActive")!=null&&!"".equals(map.get("isActive"))){
			user.setIsActive(Byte.parseByte(map.get("isActive").toString()));//是否激活
		}
		
		
		return dao.updateByPHP(user);
	}

	
	@Override
	public boolean updateUserForAPP(Map<String, Object> params) {
		boolean result = false;
		// 根据companyId和parent_dept_id=0查询oa_department数据
		Department department = null;
		try {
			department = departmentDao.selectDepartmentByCompanyId(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取顶级部门失败");
		}
		
		if (null != department) {
			// 根据login_user_id和phone查询用户是否存在，不存在，新增；存在，更新
			User user = null;
			try {
				user = dao.getUserForAPP(params);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "根据login_user_id和phone查询用户信息失败");
			}
			
			if (null != user) { // 更新
				if (user.getStatus().equals("2")) { // 离职状态，更新
					User userUpdate = new User();
					userUpdate.setCompanyId(department.getCompanyId());
					userUpdate.setDeptId(Long.parseLong(department.getId().toString()));
					userUpdate.setPhone(String.valueOf(params.get("phone")));
					userUpdate.setName(String.valueOf(params.get("userName")));
					userUpdate.setUserNo("0");
					userUpdate.setLoginUserId(Long.parseLong(params.get("loginUserId").toString()));
					userUpdate.setStatus("1");
					dao.updateUserForAPP(userUpdate);
					
					result = true;
				}
			} else { // 新增
				User userAdd = new User();
				userAdd.setCompanyId(department.getCompanyId());
				userAdd.setDeptId(Long.parseLong(department.getId().toString()));
				userAdd.setPhone(String.valueOf(params.get("phone")));
				userAdd.setName(String.valueOf(params.get("userName")));
				userAdd.setUserNo("0");
				userAdd.setLoginUserId(Long.parseLong(params.get("loginUserId").toString()));
				userAdd.setIsActive((byte)1);
				dao.insert(userAdd);
				
				result = true;
			}
		}
		
		return result;
	}
	
	
	@Autowired
	private ILeaveUserDao leaveDao;
	@Autowired
	private IInviteUserDao inviteDao;
	
	
	@Override
	public Map<String, Object> getLeaveUsers(Map<String, Object> map) {
		Map<String, Object> leaveUsers = new HashMap<String, Object>();
		int count = leaveDao.countLeaveUser(map);// 查询总数
		int pageNum = Integer.parseInt(map.get("pageNum").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		Pagination pt = new Pagination(count, pageNum, pageSize);
		// 添加条件
		map.put("pageSize", pageSize);
		map.put("startRow", pt.getStartPos());
		
		List<LeaveUser> leave=leaveDao.selectByLeaveUsers(map);
		leaveUsers.put("leave", leave);
		leaveUsers.put("page", pt);
		return leaveUsers;
	}

	@Override
	public Map<String, Object> getInviteUsers(Map<String, Object> map) {
		Map<String, Object> inviteUsers = new HashMap<String, Object>();
		int count = inviteDao.countInviteUser(map);// 查询总数
		int pageNum = Integer.parseInt(map.get("pageNum").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		Pagination pt = new Pagination(count, pageNum, pageSize);
		// 添加条件
		map.put("pageSize", pageSize);
		map.put("startRow", pt.getStartPos());
		List<InviteUser> invite=inviteDao.selectByInviteUsers(map);
		inviteUsers.put("invite", invite);
		inviteUsers.put("page", pt);
		
		
		return inviteUsers;
	}


	@Override
	public int entrant(Map<String, Object> map) {
		Map<String, Object> leaveMap = new HashMap<String, Object>();
		leaveMap.put("id", map.get("empId"));
		List<InviteUser> inviteUsers=inviteDao.selectByInviteUsers(leaveMap);
		long id=inviteUsers.get(0).getUserId();//获取用户id
		//添加人事事务
		PersonnelAffairs affairs =JSONObject.parseObject(map.get("personnelAffairs").toString(), PersonnelAffairs.class);
		affairs.setUserId(id);
		affairs.setCompanyId(Long.parseLong(map.get("companyId").toString()));
		personnelDao.insert(affairs);
		//修改员工状态
		User user = new User();
		user.setId(id);
		user.setStatus("1");
		user.setCompanyId(Long.parseLong(map.get("companyId").toString()));
		user.setDeptId(affairs.getDeptId());
		return dao.updateByExample(user);
	}

	@Override
	public int isInviteUser(Map<String, Object> map) {
		User user = JSONObject.parseObject(map.get("user")==null?null:map.get("user").toString(), User.class);
		String type=map.get("type")==null?null:map.get("type").toString();
		String companyId=map.get("companyId")==null?null:map.get("companyId").toString();
		if("1".equals(type)){
		Map<String,Object> invite= new HashMap<String, Object>();
		invite.put("deptId", user.getDeptId());
		invite.put("name", user.getName());
		invite.put("phone", user.getPhone());
		user.setCompanyId(Long.parseLong(companyId));
		dao.insert(user);
		invite.put("userId", user.getId());//invite_status
		invite.put("companyId", user.getCompanyId());
		inviteDao.insertInviteUser(invite);	
		}else {
			throw new BizException(BizExceptionMessage.FAILURE, "添加员工无效");
		}
		return 0;
	}

	@Override
	public int isInviteStatus(Map<String, Object> map) {
		String type=map.get("type")==null?null:map.get("type").toString();
		String id=map.get("id")==null?null:map.get("id").toString();
		InviteUser inviteUser = new InviteUser();
		inviteUser.setId(Long.parseLong(id));
		if("1".equals(type)){
			inviteUser.setInviteStatus(Byte.parseByte(type));
			inviteDao.updateInviteStatus(inviteUser);//更新邀请数据
			Map<String, Object> ids = new HashMap<String, Object>();
			ids.put("id", id);
			List<InviteUser> inviteUsers=inviteDao.selectByInviteUsers(ids);//获取邀请表对象
			Map<String,Object> m = new HashMap<>();
			m.put("id", inviteUsers.get(0).getUserId()) ;
			User user=dao.seleteById(m);
			user.setCompanyId(inviteUsers.get(0).getCompanyId());
			user.setDeptId(inviteUsers.get(0).getDeptId());
			dao.updateByExample(user);
		}else if ("2".equals(type)) {
			inviteUser.setInviteStatus(Byte.parseByte(type));
			inviteDao.updateInviteStatus(inviteUser);//更新邀请数据
		}
		return 0;
	}

	@Override
	public Map<String, Object> getInviteById(Map<String, Object> map) {
		Map<String, Object> ids = new HashMap<String, Object>();
		ids.put("id", map.get("id"));
		List<InviteUser> inviteUsers=inviteDao.selectByInviteUsers(ids);
		ids.put("inviteUsers", inviteUsers.get(0));
		return ids;
	}

	@Override
	public Map<String, Object> updateLoginUserPwd(Map<String, Object> params) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String loginUserId = params.get("loginUserId") == null ? null : params.get("loginUserId").toString();
		String oldPwd = params.get("oldPassword") == null ? null : params.get("oldPassword").toString();
		String newPwd = params.get("newPassword") == null ? null : params.get("newPassword").toString();
		if(StringUtils.isEmpty(companyId)){
			resultMap.put("status", HttpStatus.SC_BAD_REQUEST);//400
			resultMap.put("message", "公司id为空");
		}else if(StringUtils.isEmpty(loginUserId)){
			resultMap.put("status", HttpStatus.SC_BAD_REQUEST);
			resultMap.put("message", "用户id为空");
		}else if(StringUtils.isEmpty(oldPwd)){
			resultMap.put("status", HttpStatus.SC_BAD_REQUEST);
			resultMap.put("message", "原始密码为空");
		}else if(StringUtils.isEmpty(newPwd)){
			resultMap.put("status", HttpStatus.SC_BAD_REQUEST);
			resultMap.put("message", "新密码为空");
		}else{
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(Integer.valueOf(companyId));
			paramArray.add(Integer.valueOf(loginUserId));
			paramArray.add(oldPwd);
			paramArray.add(newPwd);
			try {
				String result = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","changePassword",paramArray);
				JSONObject json = JSONObject.parseObject(result);
				String code = json.getString("code");
				if("0".equals(code)){//表示成功
					Object obj = json.get("data");
					resultMap.put("status", HttpStatus.SC_OK);//200
					resultMap.put("message", "修改密码成功！");
				}else{
					throw new BizException("1", "初始密码错误");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof BizException){
					throw e;
				}
				resultMap.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);//500
				resultMap.put("message", "服务器内部错误,请稍后再试");
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String,Object> getNewUsers(Map<String, Object> map) throws Exception {
		map.remove("loginUserId");
		map.remove("phone");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//全部员工数据
		 List<User> list= new ArrayList<User>();
		 //管理员数组
		 List<User> managerList= new ArrayList<User>();
		//员工数组（有部门）
		 List<User> nodeList=new ArrayList<User>();
		//员工数组（未分配部门）
		 List<User> noAssginList= new ArrayList<User>();
		 //公司部门
		 List<Department> departmentList = new ArrayList<Department>();
		 
		String companyId = map.get("companyId") == null ? null : map.get("companyId").toString();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException("400", "companyId为null");
		}else{
			//调用ajuc新接口获取公司的全部管理员
			List<Object> paramArray = new ArrayList<Object>();
			paramArray.add(companyId);//公司id
			try {
				String userMenus = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","getAdminUser",paramArray);
				JSONObject json = JSONObject.parseObject(userMenus);
				String code = json.getString("code");
				if(!"0".equals(code)){//表示失败
					throw new BizException("500", "获取用户菜单远程调用接口失败,请稍后再试");
				}
				String data = json.get("data").toString();
				//得到管理员ID（大系统数据结构）
				JSONObject managerJson = JSONObject.parseObject(data);
				String managerID = managerJson.get("id").toString();
				Map<String,Object> managerMap = new HashMap<>();
				managerMap.put("loginUserIds", new String[]{managerID});
				managerMap.put("companyId",companyId);
				//管理员对象
				List<User> getManager = dao.selectByExample(managerMap);
				User managerUser = getManager.get(0);
				managerUser.setLevel(3);
				managerUser.setIsGroup(1);
				managerUser.setParentDeptId(0l);
				managerList.add(managerUser);
				//获得全部员工数据
				 list  = dao.selectNewByExample(map);
				 departmentList =  departmentDao.getNewDepartmentList(map);
				 //分数组
				 for (User user : list) {
					 user.setIsGroup(1);
					if (user.getDeptId()!=null&&!user.getDeptId().toString().trim().equals("")) {
						nodeList.add(user);
					}else {
						user.setLevel(3);
						user.setParentDeptId(0l);
						noAssginList.add(user);
					}
				 }
				 for (Department department : departmentList) {
					 department.setIsGroup(2);
				 }
				 
				 
				 List<Object> listSort = RecursionSort.listSort(departmentList,nodeList);
				resultMap.put("manager",managerList);
				resultMap.put("nodeList",listSort);
				resultMap.put("noAssginList",noAssginList);
			}catch (Exception e) {
				ExceptionUtil.throwExceptionUtil(e);
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String,Object> freeIsOverTime(Map<String, Object> params) {
		List<User> list=dao.selectByExample(params);
		User user=null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (list!=null&&!list.isEmpty()) {
			user=list.get(0);
			//系统当前时间
			Date currTime = new Date();
			//获取用户的注册时间
			Date createDate = user.getCreateDate();
			//计算出当前用户的免费使用到期时间(15天)
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTimeInMillis(createDate.getTime());
		    calendar.add(Calendar.DATE, 15);	
		    Date after15= new Date(calendar.getTimeInMillis());
		    
		    if(currTime.getTime()>after15.getTime()){
		    	resultMap.put("isOver", "yes");//到期
		    }else{
		    	resultMap.put("isOver", "no");//未到期
		    }
		    DateUtil.formatDate(currTime, "yyyy-MM-dd HH:mm:ss");
		    resultMap.put("currTime", DateUtil.formatDate(currTime, "yyyy-MM-dd HH:mm:ss"));//当前时间
		    resultMap.put("overTime", DateUtil.formatDate(after15, "yyyy-MM-dd HH:mm:ss"));//到期时间
		}
		return resultMap;
	}

	@Override
	public User getUserBycompanyIdAndLoginUserId(Map<String, String> params) {
		String companyId = params.get("companyId");
		String userId = params.get("userId");
		
		if(!StringUtils.isEmpty(userId)){
			return dao.selectBycompanyIdAndLoginUserId(params);
		}
		return null;
	}
	
	
}