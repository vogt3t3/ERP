package com.aiiju.service;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.position.CompanyPositionInfo;
import com.aiiju.bean.oa.position.WorkQualification;

/**
 * 
 * @ClassName: IPositionManageServices
 * @Description: 职位管理接口
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 *
 */
@Repository
public interface IPositionManageService {
	/**
	 * 添加职位
	 * @param params
	 * @return
	 */
	int addPositionInfo(Map<String,Object> params);
	/**
	 * 修改职位
	 * @param params
	 * @return
	 */
	int updatePositionInfo(Map<String,Object> params);
	/**
	 * 删除职位
	 * @param params
	 * @return
	 */
	int delPositionInfo(Map<String,Object> params);
	
	/**
	 * 获取职位列表
	 * @param params
	 * @return
	 */
	Map<String, Object> getPositionInfoListByParams(Map<String, Object> params);
	
	/**
	 * 根据Id查询职位对象
	 * @param params
	 * @return
	 */
	CompanyPositionInfo getCompanyPositionInfoById(Map<String, Object> params);
	/**
	 * 添加任职资格
	 * @param params
	 * @return
	 */
	int addWorkQualification(Map<String,Object> params);
	/**
	 * 修改任职资格
	 * @param params
	 * @return
	 */
	int updateWorkQualification(Map<String,Object> params);
	/**
	 * 删除任职资格
	 * @param params
	 * @return
	 */
	int delWorkQualification(Map<String,Object> params);
	/**
	 * 根据ID获取任职资格对象
	 * @param params
	 * @return
	 */
	WorkQualification getWorkQualificationById(Map<String,Object> params);
	
	/**
	 * 获取任职资格列表
	 * @param params
	 * @return
	 */
	Map<String, Object> getWorkQualificationListByParams(Map<String, Object> params);
	
	 /**
     * 导出职位列表Excel
     * @param params
     * @return
     */
    HSSFWorkbook exportPositionListExcel(Map<String, Object> params);
    
    /**
     * 导出职位模版Excel
     * @param params
     * @return
     */
    HSSFWorkbook exportPositionModelExcel(Map<String, Object> params);
    
    /**
     * 导入职位数据
     * @param params
     * @return
     */
    Map<String, Object> importPositionData(Map<String, Object> params);
}
