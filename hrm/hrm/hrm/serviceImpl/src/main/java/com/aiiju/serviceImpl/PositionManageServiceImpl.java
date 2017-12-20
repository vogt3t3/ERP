package com.aiiju.serviceImpl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.duty.CompanyDutyInfo;
import com.aiiju.bean.oa.position.CompanyPositionInfo;
import com.aiiju.bean.oa.position.WorkQualification;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.duty.ICompanyDutyInfoDao;
import com.aiiju.dao.oa.position.ICompanyPositionInfoDao;
import com.aiiju.dao.oa.position.IWorkQualificationDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.IPositionManageService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.enums.PositionModelEnum;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.exception.DAOException;
import com.aiiju.util.exception.DAOExceptionMessage;
import com.aiiju.util.page.Pagination;
import com.alibaba.fastjson.JSONObject;
@Service("positionManageService")
public class PositionManageServiceImpl  extends CommonPageService implements IPositionManageService{
	
	@Autowired
    private  ICompanyPositionInfoDao companyPositionInfoDao;
	
	@Autowired
	private ICompanyDutyInfoDao companyDutyInfoDao;
	
	@Autowired
    private IWorkQualificationDao workQualificationDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
    private JedisPool jedisPool;
	
	@Override
	public int addPositionInfo(Map<String, Object> params) {
		CompanyPositionInfo  obj=JSONObject.parseObject(params.get("position").toString(), CompanyPositionInfo.class);
		obj.setCompanyId(Long.valueOf(params.get("companyId").toString()));
		return companyPositionInfoDao.addPositionInfo(obj);
	}

	@Override
	public int updatePositionInfo(Map<String, Object> params) {
		CompanyPositionInfo  obj=JSONObject.parseObject(params.get("position").toString(), CompanyPositionInfo.class);
		return companyPositionInfoDao.updatePositionInfo(obj);
	}

	@Override
	public int delPositionInfo(Map<String, Object> params) {
		String id=params.get("id").toString();
		String[]  array=id.split(",");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("positionIds",array);
		//如果该职位下有员工就不让删除
		int count=userDao.countUser(map);
		if(count==0){
			params.put("array", array);
			return companyPositionInfoDao.delPositionInfo(params);	
		}else{
			//有员工，不删除
			return 2;
		}
	
	}

	@Override
	public Map<String, Object> getPositionInfoListByParams(
			Map<String, Object> params) {
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> deptParams=new HashMap<String, Object>();
       
        if(params.get("deptId")!=null){
        	 deptParams.put("companyId",params.get("companyId"));
             deptParams.put("deptId",params.get("deptId"));
             List<Department> subDepts= getAllDeptsEnter(deptParams);
             if(subDepts!=null&&!subDepts.isEmpty()){
             	params.put("subDepts", subDepts);
             }
        }
       
		int num=companyPositionInfoDao.getPositionInfoListCountByParams(params);
		int pageNum=Integer.parseInt(params.get("pageNum").toString());
		int pageSize=Integer.parseInt(params.get("pageSize").toString());
		Pagination pt=new Pagination(num,pageNum,pageSize);
		params.put("pageSize", pageSize);
		params.put("startRow", pt.getStartPos());

		List<CompanyPositionInfo> list=companyPositionInfoDao.getPositionInfoListByParams(params);
		 if(list!=null&&!list.isEmpty()){
			 for(CompanyPositionInfo info:list){
				 if(info.getIsSleep()!=null&&info.getIsSleep().intValue()==1){
					 info.setIsSleepStr("是");
				 }else{
					 info.setIsSleepStr("否");
				 }
			 }
		 }
		
		//返回前端结果集
		map.put("result", list);
		map.put("page", pt);
		return map;
	}

	@Override
	public int addWorkQualification(Map<String, Object> params) {
		WorkQualification  obj=JSONObject.parseObject(params.get("workQualification").toString(), WorkQualification.class);
		obj.setCompanyId(Long.valueOf(params.get("companyId").toString()));
		workQualificationDao.insert(obj);
		return obj.getId().intValue();
	}

	@Override
	public int updateWorkQualification(
			Map<String, Object> params) {
		WorkQualification  obj=JSONObject.parseObject(params.get("workQualification").toString(), WorkQualification.class);
		return workQualificationDao.updateByExample(obj);
	}

