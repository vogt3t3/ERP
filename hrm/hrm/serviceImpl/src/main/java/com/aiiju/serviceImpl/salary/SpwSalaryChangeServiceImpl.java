package com.aiiju.serviceImpl.salary;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.salary.SpwSalaryBasic;
import com.aiiju.bean.oa.salary.SpwSalaryBasicLog;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.salary.ISpwSalaryChangeDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.salary.ISpwSalaryChangeService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.Tools;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;

@Service("spwSalaryChangeService")
public class SpwSalaryChangeServiceImpl implements ISpwSalaryChangeService {

	private static Logger log = LoggerFactory.getLogger(SpwSalaryChangeServiceImpl.class);
	// 薪资数据加密字段
//	private static String pswKey = PropertiesUtil.getPropertyByKey("passwordKey");
	private static String pswKey = PropertiesUtil.getProperty(PropertiesUtil.getPropertyByKey("passwordPath"), "passwordKey");
	
	@Autowired
	private ISpwSalaryChangeDao spwSalaryChangeDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public Map<String, Object> getBasicPayLogList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SpwSalaryBasicLog> salaryBasicLogs = new ArrayList<SpwSalaryBasicLog>();
		
		List<String> deptIds = getSubDeptIdList(params, String.valueOf(params.get("deptId")));
		params.put("deptIds", deptIds);
		params.put("pswKey", pswKey);
		params.put("operateUserId", params.get("userId"));
		// 根据companyId获取当前月份调薪操作列表总数
		int totalCount = 0;
		try {
			totalCount = spwSalaryChangeDao.getCurMonthBasicPayLogListCount(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取当前月份调薪操作列表总数失败");
		}
		
		// 此处每页显示10条数据（需求定义的）,页数由前端传
		params.put("totalCount", totalCount);
		if (StringUtils.equals("", String.valueOf(params.get("pageSize")))) {
			params.put("pageSize", "10");
		}
		Pagination page = new Pagination(totalCount, String.valueOf(params.get("pageNum")), String.valueOf(params.get("pageSize")));
		
		if (totalCount > 0) {
			params.put("startPos", page.getStartPos());
			try {
				salaryBasicLogs = spwSalaryChangeDao.getCurMonthBasicPayLogList(params);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "获取当前月份调薪操作列表失败");
			}
		}
		
