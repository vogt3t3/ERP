package com.aiiju.service.workStage;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @ClassName: IAttendanceService
 * @Description: 考勤统计
 * @author BZ
 * @date 2017年1月6日 上午11:02:23 
 *
 */


public interface IAttendanceService {

	/**
	 * 根据参数获取考勤统计
	 * @param params
	 * @return
	 */
	public Map<String, Object> getAttendanceByParams(Map<String, Object> params);
	/**
	 * 导出考勤统计
	 * @param params
	 * @return
	 */
	public HSSFWorkbook exportAttendanceExcel(Map<String, Object> params);
	
}