	@Override
	public int delWorkQualification(Map<String, Object> params) {
		Long id=Long.parseLong(params.get("id").toString());
		return workQualificationDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getWorkQualificationListByParams(
			Map<String, Object> params) {
		Jedis redis = jedisPool.getResource();
		List<WorkQualification> list=workQualificationDao.selectByExample(params);
		if(list!=null&&!list.isEmpty()){
			for(WorkQualification wq:list){
				wq.setItemName(redis.get("3_"+wq.getItemId().intValue()));
			}
		}
		redis.close();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("result",list);
		return map;
	}

	@Override
	public CompanyPositionInfo getCompanyPositionInfoById(
			Map<String, Object> params) {
		CompanyPositionInfo cpi=companyPositionInfoDao.getPositionInfoById(Long.parseLong(params.get("id").toString()));
		return cpi;
	}

	@Override
	public WorkQualification getWorkQualificationById(Map<String, Object> params) {
		WorkQualification  wq=workQualificationDao.selectById(Long.parseLong(params.get("id").toString()));
		return wq;
	}

	@Override
	public HSSFWorkbook exportPositionListExcel(Map<String, Object> params) {
		// 获取职务temp模板 dutyTemp
		// List<PageDisplayInfo> tempList = this.getDisplayTemplate(params);
		// 获取结果集
		List<CompanyPositionInfo> dataList = null;
		dataList = companyPositionInfoDao.getPositionInfoListByParams(params);
		if (dataList != null && !dataList.isEmpty()) {
			for (CompanyPositionInfo info : dataList) {
				if (info.getIsSleep() != null && info.getIsSleep().intValue() == 1) {
					info.setIsSleepStr("是");
				} else {
					info.setIsSleepStr("否");
				}
			}
		}

		// 生成Excel文件
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		/*for (PageDisplayInfo pdi : tempList) {
			if (pdi.isCheck()) {
				headers.put(pdi.getOriginField(), pdi.getShowName());
			}
		}*/
		headers.put("name", "职位名称");
		headers.put("deptName", "所属组织");
		headers.put("dutyName", "所属职务");
		headers.put("remark", "描述说明");

		HSSFWorkbook book = ExcelUtil.exportExcel("职位信息列表", headers, dataList, null, null);
		return book;
	}
	
	@Override
	public HSSFWorkbook exportPositionModelExcel(Map<String, Object> params) {
		// 声明表头
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		// 声明职位信息列表
		List<CompanyPositionInfo> dataList = new ArrayList<CompanyPositionInfo>();
		// 声明下拉列表Map（key是需要设置下拉列表项的单元格列序号，value是存放列表数据的String[]）
		Map<String, Object> dropDownMap = new HashMap<String, Object>();
		// 声明excel表格
		HSSFWorkbook book = new HSSFWorkbook();
		// 导出模版类型，模版间有从属关系
		String modelType = (String) params.get("modelType");
		// 模板是否导出已有数据
		String isExportData = (String) params.get("isExportData");
		if (StringUtils.equals("1", isExportData)) { // 导出已有数据
			dataList = companyPositionInfoDao.getPositionInfoListByParams(params);
		}

		if (StringUtils.equals("1", modelType)) { // 职务信息模版
			headers.put("name", "职位名称");
			headers.put("dutyTypeName", "所属职务");
			headers.put("deptName", "所属组织");
			headers.put("positionNo", "职位序号");
			headers.put("remark", "描述说明");
			// 表头字体颜色需要改变的单元格序号数组
			int[] changeTitleFont = { 0, 2 };
			List<CompanyDutyInfo> companyDutyInfos = null;
			List<Department> departments = null;
			try {
				// 获取职务名称
				companyDutyInfos = companyDutyInfoDao.getDutyListByParams(params);
				// 获取组织名称
				departments = departmentDao.getDepartmentList(params);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR);
			}
			if (null != companyDutyInfos && companyDutyInfos.size() > 0) {
				// 职务名称数组（拒绝for循环，从我做起。）
				List<String> dutyNames = new ArrayList<String>();
				companyDutyInfos.forEach(companyDutyInfo -> dutyNames.add(companyDutyInfo.getName()));
				// 组装下拉列表
				String[] dutyNameStr = new String[dutyNames.size()];
				dropDownMap.put("1", dutyNames.toArray(dutyNameStr));
			}
			if (null != departments && departments.size() > 0) {
				// 组织名称数组（拒绝for循环，从我做起。）
				List<String> deptNames = new ArrayList<String>();
				departments.forEach(department -> deptNames.add(department.getName()));
				// 组装下拉列表
				String[] deptNameStr = new String[deptNames.size()];
				dropDownMap.put("2", deptNames.toArray(deptNameStr));
			}
			book = ExcelUtil.exportExcel("职位信息", headers, dataList, changeTitleFont, dropDownMap);
		}

		return book;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importPositionData(Map<String, Object> params) {
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
		ArrayList<ArrayList<Object>> positionDatas = null;
		try {
			positionDatas = ExcelUtil.readRows(fileName);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.READ_FILE_FAIL);
		}

		if (null != positionDatas && positionDatas.size() > 0) { // 存在数据
			if (!CommonUtil.compareList(PositionModelEnum.getModelHeader(), positionDatas.get(0))) { // 判断表头是否完整且保持原有模版的顺序
				throw new BizException(BizExceptionMessage.EXCEL_MODEL_ERROR);
			} else {
				// 判断关键字段是否为空
				List<Object> resultList = positionDatas.stream()
						.filter(dutyData -> StringUtils.isEmpty(String.valueOf(dutyData.get(0)))
								|| StringUtils.isEmpty(String.valueOf(dutyData.get(2))))
						.collect(Collectors.toList());
				if (null != resultList && resultList.size() > 0) { // 有数据，说明关键字段存在非空
					throw new BizException(BizExceptionMessage.EMPTY_ERROR);
				} else { // 无数据， 关键字段不存在非空
					// 去除表头
					positionDatas.remove(0);
					if (null != positionDatas && !positionDatas.isEmpty()) { // 除去表头后，存在数据
						// 获取用户提交的文件中的所属职务、所属组织数据
						List<String> dutyNames = new ArrayList<String>();
						positionDatas.forEach(list -> dutyNames.add(String.valueOf(list.get(1))));
						List<String> deptNames = new ArrayList<String>();
						positionDatas.forEach(list -> deptNames.add(String.valueOf(list.get(2))));

						List<CompanyDutyInfo> companyDutyInfos = null;
						List<Department> departments = null;
						try {
							// 获取职务名称（根据公司ID）
							companyDutyInfos = companyDutyInfoDao.getDutyListByParams(params);
							// 获取组织名称（根据公司ID）
							departments = departmentDao.getDepartmentList(params);
						} catch (Exception e) {
							throw new BizException(BizExceptionMessage.DB_ERROR);
						}

						// 职务类型
						List<String> dutyNameData = companyDutyInfos.stream().map(CompanyDutyInfo::getName)
								.collect(Collectors.toList());
						// 职等
						List<String> deptData = departments.stream().map(Department::getName)
								.collect(Collectors.toList());
						// 查询职务类型，职等上下限数据是否在数据库中存在
						if (dutyNameData.containsAll(dutyNames) && deptData.containsAll(deptNames)) {
							// 将职务（基础数据组装成map<K, V>，其中K是职务名称，V是id）。注：如果companyDutyInfos存在name为空或者重复的情况，下面代码是会报错的。
							// 不用id做map的key是因为下面根据Excel表格中字段名称取id好取一些，例如第351行
							Map<String, Long> dutyNameMap = companyDutyInfos.stream()
									.collect(Collectors.toMap(CompanyDutyInfo::getName, CompanyDutyInfo::getId));

							// 将组织（基础数据组装成map<K, V>，其中K是组织名称，V是id）注：如果departments存在name为空或者重复的情况，下面代码是会报错的。
							Map<String, Integer> deptMap = departments.stream()
									.collect(Collectors.toMap(Department::getName, Department::getId));
							
							// 查询数据库中已经存在的职务数据（请求参数companyId）
							List<CompanyPositionInfo> companyPositionInfosFromDB = companyPositionInfoDao.getPositionInfoListByParams(params);
							List<String> positionsNames = new ArrayList<String>();
							companyPositionInfosFromDB.forEach(companyPositionInfo -> positionsNames.add(companyPositionInfo.getName()));
							
							// 筛选数据-新增
							ArrayList<ArrayList<Object>> addCompanyPositionInfos = (ArrayList<ArrayList<Object>>) positionDatas.stream().filter(companyPositionInfo -> !positionsNames.contains(companyPositionInfo.get(0))).collect(Collectors.toList());
							// 筛选数据-更新(下面这行注释代码不要删，这个暂时不会用到，可以用作统计数据用)
//							ArrayList<ArrayList<Object>> updateCompanyPositionInfos = (ArrayList<ArrayList<Object>>) dutyDatas.stream().filter(companyDutyInfo -> positionsNames.contains(companyPositionInfo.get(0))).collect(Collectors.toList());
							// 声明失败的行号
							StringBuilder failedLineNums = new StringBuilder();
							// 筛选数据-名称重复的数据的行号(此处用Stream暂时没实现，待研究)
							for (int i = 0; i < positionDatas.size(); i++) {
								if (positionsNames.contains(positionDatas.get(i).get(0))) {
									failedLineNums.append(i + 1).append(",");
								}
							}
							resultMap.put("failedLineNums", failedLineNums);
							
							// 重新组装数据
							List<CompanyPositionInfo> companyPositionInfos = new ArrayList<CompanyPositionInfo>();
							for (ArrayList<Object> positionData : addCompanyPositionInfos) {
								CompanyPositionInfo companyPositionInfo = new CompanyPositionInfo();
								companyPositionInfo.setCompanyId(Long.valueOf(params.get("companyId").toString()));
								companyPositionInfo.setName(String.valueOf(positionData.get(0)));
								if (null != dutyNameMap && null != dutyNameMap.get(positionData.get(1))) { //
									companyPositionInfo.setDutyId(dutyNameMap.get(positionData.get(1)));
								}
								if (null != deptMap && null != deptMap.get(positionData.get(2))) { //
									companyPositionInfo.setDeptId(deptMap.get(positionData.get(2)).longValue());
								}
								companyPositionInfo.setPositionNo(String.valueOf(positionData.get(3)));
								if (positionData.get(3) instanceof Double) {
									Double positionNo = (Double) positionData.get(3);
									companyPositionInfo.setPositionNo(String.valueOf(positionNo.intValue()));
								}
								/*if (null != positionData.get(3)) {
									Double positionNo = (Double) positionData.get(3);
									companyPositionInfo.setPositionNo(String.valueOf(positionNo.intValue()));
								}*/
								if (null != positionData.get(4)) {
									companyPositionInfo.setRemark(String.valueOf(positionData.get(4)));
								}
								companyPositionInfos.add(companyPositionInfo);
							}

							// 批量新增
							try {
								if (null != companyPositionInfos && !companyPositionInfos.isEmpty()) {
									params.put("positionInfoAddList", companyPositionInfos);
									companyPositionInfoDao.batchAddPositionInfo(params);
								}
							} catch (Exception e) {
								throw new BizException(BizExceptionMessage.DB_ERROR);
							}
							
						} else {
							throw new BizException(BizExceptionMessage.PARAM_ERROR, "Excel中职务类型或职等上下限数据没有从下拉列表中获取"); // 存在下拉选项的单元格只能从下拉列表中选择数据
						}
					}
				}
			}
		} else {
			throw new BizException(BizExceptionMessage.EXCEL_NOT_EXIST);
		}

		return resultMap;
	}
	/**
	 * 根据传入查询部门，返回该部门及所有子部门--入口
	 * @param: "id"--部门id；
	 * @return:
	 * @throws:
	 */
	public  List<Department> getAllDeptsEnter(Map<String,Object> params){
		//该部门及其子部门
		List<Department> showDepts = new ArrayList<Department>();
		
		List<Department> allDepts = null;
		try {
			allDepts = departmentDao.getDepartmentList(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(DAOExceptionMessage.DB_ERROR);
		}
		//获取当前查询的部门列表
		List<Department> curDepts=new ArrayList<Department>();
		Department curDept = new Department();//departmentDao.getDepartmentById(params);
		curDept.setId(Integer.parseInt(params.get("deptId").toString()));
		curDepts.add(curDept);
		return DeptUtil.getAllDepts(curDepts, allDepts, showDepts);
	}
}