		resultMap.put("result", salaryBasicLogs);
		resultMap.put("page", page);
		return resultMap;
	}

	/**
	 * 获取当前月份薪资情况--工资管理页面 
	 */
	@Override
	public Map<String, Object> getCurMonthSalaryInfo(Map<String, Object> params) {
		log.info("-----------------------------获取当前月份薪资情况START----------------------------------------");
		String isConfirm = params.get("isConfirm") == null ? null : params.get("isConfirm").toString();//0:全部，1:未定薪 ，2:已定薪
		String userDraw = params.get("userDraw") == null ? null : params.get("userDraw").toString();//0:全部
		String deptId = params.get("deptId") == null ? null : params.get("deptId").toString();
		String pageNumStr = params.get("pageNum").toString();
		String pageSizeStr = params.get("pageSize").toString();
		if(StringUtils.isEmpty(deptId) || StringUtils.isEmpty(isConfirm) || StringUtils.isEmpty(userDraw) || StringUtils.isEmpty(pageNumStr) || StringUtils.isEmpty(pageSizeStr)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SpwSalaryBasicLog> ssblList = new ArrayList<SpwSalaryBasicLog>();
		params.put("pswKey", pswKey);
		List<String> deptIds = getSubDeptIdList(params, String.valueOf(deptId));
		params.put("deptIds", deptIds);
		int pageNum = Integer.parseInt(pageNumStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		int num = spwSalaryChangeDao.getCurMonthSalaryInfoCount(params);
		Pagination pt = new Pagination(num, pageNum, pageSize);
		params.put("pageSize", pageSize);
		params.put("startRow", pt.getStartPos());
		//查询记录
		ssblList = spwSalaryChangeDao.getCurMonthSalaryInfo(params);
		//获取调薪记录数
		int confirmNum = spwSalaryChangeDao.getCurMonthBasicPayLogListCount(params);
		//获取未定薪数
		params.put("isConfirm", 1);
		int unConfirmNum = spwSalaryChangeDao.getCurMonthSalaryInfoCount(params);
		String curMonth = DateUtil.getCurMonth("M");
		String startDay = curMonth + ".1";
		String endDay = DateUtil.getCurDate("M.d");
		resultMap.put("result", ssblList);
		resultMap.put("startDay", startDay);//页面展示--开始日期
		resultMap.put("endDay", endDay);//页面展示--结束日期
		resultMap.put("confirmNum", confirmNum);//调薪
		resultMap.put("unConfirmNum", unConfirmNum);//未定薪
		resultMap.put("page", pt);
		log.info("-----------------------------获取当前月份薪资情况END----------------------------------------");
		return resultMap;
	}

	/**
	 * 调整薪资（参数isInit : 1 设置初始薪资--必须传basicPay，0调薪）   
	 */
	@Override
	@Transactional
	public Map<String, Object> changeSalary(Map<String, Object> params) {
		log.info("-----------------------------调整薪资START----------------------------------------");
		params.put("pswKey", pswKey);
		String isInit = params.get("isInit") == null ? null : params.get("isInit").toString();
		String basicPay = params.get("basicPay") == null ? null : params.get("basicPay").toString();
		String setUserId = params.get("setUserId") == null ? null : params.get("setUserId").toString();
		String beforeBasicPay = params.get("beforeBasicPay") == null ? null : params.get("beforeBasicPay").toString();
		String afterBasicPay = params.get("afterBasicPay") == null ? null : params.get("afterBasicPay").toString();
		String adjustRange = params.get("adjustRange") == null ? null : params.get("adjustRange").toString();
		String forceTime = params.get("forceTime") == null ? null : params.get("forceTime").toString();
		if("1".equals(isInit)){//设置初始薪资
			if(StringUtils.isEmpty(basicPay) || StringUtils.isEmpty(setUserId)){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR,"设置初始薪资时,员工ID与初始薪资不能为空");
			}else if(!Tools.regularCheck("^([1-9]{1}[0-9]*|0{1})(|\\.{1}[0-9]{1,2})$", basicPay)){
				
				throw new BizException("400","请输入正确的薪资");
			}
			else{
				int cnt = spwSalaryChangeDao.checkIsInit(params);
				//先判断是否已经设置过初始薪资，防止页面长久停留，他人修改，导致数据并发。
				if(cnt == 0){
					spwSalaryChangeDao.addSalary(params);
					params.put("afterBasicPay", basicPay);
					params.put("forceTime", DateUtil.getDay());
				}else{
					throw new BizException(BizExceptionMessage.LOGIC_ERROR,"该用户已设置初始薪资");
				}
			}
		}else if("0".equals(isInit)){//调薪
			if(StringUtils.isEmpty(setUserId) || StringUtils.isEmpty(beforeBasicPay) || StringUtils.isEmpty(afterBasicPay) 
					|| StringUtils.isEmpty(adjustRange) || StringUtils.isEmpty(forceTime)){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR,"调薪操作时,参数有误");
			}else if(!Tools.regularCheck("^([1-9]{1}[0-9]*|0{1})(|\\.{1}[0-9]{1,2})$", beforeBasicPay)
					||!Tools.regularCheck("^([1-9]{1}[0-9]*|0{1})(|\\.{1}[0-9]{1,2})$", afterBasicPay)
					||!Tools.regularCheck("^([1-9]{1}[0-9]*|0{1})(|\\.{1}[0-9]{1,10})$", afterBasicPay)){
				
				throw new BizException("400","请输入正确的薪资");
			}
			
		}else{
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"isInit不能为空");
		}
		String operateUserId = params.get("userId") == null ? null : params.get("userId").toString();
		params.put("operateUserId", operateUserId);
		spwSalaryChangeDao.addSalaryLog(params);
		log.info("-----------------------------调整薪资END----------------------------------------");
		return null;
	}

	@Override
	public Map<String, Object> getSalaryInfoCount(Map<String, Object> params) {
		// 结果集
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 定义存储统计数据的Map（调薪操作列表记录除外）
		Map<String, Object> increaseCount = new HashMap<String, Object>();
		// 获取部门及其子部门
		List<String> deptIds = getSubDeptIdList(params, String.valueOf(params.get("deptId")));
		// 获取部门人数(请求参数：companyId,deptIds)
		params.put("deptIds", deptIds);
		int totalPeopleCount = 0;
		try {
			totalPeopleCount = userDao.getUserCountByDeptId(params);
		} catch (Exception e) {
			log.info("获取部门人数失败异常信息：" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取部门人数失败");
		}

		params.put("pswKey", pswKey);
		params.put("isInit", 0);
		params.put("operateUserId", params.get("userId"));
		// 根据companyId获取当前月份调薪操作列表总数
		int totalCount = 0;
		try {
			totalCount = spwSalaryChangeDao.getSalaryBasicLogListCount(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取调薪统计列表总数失败");
		}
		
		// 根据companyId,deptId,生效时间查询统计数据
		List<SpwSalaryBasicLog> salaryBasicLogs = null;
		if (totalCount > 0) {
			try {
				salaryBasicLogs = spwSalaryChangeDao.getSalaryBasicLogList(params);
			} catch (Exception e) {
				log.info("获取调薪操作记录失败异常信息" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "获取调薪操作记录失败");
			}
		}

		// 对查询出来的list做统计工作
		if (null != salaryBasicLogs && !salaryBasicLogs.isEmpty()) {
			// 人数统计
			long peopleCount = salaryBasicLogs.stream().map(SpwSalaryBasicLog::getUserId).distinct().count();
			// 调薪均幅
			double salaryAdjustment = salaryBasicLogs.stream().mapToDouble(SpwSalaryBasicLog::getAdjustRange).average()
					.getAsDouble();
			// 调薪比例
			double salaryProportion = 0;
			if (0 != totalPeopleCount) {
				salaryProportion = peopleCount / (double) totalPeopleCount;
			}
			
			BigDecimal salaryAdjustmentBD = new BigDecimal(salaryAdjustment);
			double salaryAdjustmentResult = salaryAdjustmentBD.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal salaryProportionBD = new BigDecimal(salaryProportion);
			double salaryProportionResult = salaryProportionBD.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			increaseCount.put("peopleCount", peopleCount);
			increaseCount.put("salaryAdjustment", salaryAdjustmentResult);
			increaseCount.put("salaryProportion", salaryProportionResult);
			// 调薪幅度分类
			increaseCount.put("<0", getSalaryIncrease(0d, null, salaryBasicLogs));
			increaseCount.put("0 ~ 5%", getSalaryIncrease(0d, 0.05, salaryBasicLogs));
			increaseCount.put("5% ~ 10%", getSalaryIncrease(0.05, 0.1, salaryBasicLogs));
			increaseCount.put("10% ~ 20%", getSalaryIncrease(0.1, 0.2, salaryBasicLogs));
			increaseCount.put("20% ~ 30%", getSalaryIncrease(0.2, 0.3, salaryBasicLogs));
			increaseCount.put("30%以上", getSalaryIncrease(null, 0.3, salaryBasicLogs));
		}

//		resultMap.put("result", salaryBasicLogs);
		resultMap.put("result", increaseCount);
//		resultMap.put("page", page);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getSalaryInfoCountDetail(Map<String, Object> params) {
		// 结果集
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取部门及其子部门
		List<String> deptIds = getSubDeptIdList(params, String.valueOf(params.get("deptId")));
		params.put("deptIds", deptIds);

		// 根据companyId获取当前月份调薪操作列表总数
		params.put("pswKey", pswKey);
		params.put("isInit", 0);
		params.put("operateUserId", params.get("userId"));
		int totalCount = 0;
		try {
			totalCount = spwSalaryChangeDao.getSalaryBasicLogListCount(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取调薪统计列表总数失败");
		}
		
		// 此处每页显示5条数据（需求定义的）,页数由前端传
		params.put("totalCount", totalCount);
		if (StringUtils.equals("", String.valueOf(params.get("pageSize")))) {
			params.put("pageSize", "5");
		}
		Pagination page = new Pagination(totalCount, String.valueOf(params.get("pageNum")), String.valueOf(params.get("pageSize")));
		// 根据companyId,deptId,生效时间查询统计数据
		List<SpwSalaryBasicLog> salaryBasicLogs = null;
		if (totalCount > 0) {
			params.put("startPos", page.getStartPos());
			try {
				salaryBasicLogs = spwSalaryChangeDao.getSalaryBasicLogList(params);
			} catch (Exception e) {
				log.info("获取调薪操作记录失败异常信息" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "获取调薪操作记录失败");
			}
		}
		
		resultMap.put("result", salaryBasicLogs);
		resultMap.put("page", page);
		return resultMap;
	}
	
	/**
	 * 导出调薪统计报表
	 * @param params
	 * @return
	 */
	@Override
	public HSSFWorkbook exportSalaryCountExcel(Map<String, Object> params) {
		params.put("pswKey", pswKey);
		params.put("isInit", 0);
		params.put("operateUserId", params.get("userId"));
		List<String> deptIds = getSubDeptIdList(params, String.valueOf(params.get("deptId")));
		params.put("deptIds", deptIds);
		// 根据companyId,deptId,生效时间查询统计数据
		List<SpwSalaryBasicLog> dataList = null;
		try {
			dataList = spwSalaryChangeDao.getSalaryBasicLogList(params);
		} catch (Exception e) {
			log.info("获取调薪操作记录失败异常信息" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取调薪操作记录失败");
		}
		
		// 对数据进行处理
		DecimalFormat df = new DecimalFormat("0.00");
		for (SpwSalaryBasicLog salaryBasicLog: dataList) {
			salaryBasicLog.setBeforeBasicPayStr(df.format(salaryBasicLog.getBeforeBasicPay()));
			salaryBasicLog.setAfterBasicPayStr(df.format(salaryBasicLog.getAfterBasicPay()));
			double adjustRange = salaryBasicLog.getAdjustRange();
			salaryBasicLog.setAdjustRangeStr(df.format(adjustRange * 100) + "%");
		}
		
		// 组装Excel表头
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("userName", "姓名");
		headers.put("empNo", "工号");
		headers.put("deptName", "部门");
		headers.put("joinDate", "入职日期");
		headers.put("beforeBasicPayStr", "调整前薪资");
		headers.put("afterBasicPayStr", "调整后薪资");
		headers.put("adjustRangeStr", "涨薪幅度");
		headers.put("forceTime", "生效时间");
		headers.put("operateUserName", "操作人");
		headers.put("createTime", "调整时间");
		// 生成Excel文件
		HSSFWorkbook book = ExcelUtil.exportExcelWithHeader("调薪统计列表", headers, dataList, null, null, params);
		return book;
	}
	
	/**
	 * 获取该组织的所有下级部门ID,包含自己
	 * @param params
	 * @param deptId
	 * @return
	 */
	private List<String> getSubDeptIdList(Map<String, Object> params, String deptId) {
		List<String> subDeptIdList = new ArrayList<String>();
		// 查询的部门
		List<Department> queryDepts = new ArrayList<Department>();
		Department queryDept = new Department();
		queryDept.setId(Integer.valueOf(params.get("deptId").toString()));
		queryDepts.add(queryDept);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("companyId", params.get("companyId").toString());
		// 所有部门
		List<Department> allDepts = departmentDao.getDepartmentList(m);
		List<Department> showDepts = new ArrayList<Department>();
		DeptUtil.getAllDepts(queryDepts, allDepts, showDepts);
		for (Department dept : showDepts) {
			subDeptIdList.add(dept.getId().toString());
		}
		return subDeptIdList;
	}
	
	/**
	 * 获取调整增幅的数据列表
	 * @param min 调整增幅范围的下限值
	 * @param max 调整增幅范围的上限值
	 * @param list 调薪操作记录列表
	 * @return
	 */
	private long getSalaryIncrease(Double min, Double max, List<SpwSalaryBasicLog> list) {
		// 定义处于某范围内的记录总数
		long recordCount = 0;
		// 增幅比例
//		double increaseCount = 0;
		if (null == min && null == max) {
			throw new BizException(BizExceptionMessage.PARAM_ERROR, "最大值和最小值不能同时为null");
		} else {
			if (null == max) {
				recordCount = list.stream().filter(spwSalaryBasicLog -> (spwSalaryBasicLog.getAdjustRange() <= min)).count();
			} else if (null == min) {
				recordCount = list.stream().filter(spwSalaryBasicLog -> (spwSalaryBasicLog.getAdjustRange() > max)).count();
			} else {
				recordCount = list.stream()
						.filter(spwSalaryBasicLog -> (spwSalaryBasicLog.getAdjustRange() > min
								&& spwSalaryBasicLog.getAdjustRange() <= max)).count();
			}
		}
		
//		increaseCount = recordCount / list.size();

		return recordCount;
	}
	
	/**
	 * 查询调薪记录  - 调薪页面时右侧的调薪记录   
	 * params: 参数setUserId-查询对象ID
	 */
	@Override
	public Map<String,Object> getAdjustSalaryLog(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SpwSalaryBasicLog> ssblList = new ArrayList<SpwSalaryBasicLog>();
		params.put("pswKey", pswKey);
		String setUserId = params.get("setUserId") == null ? null : params.get("setUserId").toString();
		if(StringUtils.isEmpty(setUserId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"查询员工ID不能为空");
		}else{
			ssblList = spwSalaryChangeDao.getAdjustSalaryLog(params);
		}
		for(SpwSalaryBasicLog ssb : ssblList){
			Long time = DateUtil.getTimeMillis(ssb.getForceTime(),"yyyy-MM-dd") - DateUtil.getTimeMillis(DateUtil.getCurDay(),"yyyy-MM-dd");
			if(time > 0){
				ssb.setCanDel(true);
			}else{
				ssb.setCanDel(false);
			}
		}
		resultMap.put("result", ssblList);
		resultMap.put("curDate", DateUtil.getCurDay());
		return resultMap;
	}

	/**
	 * 定时器刷新工资。
	 */
	@Override
	@Transactional
	public Map<String, Object> updateSalary() {
		Map<String, Object> params = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SpwSalaryBasic> ssbList = new ArrayList<SpwSalaryBasic>();
		params.put("pswKey", pswKey);
		//获取生效时间为近三天的“最新”薪资调整数据   用作定时器刷新时使用
		ssbList = spwSalaryChangeDao.getRecentSalaryInfo(params);
		int allCount = 0;
		for(SpwSalaryBasic ssb : ssbList){
			params.put("basicPay", ssb.getBasicPay());
			params.put("userId", ssb.getUserId());
			params.put("companyId", ssb.getCompanyId());
			int num = spwSalaryChangeDao.updateSalaryInfo(params);
			allCount += num;
		}
		resultMap.put("allCount", allCount);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteSalaryLog(Map<String,Object> params) {
		String id = params.get("id") == null ? null : params.get("id").toString();
		if(StringUtils.isEmpty(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		int delNum = spwSalaryChangeDao.deleteSalaryLog(paramMap);
		resultMap.put("delNum", delNum);
		return resultMap;
	}
	
	public static void main(String[] args) {
		boolean regularCheck = Tools.regularCheck("^[1-9]{1}[0-9]*\\.?[0-9]{0,2}$", "1");
		System.out.println(regularCheck);
	}
}
