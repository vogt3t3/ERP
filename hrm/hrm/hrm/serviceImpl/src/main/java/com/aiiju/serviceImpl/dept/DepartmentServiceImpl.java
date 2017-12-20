package com.aiiju.serviceImpl.dept;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.dept.DepartmentTreePojo;
import com.aiiju.bean.oa.position.CompanyPositionInfo;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.position.ICompanyPositionInfoDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.dept.IDepartmentService;
import com.aiiju.serviceImpl.CommonPageService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.common.RecursionSort;
import com.aiiju.util.enums.DeptModelEnum;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;
 
/** 
@Author  太一 
@Date 创建时间：2016年10月18日 下午4:18:48 
*/
@Service("departmentService")
public class DepartmentServiceImpl extends CommonPageService implements IDepartmentService{
	
	private static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	private IDepartmentDao departmentDao;
	@Autowired
	private ICompanyPositionInfoDao companyPositionInfoDao;
	@Autowired
	private IUserDao userDao;
	
	@Override
	public int addDepartment(Map<String, Object> params) {
		String name = params.get("name") == null ? null : params.get("name").toString().trim();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.PROP_ERROR,"组织名称或公司ID不能为空");
		}else{
			int cnt = departmentDao.getCntByDeptName(params);
			if(cnt>0){
				throw new BizException(BizExceptionMessage.LOGIC_ERROR,"该部门名称已经存在");
			}else{
				//获得父部门信息,用于修改时，改变自己的层级
				Map<String,Object> getMap = new HashMap<>();
				getMap.put("id", params.get("parentDeptId").toString());
				Department dept = departmentDao.getDepartmentById(getMap);
				params.put("level",dept.getLevel()+1);
				departmentDao.addDepartment(params);
			}
		}
		return 1;
	}
	
	@Override
	public void initDepartmentForHTTP(Map<String, Object> params) {
		Department parentDept = departmentDao.selectDepartmentByCompanyId(params);
		List<Department> initDeptList  = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			String [] deptNames = {"销售部","运营部","客服部","财务部"};
			Department initSubDept = initSubDept(Long.valueOf(params.get("companyId").toString()), parentDept.getId(), deptNames[i]);
			initDeptList.add(initSubDept);
		}
		params.put("deptAddList", initDeptList);
		departmentDao.batchAddDeptInfo(params);
		
	}

	@Override
	public int updateDepartment(Map<String, Object> params) {
		int reuslt = 0;
		String deptId = params.get("deptId") == null ? null : params.get("deptId").toString();
		String parentDeptId = params.get("parentDeptId") == null ? null : params.get("parentDeptId").toString();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String level = params.get("level") == null ? null : params.get("level").toString();
		if(StringUtils.isEmpty(deptId)||StringUtils.isEmpty(companyId)){
			throw new BizException(BizExceptionMessage.PROP_ERROR,"没有指定修改哪个部门ID");
		} 
		//获得父部门信息,用于修改时，改变自己的层级
		Map<String,Object> getMap = new HashMap<>();
		getMap.put("id", params.get("parentDeptId").toString());
		Department dept = departmentDao.getDepartmentById(getMap);
		params.put("level",dept.getLevel()+1);
		
		List<String> subDeptIdList = this.getSubDeptIdList(params, deptId);
		if(subDeptIdList.contains(parentDeptId)){
			throw new BizException(BizExceptionMessage.PROP_ERROR,"上级组织不能选择自己及自己的下级!");
		}else{
			reuslt = departmentDao.updateDepartment(params);
		}
		return reuslt;
	}
	
	@Override
	public void updateDepartmentForHTTP(Map<String, Object> params) {
		Department parentDept = departmentDao.selectDepartmentByCompanyId(params);
		try {
			
			params.put("deptId", parentDept.getId());
			params.put("name", params.get("companyName"));
			departmentDao.updateDepartment(params);
		} catch (Exception e) {
			throw new BizException("1","修改失败");
		}
		
	}

	@Override
	public int delDepartment(Map<String, Object> params) {
		String ids = params.get("deptIds") == null ? null : params.get("deptIds").toString();
		String[] idsArray = ids.split(",");
		int reuslt = 0;
		if(idsArray.length > 0){
			for(int i=0;i<idsArray.length;i++){
				//1、有子部门的不能删
				int subdeptCount=departmentDao.getSubdeptCount(idsArray[i]);
				//2、部门下有员工不能删（调用员工接口）
				int empCount = departmentDao.getEmpCount(idsArray[i]);
				if(subdeptCount>0){
					throw new BizException(BizExceptionMessage.LOGIC_ERROR,"存在子部门，请先删除其下的子部门");
				}else if(empCount>0){
					throw new BizException(BizExceptionMessage.LOGIC_ERROR,"存在员工，请先删除其下的员工");
				}
			}
			params.put("idsArray", idsArray);
			reuslt = departmentDao.delDepartmentByIds(params);
		}
		return reuslt;
	}
	@Override
	public List<DepartmentTreePojo> getDepartmentTreeByCompanyId(Map<String,Object> params){
		List<DepartmentTreePojo> list=departmentDao.getDepartmentTreeCompanyId(params);
		List<DepartmentTreePojo> returnList = DeptUtil.getChildDepartments(list,null);
		return returnList;
	}
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importDepartment(Map<String, Object> params) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取文件名(完整路径名)
		InputStream fileName = null;
		List<InputStream> fileInfoList = null;
		if (params.get("streamList") instanceof List) {
			fileInfoList = (List<InputStream>) params.get("streamList");
		}
		if (null != fileInfoList && !fileInfoList.isEmpty()) {
			fileName = fileInfoList.get(0);
		}
		// 声明Excel数据存放的数组
		ArrayList<ArrayList<Object>> deptDatas = null;
		try {
			deptDatas = ExcelUtil.readRows(fileName);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.READ_FILE_FAIL);
		}
		if (null != deptDatas && deptDatas.size() > 0) { // 存在数据
			if (!CommonUtil.compareList(DeptModelEnum.getModelHeader(), deptDatas.get(0))) { // 判断表头是否完整且保持原有模版的顺序
				throw new BizException(BizExceptionMessage.EXCEL_MODEL_ERROR);
			} else{
				// 判断关键字段是否为空
				List<Object> resultList = deptDatas.stream()
						.filter(deptData -> StringUtils.isEmpty(String.valueOf(deptData.get(0))))
						.collect(Collectors.toList());
				if (null != resultList && resultList.size() > 0) { // 有数据，说明关键字段存在非空
					throw new BizException(BizExceptionMessage.EMPTY_ERROR);
				} else { // 无数据， 关键字段不存在非空
					// 去除表头
					deptDatas.remove(0);
					if (null != deptDatas && !deptDatas.isEmpty()) { // 去除表头后存在数据
						try {
							// 注：以下是数据库不建立索引的情况下导入excel数据,新增数据时分成两部分，一个是待新增列表，一个是待更新列表
							// 查询数据库获取组织信息（根据公司ID）
							List<Department> departments = departmentDao.getDepartmentList(params);
							// 取出组织名称组成list
							List<String> deptNames = new ArrayList<String>();
							departments.forEach(dept -> deptNames.add(dept.getName()));
							// 将excel的数据都转换成实体类List（此处迫不及待转成实体类List，谁让后面要把需要新增和更新的数据分开呢）
							List<Department> allDepartments = new ArrayList<Department>();
							for (ArrayList<Object> deptData: deptDatas) {
								Department department = new Department();
								department.setCompanyId(Long.valueOf(params.get("companyId").toString()));
								department.setName(String.valueOf(deptData.get(0)));
								if (deptData.get(0) instanceof Double) {
									Double name = (Double) deptData.get(0);
									department.setName(String.valueOf(name.intValue()));
								}
								department.setParentDeptName(String.valueOf(deptData.get(1)));
								if (deptData.get(1) instanceof Double) {
									Double parentDeptName = (Double) deptData.get(1);
									department.setParentDeptName(String.valueOf(parentDeptName.intValue()));
								}
								department.setShortName(String.valueOf(deptData.get(2)));
								if (deptData.get(2) instanceof Double) {
									Double shortName = (Double) deptData.get(2);
									department.setShortName(String.valueOf(shortName.intValue()));
								}
								department.setDeptNo(String.valueOf(deptData.get(3)));
								if (deptData.get(3) instanceof Double) {
									Double deptNo = (Double) deptData.get(3);
									department.setDeptNo(String.valueOf(deptNo.intValue()));
								}
								/*if (null != deptData.get(3)) {
									
									Double deptNo = (Double) deptData.get(3);
									department.setDeptNo(String.valueOf(deptNo.intValue()));
								}*/
								department.setRemark(String.valueOf(deptData.get(4)));
								if (deptData.get(4) instanceof Double) {
									Double remark = (Double) deptData.get(4);
									department.setRemark(String.valueOf(remark.intValue()));
								}
								allDepartments.add(department);
							}
							// 根据组织名称去重，只保留第一条(重写了equals方法和hashCode方法)
							Set<Department> set = new HashSet<>();
							List<Department> tempList = new ArrayList<>();
							for (Iterator<Department> ite = allDepartments.iterator(); ite.hasNext();) {
								Department temp = ite.next();
								if (set.add(temp)) {
									tempList.add(temp);
								}
							}
							allDepartments.clear();
							allDepartments.addAll(tempList);
							// 筛选出allDepartments中待新增的数据
							List<Department> addDeptList = new ArrayList<Department>();
							addDeptList = allDepartments.stream().filter(dept -> !deptNames.contains(dept.getName())).collect(Collectors.toList());
							// 这部分注释代码不要删，可以做后面的出错信息数据统计用，错误信息是"部门名称已存在"
							/*List<Department> updateDeptList = new ArrayList<Department>();
							updateDeptList = allDepartments.stream().filter(dept -> deptNames.contains(dept.getName())).collect(Collectors.toList());*/
							
							// 声明失败的行号
							StringBuilder failedLineNums = new StringBuilder();
							// 筛选数据-名称重复的数据的行号(此处用Stream暂时没实现，待研究)
							for (int i = 0; i < deptDatas.size(); i++) {
								if (deptNames.contains(deptDatas.get(i).get(0))) {
									failedLineNums.append(i + 1).append(",");
								}
							}
							resultMap.put("failedLineNums", failedLineNums);
							
							// 新增
							params.put("deptAddList", addDeptList);
							departmentDao.batchAddDeptInfo(params);
//							departmentDao.batchUpdateDeptInfo(updateDeptList);
							// 查询新增/更新后的组织机构数据(根据公司ID)
							List<Department> lastDepartments = departmentDao.getDepartmentList(params);
							Map<String, Integer> lastDeptNames = new HashMap<String, Integer>();
							for (Department depts : lastDepartments) {
								lastDeptNames.put(depts.getName(), depts.getId());
								for(Department dept:addDeptList){
									if(depts.getName().equals(dept.getParentDeptName())){
										dept.setLevel((byte)(depts.getLevel()+1));
									}
								}
							}
							//用的是JAVA8.0的功能一直报错，换成list迭代存到map中
//							lastDeptNames = lastDepartments.stream().collect(Collectors.toMap(Department::getName,Department::getId));
							logger.debug("==================查询组织数据：" + lastDeptNames.toString() + "==================");
							// 筛选出上级组织名称存在于lastDeptNames中的数据
							List<Department> updateParentIdList = new ArrayList<Department>();
							for (Department dept: addDeptList) {
								Department updateParentId = new Department();
								if (lastDeptNames.containsKey(dept.getParentDeptName())) { // 上级组织存在于数据库中
									updateParentId.setCompanyId(Long.valueOf(params.get("companyId").toString()));
									updateParentId.setName(dept.getName());
									updateParentId.setParentDeptId(lastDeptNames.get(dept.getParentDeptName()));
									updateParentId.setLevel(dept.getLevel());
								} else {
									updateParentId = null;
								}
								if (null != updateParentId) {
									updateParentIdList.add(updateParentId);
								}
							}
							
							logger.info("==================组装组织数据列表开始:" + updateParentIdList.toString() + "==================");
							// 对筛选出的数据做批量更新操作，更新父部门ID
							if (null != updateParentIdList && !updateParentIdList.isEmpty()) {
								params.put("deptParentIdUpdateList", addDeptList);
								departmentDao.batchUpdateDeptParentId(updateParentIdList);
							}
							logger.info("==================组装组织数据列表结束==================");
						} catch (Exception e) {
							logger.info("异常信息" + e.getMessage());
							throw new BizException(BizExceptionMessage.DB_ERROR);
						}
					}
				}
			}
		}else {
			throw new BizException(BizExceptionMessage.EXCEL_NOT_EXIST);
		}
		return resultMap;
	}

	@Override
	public HSSFWorkbook exportDepartment(Map<String, Object> params) {
		List<Department> list = null;
		try {
			list = departmentDao.getDepartmentList(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("name", "部门名称");
		headers.put("shortName", "部门简称");
		headers.put("parentDeptName", "上级部门");
		headers.put("remark", "描述说明");
		HSSFWorkbook book = ExcelUtil.exportExcel("组织信息列表", headers, list, null, null);
		return book;
	}

	@Override
	public Map<String, Object> getDepartmentList(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		String deptId = params.get("deptId") == null ? null : params.get("deptId").toString();
		Integer pageNum = null==params.get("pageNum") ? null
				: Integer.parseInt(params.get("pageNum").toString());
		Integer pageSize= params.get("pageSize") == null ? null
				: Integer.parseInt(params.get("pageSize").toString());
		// 分页查询
		Pagination page = null;
		
		int totalNum = 0;
		if(!StringUtils.isEmpty(deptId)){
			List<String> subDeptList = this.getSubDeptIdList(params, deptId);
			params.put("list", subDeptList);
		}
		//APP 不传分页参数时，给所有信息,分页对象为null
		if(params.get("from")==null||!params.get("from").toString().equals("APP")
				||(params.get("from").toString().equals("APP")
						&&!StringUtils.isEmpty(params.get("pageNum")==null?null:params.get("pageNum").toString())
						&&!StringUtils.isEmpty(params.get("pageSize")==null?null:params.get("pageSize").toString()))){
		
			totalNum = departmentDao.getDepartmentListCount(params);
			 page = new Pagination(totalNum, pageNum, pageSize);
			params.put("startPos", String.valueOf(page.getStartPos()));
			params.put("pageSize", String.valueOf(page.getPageSize()));
		}
		
		List<Department> list = departmentDao.getDepartmentList(params);
		if(list!=null&&list.size()>0){
			RecursionSort.deptPersonNum(list, null,null);
		}
		map.put("result", list);
		map.put("page", page);
		return map;
	}


	@Override
	public List<DepartmentTreePojo> getDepartmentFramework(Map<String, Object> params) {
		List<DepartmentTreePojo> list = new ArrayList<DepartmentTreePojo>();
		//根据params传参，判断是查公司所有部门架构图 还是一个部门的架构图
//		if(null!=params.get("deptId")){
//			// 先查询该部门的所有子部门
//			List<Department> allDepts = getAllDeptsEnter(params);
//			params.put("allDepts", allDepts);
//		}
		list = departmentDao.getDepartmentFramework(params);
		return DeptUtil.getChildDepartments(list, null);
	}
	
	
	
	@Override
	public HSSFWorkbook downDeptModelExcel(Map<String, Object> params) {

		// 声明excel表格
		HSSFWorkbook book = new HSSFWorkbook();
		// 声明表头
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		// 声明下拉列表Map（key是需要设置下拉列表项的单元格列序号，value是存放列表数据的String[]）
		Map<String, Object> dropDownMap = new HashMap<String, Object>();
		// 导出模版类型1-组织信息；2-法人信息
		String modelType = (String) params.get("modelType");
		// 模板是否导出已有数据
		String isExportData = (String) params.get("isExportData");
		//组织信息
		if(StringUtils.equals("1", modelType)){
			headers.put("name", "部门名称");
			headers.put("parentDeptName", "上级部门");
			headers.put("shortName", "部门简称");
			headers.put("deptNo", "部门序号");
			headers.put("remark", "描述说明");
			// 表头字体颜色需要改变的单元格序号数组
			int[] changeTitleFont = { 0};
			
			List<Department> dataList = new ArrayList<Department>();
			if(StringUtils.equals("1", isExportData)){//导出已有数据
				dataList=departmentDao.getDepartmentList(params);
			}
			// 根据公司ID查询该公司的所有部门
            List<Department> deptList = null;
            try {
                deptList = departmentDao.getDepartmentList(params);
            } catch (Exception e) {
                throw new BizException(BizExceptionMessage.DB_ERROR, "获取组织列表失败");
            }
            if (null != deptList && !deptList.isEmpty()) {
                List<String> deptNames = new ArrayList<String>();
                deptList.forEach(deptInfo -> deptNames.add(deptInfo.getName()));
                // 组装下拉列表
                String[] deptNamesStr = new String[deptNames.size()];
                dropDownMap.put("1", deptNames.toArray(deptNamesStr));
            }
			book = ExcelUtil.exportExcel("部门信息", headers, dataList, changeTitleFont, dropDownMap);
		} // 法人组织
		else if (StringUtils.equals("2", modelType)) {
			headers.put("name", "部门名称");
			headers.put("companyNo", "法人单位编码");
			headers.put("industryType", "所属行业类别");
			headers.put("registerType", "登记注册类型");
			headers.put("corporate", "法人代表");
			headers.put("taxpayerRegistrationNo", "纳税人登记号");
			headers.put("administrativeDivisionCode", "行政区划代码");
			headers.put("approvalDocNo", "批准成立文号");
			// 表头字体颜色需要改变的单元格序号数组
			int[] changeTitleFont = { 0,1};
			
			List<Department> dataList = new ArrayList<Department>();
			if(StringUtils.equals("1", isExportData)){//导出已有数据
				dataList=departmentDao.getDepartmentList(params);
				if(null != dataList && dataList.size() > 0) {
					List<String> deptNames=dataList.stream().map(Department::getName).collect(Collectors.toList());
					String[] deptNamesStr=new String[deptNames.size()];
					dropDownMap.put("0", deptNames.toArray(deptNamesStr));
				}
			}
			book = ExcelUtil.exportExcel("法人信息", headers, dataList, changeTitleFont, dropDownMap);
		}
			
		
		
		
		return book;
	}
	
//	/**
//	 * 获取该组织的所有下级部门ID,包含自己
//	 * @param params
//	 * @param deptId
//	 * @return
//	 */
//	private List<String> getSubDeptIdList(Map<String,Object> params,String deptId){
//		List<String> subDeptIdList = new ArrayList<String>();
//		//查询的部门
//		List<Department> queryDepts = new ArrayList<Department>();
//		Department queryDept = new Department();
//		queryDept.setId(Integer.valueOf(params.get("deptId").toString()));
//		queryDepts.add(queryDept);
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("companyId", params.get("companyId").toString());
//		//所有部门
//		List<Department> allDepts = departmentDao.getDepartmentList(m);
//		List<Department> showDepts = new ArrayList<Department>();
//		DeptUtil.getAllDepts(queryDepts, allDepts, showDepts);
//		for(Department dept : showDepts){
//			subDeptIdList.add(dept.getId().toString());
//		}
//		return subDeptIdList;
//	}

	/**
	 * 获取组织树状结构---（组织下面可能包含员工，职位）treeType--1:职位；2:员工
	 * @param params
	 * @return
	 */
	@Override
	public List<DepartmentTreePojo> getCommonInfoByDepartmentTree(
			Map<String, Object> params) {
		//获取全部的树
		List<DepartmentTreePojo> allDeptList=departmentDao.getDepartmentTreeCompanyId(params);
		List<DepartmentTreePojo> pList = new ArrayList<DepartmentTreePojo>();
		if("1".equals(params.get("treeType").toString())){
			//获取全部的职位
			List<CompanyPositionInfo> positionList=companyPositionInfoDao.getPositionInfoListByParams(params);
			for(CompanyPositionInfo cpi : positionList){
				DepartmentTreePojo dtp = new DepartmentTreePojo();
				dtp.setId(cpi.getId().intValue());
				dtp.setName(cpi.getName());
				dtp.setParentDeptId(cpi.getDeptId().intValue());
				pList.add(dtp);
			}
		}else if("2".equals(params.get("treeType").toString())){
			//新建一个map是为了防止selectByExample这个公共方法过滤条件太多。
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("companyId", params.get("companyId").toString());
			//获取全部的员工
			List<User> userList=userDao.selectByExample(userMap);
			for(User u : userList){
				DepartmentTreePojo dtp = new DepartmentTreePojo();
				dtp.setId(u.getId().intValue());
				dtp.setName(u.getName());
				dtp.setParentDeptId(u.getDeptId().intValue());
				pList.add(dtp);
			}
		}
		//返回有职位的部门树
		List<DepartmentTreePojo> deptListTree = DeptUtil.getChildDepartments(allDeptList,pList);
		
		return deptListTree;
	}
	
	
	private Department initSubDept(Long companyId , Integer parentDeptId, String deptName) {
	    // 对deptName进行处理
	    Department dept = new Department();
	    dept.setCompanyId(companyId);
	    dept.setLevel((byte)2);
	    dept.setName(deptName);
	    dept.setShortName(deptName);
        dept.setParentDeptId(parentDeptId);
        dept.setDeptNo("");
        dept.setRemark("");
        return dept;
	}
	
}
