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

import com.aiiju.bean.oa.duty.AbilityRequire;
import com.aiiju.bean.oa.duty.CompanyDutyInfo;
import com.aiiju.bean.oa.duty.PositionTypeInfo;
import com.aiiju.dao.oa.duty.IAbilityRequireDao;
import com.aiiju.dao.oa.duty.ICompanyDutyInfoDao;
import com.aiiju.dao.oa.duty.IPositionTypeInfoDao;
import com.aiiju.service.IDutyManageService;
import com.aiiju.util.enums.DutyModelEnum;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;
import com.alibaba.fastjson.JSONObject;
@Service("dutyManageService")
public class DutyManageServiceImpl extends CommonPageService implements IDutyManageService{
	@Autowired
	private ICompanyDutyInfoDao companyDutyInfoDao;
	@Autowired
	private IPositionTypeInfoDao positionTypeInfoDao;
	@Autowired
	private IAbilityRequireDao abilityRequireDao;
	@Override
	public int addDutyLevel(Map<String, Object> params) {
		PositionTypeInfo  obj=JSONObject.parseObject(params.get("dutyLevel").toString(), PositionTypeInfo.class);
		obj.setCompanyId(Long.valueOf(params.get("companyId").toString()));
		positionTypeInfoDao.addDutyLevel(obj);
		return obj.getId().intValue();
	}

	@Override
	public int updateDutyLevel(Map<String, Object> params) {
		PositionTypeInfo  obj=JSONObject.parseObject(params.get("dutyLevel").toString(), PositionTypeInfo.class);
		obj.setCompanyId(Long.valueOf(params.get("companyId").toString()));
		return positionTypeInfoDao.updateDutyLevel(obj);
	}

	@Override
	public int delDutyLevel(Map<String, Object> params) {
		return positionTypeInfoDao.delDutyLevel(params);
	}

