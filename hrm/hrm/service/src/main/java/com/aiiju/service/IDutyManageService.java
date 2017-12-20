package com.aiiju.service;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.duty.AbilityRequire;
import com.aiiju.bean.oa.duty.CompanyDutyInfo;

/**
 * @description 职务管理接口
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 */
@Repository
public interface IDutyManageService {
	/**
	 * 添加职等/职务类别 1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int addDutyLevel(Map<String,Object> params);
	/**
	 * 修改职等/职务类别  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int updateDutyLevel(Map<String,Object> params);
	/**
	 * 删除职等/职务类别  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int delDutyLevel(Map<String,Object> params);
	
	/**
	 * 获取职等/职务类别列表  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	public Map<String, Object> getDutyLevelListByParams(Map<String, Object> params);
	/**
	 * 添加职务
	 * @param params
	 * @return
	 */
	int addDutyInfo(Map<String,Object> params);
	/**
	 * 修改职务
	 * @param params
	 * @return
	 */
	int updateDutyInfo(Map<String,Object> params);
	/**
	 * 删除职务
	 * @param params
	 * @return
	 */
	int delDutyInfo(Map<String,Object> params);
	
	/**
	 * 获取职务列表
	 * @param params
	 * @return
	 */
	Map<String, Object> getDutyListByParams(Map<String, Object> params);
	/**
	 * 根据职务类别获取职务树
	 * @param params
	 * @return
	 */
	List getDutyTreeByParams(Map<String, Object> params);
	 /**
	  * 通过ID获取公司职务对象
	  * @param params
	  * @return
	  */
	 CompanyDutyInfo getCompanyDutyInfoById(Map<String, Object> params);
	/**
	 * 添加职务能力要求
	 * @param params
	 * @return
	 */
	int addAbilityRequire(Map<String,Object> params);
	/**
	 * 修改职务能力要求
	 * @param params
	 * @return
	 */
	int updateAbilityRequire(Map<String,Object> params);
	/**
	 * 删除职务能力要求
	 * @param params
	 * @return
	 */
	int delAbilityRequire(Map<String,Object> params);
	
	/**
	 * 获取职务能力要求列表
	 * @param params
	 * @return
	 */
	Map<String, Object> getAbilityRequireListByParams(Map<String, Object> params);
	/**
	 * 根据ID获取能力要求对象
	 * @param params
	 * @return
	 */
	AbilityRequire getAbilityRequireById(Map<String, Object> params);

	/**
	 * 导出职务列表数据Excel（动态）
	 * @param params
	 * @return
	 */
	HSSFWorkbook exportDutyListExcel(Map<String, Object> params);
	
	/**
	 * 导出职务列表模版Excel文件
	 * @param params
	 * @return
	 */
	HSSFWorkbook exportDutyModelExcel(Map<String, Object> params);
	
	/**
	 * 导入职务数据
	 * @param params
	 * @return
	 */
	Map<String, Object> importDutyData(Map<String, Object> params);
}