	@Override
	public Map<String, Object> getDutyLevelListByParams(
			Map<String, Object> params) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<PositionTypeInfo> list=positionTypeInfoDao.getDutyLevelListByParams(params);
		map.put("result",list);
		return map;
	}

	@Override
	public int addDutyInfo(Map<String, Object> params) {
		CompanyDutyInfo  obj=JSONObject.parseObject(params.get("duty").toString(), CompanyDutyInfo.class);
		obj.setCompanyId(Long.parseLong(params.get("companyId").toString()));
		companyDutyInfoDao.addDutyInfo(obj);
		return  obj.getId().intValue();
	}

	@Override
	public int updateDutyInfo(Map<String, Object> params) {
		CompanyDutyInfo  obj=JSONObject.parseObject(params.get("duty").toString(), CompanyDutyInfo.class);
		return companyDutyInfoDao.updateDutyInfo(obj);
	}

	@Override
	public int delDutyInfo(Map<String, Object> params) {
		String id=params.get("id").toString();
		String[]  array=id.split(",");
		params.put("array", array);
		return companyDutyInfoDao.delDutyInfo(params);
	}

	@Override
	public Map<String, Object> getDutyListByParams(Map<String, Object> params) {
		Map<String,Object> map=new HashMap<String, Object>();
		
		int num=companyDutyInfoDao.getDutyListCountByParams(params);
		
		Object isPage=params.get("pageNum");
		int pageNum=0;
		int pageSize=0;
		Pagination pt=null;
		if(isPage!=null){
			pageNum=Integer.parseInt(params.get("pageNum").toString());
			pageSize=Integer.parseInt(params.get("pageSize").toString());
			pt=new Pagination(num,pageNum,pageSize);
			params.put("pageSize", pageSize);
			params.put("startRow", pt.getStartPos());
		}
		
		//获取结果集
		List<CompanyDutyInfo> list= companyDutyInfoDao.getDutyListByParams(params);
		if(list!=null&&!list.isEmpty()){
			for(CompanyDutyInfo info:list){
				if(info.getIsSleep()!=null&&info.getIsSleep().intValue()==1){
					info.setIsSleepStr("是");
				}else{
					info.setIsSleepStr("否");
				}
				
			}
		}
		
		map.put("result", list);
		map.put("page", pt);
		return map;
	}

	@Override
	public int addAbilityRequire(Map<String, Object> params) {
			AbilityRequire  record=JSONObject.parseObject(params.get("abilityRequire").toString(), AbilityRequire.class);
			String companyId = params.get("companyId")==null?null:params.get("companyId").toString();
			record.setCompanyId(Long.parseLong(companyId));
			abilityRequireDao.insert(record);
		
		return record.getId().intValue();
	}

	@Override
	public int updateAbilityRequire(Map<String, Object> params) {
		AbilityRequire  record=JSONObject.parseObject(params.get("abilityRequire").toString(), AbilityRequire.class);
		return abilityRequireDao.updateByExample(record);
	}

	@Override
	public int delAbilityRequire(Map<String, Object> params) {
		return abilityRequireDao.deleteByParam(params);
	}

	@Override
	public Map<String, Object> getAbilityRequireListByParams(
			Map<String, Object> params) {
        Map<String,Object> map=new HashMap<String, Object>();
		//获取结果集
		List<AbilityRequire> list=abilityRequireDao.selectByExample(params);
		map.put("result", list);
		return map;
	}

	@Override
	public CompanyDutyInfo getCompanyDutyInfoById(Map<String, Object> params) {
		CompanyDutyInfo cdi=companyDutyInfoDao.getDutyInfoById(Long.parseLong(params.get("id").toString()));
		return cdi;
	}
	@Override
	public AbilityRequire getAbilityRequireById(Map<String, Object> params) {
		AbilityRequire ar=abilityRequireDao.selectById(Long.parseLong(params.get("id").toString()));
		return ar;
	}

	@Override
	public HSSFWorkbook exportDutyListExcel(Map<String, Object> params) {
		// 获取职务temp模板 dutyTemp
		// List<PageDisplayInfo> tempList = this.getDisplayTemplate(params);
		// 获取结果集
		List<CompanyDutyInfo> dataList = null;

		dataList = companyDutyInfoDao.getDutyListByParams(params);
		if (dataList != null && !dataList.isEmpty()) {
			for (CompanyDutyInfo info : dataList) {
				if (info.getIsSleep() != null && info.getIsSleep().intValue() == 1) {
					info.setIsSleepStr("是");
				} else {
					info.setIsSleepStr("否");
				}
			}
		}

		// 组装Excel表头
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		/*
		 * for (PageDisplayInfo pdi : tempList) { if (pdi.isCheck()) {
		 * headers.put(pdi.getOriginField(), pdi.getShowName()); } }
		 */
		headers.put("name", "职务名称");
		headers.put("dutyTypeName", "职务类型");
		headers.put("dutyLevelBottomName", "职等下限");
		headers.put("dutyLevelTopName", "职等上限");
		headers.put("remark", "描述说明");
		// 生成Excel文件
		HSSFWorkbook book = ExcelUtil.exportExcel("职务信息列表", headers, dataList, null, null);
		return book;

	}

	@Override
	public HSSFWorkbook exportDutyModelExcel(Map<String, Object> params) {
		// 声明表头
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		// 声明职务信息列表
		List<CompanyDutyInfo> dataList = new ArrayList<CompanyDutyInfo>();
		// 声明下拉列表Map（key是需要设置下拉列表项的单元格列序号，value是存放列表数据的String[]）
		Map<String, Object> dropDownMap = new HashMap<String, Object>();
		// 声明excel表格
		HSSFWorkbook book = new HSSFWorkbook();
		// 导出模版类型，模版间有从属关系（V1.0.0版本职务业务只有一个模版）
		String modelType = (String) params.get("modelType");
		// 模板是否导出已有数据
		String isExportData = (String) params.get("isExportData");
		// 此处获取职务信息，是为了下面模版2和3需要取职务名称的值
		if (StringUtils.equals("1", isExportData)) { // 导出已有数据
			dataList = companyDutyInfoDao.getDutyListByParams(params);
		}
		
		if (StringUtils.equals("1", modelType)) { // 职务信息模版
			headers.put("name", "职务名称");
	        headers.put("dutyTypeName", "职务类型");
	        headers.put("dutyLevelBottomName", "职等下限");
	        headers.put("dutyLevelTopName", "职等上限");
	        headers.put("remark", "描述说明");
	        headers.put("dutyNo", "职务序号");
	        // 表头字体颜色需要改变的单元格序号数组
			int[] changeTitleFont = {0, 1};
			// 获取职务名称/职等下限/职等上限的数据（根据公司ID）
			List<PositionTypeInfo> positionTypeInfos = positionTypeInfoDao.getDutyLevelListByParams(params);
			if (null != positionTypeInfos && positionTypeInfos.size() > 0) {
				// 职务名称数组（拒绝for循环，从我做起。）
				List<String> dutyTypeNames = new ArrayList<String>();
				positionTypeInfos.stream().filter(positionTypeInfo -> StringUtils.equals("1", String.valueOf(positionTypeInfo.getType())))
						.forEach(positionTypeInfo -> dutyTypeNames.add(positionTypeInfo.getName()));
				// 职等数组（拒绝for循环，从我做起。）
				List<String> dutyLevelNames = new ArrayList<String>();
				positionTypeInfos.stream().filter(positionTypeInfo -> StringUtils.equals("2", String.valueOf(positionTypeInfo.getType())))
						.forEach(positionTypeInfo -> dutyLevelNames.add(positionTypeInfo.getName()));
				// 组装下拉列表
				String[] dutyTypeNameStr = new String[dutyTypeNames.size()];
				String[] dutyLevelNameStr = new String[dutyLevelNames.size()];
				dropDownMap.put("1", dutyTypeNames.toArray(dutyTypeNameStr));
				dropDownMap.put("2", dutyLevelNames.toArray(dutyLevelNameStr));
				dropDownMap.put("3", dutyLevelNames.toArray(dutyLevelNameStr));
			}
			
			book = ExcelUtil.exportExcel("职务信息", headers, dataList, changeTitleFont, dropDownMap);
		}
		
		return book;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importDutyData(Map<String, Object> params) {
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
		ArrayList<ArrayList<Object>> dutyDatas = null;
		try {
			dutyDatas = ExcelUtil.readRows(fileName);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.READ_FILE_FAIL);
		}

		if (null != dutyDatas && dutyDatas.size() > 0) { // 存在数据
			if (!CommonUtil.compareList(DutyModelEnum.getModelHeader(), dutyDatas.get(0))) { // 判断表头是否完整且保持原有模版的顺序
				throw new BizException(BizExceptionMessage.EXCEL_MODEL_ERROR);
			} else {
				// 判断关键字段是否为空
				List<Object> resultList = dutyDatas.stream()
						.filter(dutyData -> StringUtils.isEmpty(String.valueOf(dutyData.get(0)))
								|| StringUtils.isEmpty(String.valueOf(dutyData.get(1)))
								|| StringUtils.isEmpty(String.valueOf(dutyData.get(2)))
								|| StringUtils.isEmpty(String.valueOf(dutyData.get(3))))
						.collect(Collectors.toList());
				if (null != resultList && resultList.size() > 0) { // 有数据，说明关键字段存在非空
					throw new BizException(BizExceptionMessage.EMPTY_ERROR);
				} else { // 无数据， 关键字段不存在非空
					// 去掉表头
					dutyDatas.remove(0);
					if (null != dutyDatas && !dutyDatas.isEmpty()) { // 除去表头后，存在数据
						// 获取用户提交的文件中的职务类型、职等上下限数据
						List<String> dutyTypes = new ArrayList<String>();
						dutyDatas.forEach(list -> dutyTypes.add(String.valueOf(list.get(1))));
						List<String> dutyLevelBottoms = new ArrayList<String>();
						dutyDatas.forEach(list -> dutyLevelBottoms.add(String.valueOf(list.get(2))));
						List<String> dutyLevelTops = new ArrayList<String>();
						dutyDatas.forEach(list -> dutyLevelTops.add(String.valueOf(list.get(3))));
						// 取出职务基础数据（根据公司ID）
						List<PositionTypeInfo> positionTypeInfos = positionTypeInfoDao.getDutyLevelListByParams(params);
						// 职务类型
						List<String> dutyTypeData = positionTypeInfos.stream().filter(
								positionTypeInfo -> StringUtils.equals("1", String.valueOf(positionTypeInfo.getType())))
								.map(PositionTypeInfo::getName).collect(Collectors.toList());
						// 职等
						List<String> dutyLevelData = positionTypeInfos.stream().filter(
								positionTypeInfo -> StringUtils.equals("2", String.valueOf(positionTypeInfo.getType())))
								.map(PositionTypeInfo::getName).collect(Collectors.toList());
						// 查询职务类型，职等上下限数据是否在数据库中存在
						if (dutyTypeData.containsAll(dutyTypes) && dutyLevelData.containsAll(dutyLevelTops)
								&& dutyLevelData.containsAll(dutyLevelBottoms)) {
							// 将职务类型（基础数据组装成map<K, V>，其中K是职务类型名称，V是id）
							Map<String, Long> dutyTypeMap = positionTypeInfos.stream()
									.filter(positionTypeInfo -> StringUtils.equals("1",
											String.valueOf(positionTypeInfo.getType())))
									.collect(Collectors.toMap(PositionTypeInfo::getName, PositionTypeInfo::getId));

							// 将职等（基础数据组装成map<K, V>，其中K是职等名称，V是id）
							Map<String, Long> dutyLevelMap = positionTypeInfos.stream()
									.filter(positionTypeInfo -> StringUtils.equals("2",
											String.valueOf(positionTypeInfo.getType())))
									.collect(Collectors.toMap(PositionTypeInfo::getName, PositionTypeInfo::getId));
							
							// 查询数据库中已经存在的职务数据（请求参数companyId）
							List<CompanyDutyInfo> companyDutyInfosFromDB = companyDutyInfoDao.getDutyListByParams(params);
							List<String> dutyNames = new ArrayList<String>();
							companyDutyInfosFromDB.forEach(companyDutyInfo -> dutyNames.add(companyDutyInfo.getName()));
							
							// 筛选数据-新增
							ArrayList<ArrayList<Object>> addCompanyDutyInfos = (ArrayList<ArrayList<Object>>) dutyDatas.stream().filter(companyDutyInfo -> !dutyNames.contains(companyDutyInfo.get(0))).collect(Collectors.toList());
							// 筛选数据-更新(下面这行注释代码不要删，这个暂时不会用到，可以用作统计数据用)
//							ArrayList<ArrayList<Object>> updateCompanyDutyInfos = (ArrayList<ArrayList<Object>>) dutyDatas.stream().filter(companyDutyInfo -> dutyNames.contains(companyDutyInfo.get(0))).collect(Collectors.toList());
							// 声明失败的行号
							StringBuilder failedLineNums = new StringBuilder();
							// 筛选数据-名称重复的数据的行号(此处用Stream暂时没实现，待研究)
							for (int i = 0; i < dutyDatas.size(); i++) {
								if (dutyNames.contains(dutyDatas.get(i).get(0))) {
									failedLineNums.append(i + 1).append(",");
								}
							}
							resultMap.put("failedLineNums", failedLineNums);
							
							// 重新组装数据
							List<CompanyDutyInfo> companyDutyInfos = new ArrayList<CompanyDutyInfo>();
							for (ArrayList<Object> dutyData : addCompanyDutyInfos) {
								CompanyDutyInfo companyDutyInfo = new CompanyDutyInfo();
								companyDutyInfo.setCompanyId(Long.valueOf(params.get("companyId").toString()));
								companyDutyInfo.setName(String.valueOf(dutyData.get(0)));
								if (null != dutyTypeMap && null != dutyTypeMap.get(dutyData.get(1))) { //
									companyDutyInfo.setDutyTypeId(dutyTypeMap.get(dutyData.get(1)));
								}
								if (null != dutyLevelMap) {
									if (null != dutyLevelMap.get(dutyData.get(2))) {
										companyDutyInfo.setDutyLevelBottomId(dutyLevelMap.get(dutyData.get(2)));
									}
									if (null != dutyLevelMap.get(dutyData.get(3))) {
										companyDutyInfo.setDutyLevelTopId(dutyLevelMap.get(dutyData.get(3)));
									}
								}
								if (null != dutyData.get(4)) {
									companyDutyInfo.setRemark(String.valueOf(dutyData.get(4)));
								}
								companyDutyInfo.setDutyNo(String.valueOf(dutyData.get(5)));
								if (dutyData.get(5) instanceof Double) {
									Double dutyNo = (Double) dutyData.get(5);
									companyDutyInfo.setDutyNo(String.valueOf(dutyNo.intValue()));
								}
								/*if (null != dutyData.get(5)) {
									Double dutyNo = (Double) dutyData.get(5);
									companyDutyInfo.setDutyNo(String.valueOf(dutyNo.intValue()));
								}*/
								companyDutyInfos.add(companyDutyInfo);
							}

							// 批量新增
							try {
								companyDutyInfoDao.batchAddOrUpdateDutyInfo(companyDutyInfos);
							} catch (Exception e) {
								throw new BizException(BizExceptionMessage.DB_ERROR, "导入数据全部失败");
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

	
	@Override
	public List<PositionTypeInfo> getDutyTreeByParams(Map<String, Object> params) {
		//获取职务类型列表
		params.put("type", 1);
		List<PositionTypeInfo>  list=positionTypeInfoDao.getDutyLevelListByParams(params);
		//获取所有职务
		List<CompanyDutyInfo> dutyList=companyDutyInfoDao.getDutyListByParams(params);
		//根据不同类型装载职务列表
		if(list!=null&&!list.isEmpty()){
			for(PositionTypeInfo pti:list){
				
				List<CompanyDutyInfo> subDutyList=null;
				
				if(dutyList!=null&&!dutyList.isEmpty()){
					
					subDutyList=new ArrayList<CompanyDutyInfo>();
					
					for(CompanyDutyInfo cdi:dutyList){
						if(cdi.getDutyTypeId().intValue()==pti.getId().intValue()){
							subDutyList.add(cdi);
						}
					}
					
				}
				pti.setSubList(subDutyList);
			}
		}
		return list;
	}
}
