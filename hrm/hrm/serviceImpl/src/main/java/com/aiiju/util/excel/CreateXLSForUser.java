package com.aiiju.util.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.aiiju.bean.oa.staff.User;
import com.aiiju.util.common.ClazzByType;
import com.aiiju.util.enums.UserXls;
/**
 * 人员导出导入xls
 * 
 * @author 维斯
 *	2016年10月28日	 下午4:30:10
 */
public class CreateXLSForUser {

	private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		style.setFont(font);

		return style;
	}
	/**
	 * 普通导出数据
	 * @author 维斯
	 * @param list
	 * @return
	 * 2016年10月28日  下午4:22:09
	 */
	public static Workbook createXLS(List<User> list) {
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("员工表(普通)");
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();  
        HSSFDataFormat format = workbook.createDataFormat();  
        cellStyle2.setDataFormat(format.getFormat("@")); 

		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 循环创建第一行内容
		int i = 0;
		List<String> top=UserXls.getAll();
		List<String> topval=UserXls.getAllVal();
		String[] params = topval.toArray(new String[topval.size()]);
		for (String string : top) {
			cell = row.createCell(i);
			cell.setCellValue(string);
			cell.setCellStyle(cellStyle2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			i++;
		}
		int j = 1;
		for (User user : list) {
			int n = 0;
			HSSFRow nextRow = sheet.createRow(j);
			for (String string : topval) {
			// 第二行追加数据
				if(string.equals("sex")){
					HSSFCell cells = nextRow.createCell(n);
					if("1".equals(user.getSex())){
						cells.setCellValue("男");
					}else if("2".equals(user.getSex())){
						cells.setCellValue("女");
					}else if("0".equals(user.getSex())||"".equals(user.getSex())||user.getSex()==null){
						cells.setCellValue("未设置");
					}
					cells.setCellStyle(cellStyle2);//设置单元格格式为"文本"
					cells.setCellType(HSSFCell.CELL_TYPE_STRING); 
					n++;
					continue;
				}
				if(string.equals(params[n])){
					HSSFCell cells = nextRow.createCell(n);
					if(ClazzByType.getFieldValueByName(params[n], user)==null){
						cells.setCellValue("");
						cells.setCellStyle(cellStyle2);//设置单元格格式为"文本"
						cells.setCellType(HSSFCell.CELL_TYPE_STRING); 
						n++;
						continue;
					}else {
						cells.setCellValue(ClazzByType.getFieldValueByName(params[n], user).toString());
						cells.setCellStyle(cellStyle2);//设置单元格格式为"文本"
						cells.setCellType(HSSFCell.CELL_TYPE_STRING); 
						n++;
						continue;
					}
				}
				
			}
			j++;
		}
		return workbook;
	}
	/**
	 * 快速导出模板(含下拉选项)
	 * @author 维斯
	 * @param postlist
	 * @param deptName
	 * @return
	 * 2016年12月19日   下午2:46:08
	 */
	public static Workbook fastEmpXLS(List<String> postlist,List<String> deptName){
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("员工表(快速)");
		String[] params = {"姓名","电话号码","部门","职位","员工编号"};
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();  
        HSSFDataFormat format = workbook.createDataFormat();  
        cellStyle2.setDataFormat(format.getFormat("@"));  
        
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 循环创建第一行内容
		
		for (int j = 0; j < params.length; j++) {
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(params[j]);
			cell.setCellStyle(cellStyle2);
		}
		row = sheet.createRow(1);		
		for (int j = 0; j < params.length; j++) {
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle2);
			if("部门".equals(params[j])){
				sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, deptName.toArray(new String[deptName.size()]), 1, 200, j, j)); 
				continue;
			}
			if("职位".equals(params[j])){
				sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, postlist.toArray(new String[postlist.size()]), 1, 200, j, j)); 
				continue;
			}
		}
		return workbook;
		
	}
	
	/**
	 * 快速维护导出
	 * @author 维斯
	 * 2016年12月3日   下午3:09:52
	 */
	public static Workbook fastCreateXLS(List<User> list){
		
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("员工表(快速)");
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 循环创建第一行内容
		int i = 0;
		String[] params = {"姓名","电话号码","部门","职位","员工编号"};
		for (String string : params) {
			cell = row.createCell(i);
			cell.setCellValue(string);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			i++;
		}
		int j = 1;
		for (User user : list) {
			int n = 0;
			HSSFRow nextRow = sheet.createRow(j);
			for (String string : params) {
			// 第二行追加数据
				HSSFCell cells = nextRow.createCell(n);
				if("姓名".equals(string)){
					cells.setCellValue(user.getName());
					cells.setCellStyle(style);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					n++;
					continue;
				}else if ("电话号码".equals(string)) {
					cells.setCellValue(user.getPhone());
					cells.setCellStyle(style);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					n++;
					continue;
				}else if ("部门".equals(string)) {
					cells.setCellValue(user.getDeptName());
					cells.setCellStyle(style);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					n++;
					continue;
				}else if ("职位".equals(string)) {
					cells.setCellValue(user.getPositionName());
					cells.setCellStyle(style);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					n++;
					continue;
				}else if ("员工编号".equals(string)) {
					cells.setCellValue(user.getUserNo());
					cells.setCellStyle(style);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					n++;
					continue;
				}
			}
			j++;
		}
		return workbook;
		
	}
	/**
	 * 人员结构统计导出 二级标题  1级合并
	 * @author 维斯
	 * @param map
	 * @param dept
	 * @param posNum 职等长度
	 * @param top 一级标题
	 * @return
	 * 2016年10月28日  下午4:21:23
	 */
	public static Workbook userCount(Map<String, Map<String, Integer>> map,String dept,int posNum) {
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("员工表(统计)");
		HSSFCellStyle style = getStyle(workbook);
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 循环创建第一行内容
		cell = row.createCell(0);
		cell.setCellValue("");
		//性别2  状态2  学历5  职等?  在职年限5  年龄4
		int i = 1;
		for (String parms : map.keySet()) {
			int num=0;
			if("性别".equals(parms)){
				num=1+i;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			
			if("状态".equals(parms)){
				num=1+i;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			
			if("年龄".equals(parms)){
				num=3+i;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			if("学历".equals(parms)){
				num=4+i;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			if("在职年限".equals(parms)){
				num=4+i;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			if("职等".equals(parms)){
				num=posNum+i-1;
				CellRangeAddress cra = new CellRangeAddress(0, 0, i, num);
				sheet.addMergedRegion(cra);
				cell = row.createCell(i);
				cell.setCellValue(parms);
				cell.setCellStyle(style);
				i=num+1;
				continue;
			}
			
			
		
		}
		// 创建第二行
		row = sheet.createRow(1);
		int j = 1;
		for (String parms : map.keySet()) {
			for (String cell2 : map.get(parms).keySet()) {
				cell = row.createCell(j);
				cell.setCellValue(cell2);
				cell.setCellStyle(style);
				j++;
			}
		}
		// 创建下一行 插入数据
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(dept);
		int n = 1;
		for (String parm : map.keySet()) {
			for (String cell2 : map.get(parm).keySet()) {
				cell = row.createCell(n);
				if("".equals(map.get(parm).get(cell2))||map.get(parm).get(cell2)==null){
					cell.setCellValue("0");
				}else {
					cell.setCellValue(map.get(parm).get(cell2));
				}
				cell.setCellStyle(style);
				n++;
			}
		}
		return workbook;
	}
	
	/**
	 *  导出有下拉数据xls
	 * @author 维斯
	 * @param status 			在职状态
	 * @param maritalStatus		婚姻状态
	 * @param certificateType	证件类型
	 * @param nation			民族
	 * @param positionId		职位
	 * @param eduational		学历
	 * @param formalFace		政治面貌
	 * @param sex				男/女
	 * @param deptName			部门
	 * @return
	 * 2016年11月1日  下午2:50:36
	 */
	public static Workbook exportXls(List<String> status,List<String> maritalStatus,List<String> certificateType,List<String> eduational,
			List<String> nation,List<String> positionId,
			List<String> formalFace,String[] sex,String[] params,List<String> deptName){
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("员工表(普通)");
		
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();  
        HSSFDataFormat format = workbook.createDataFormat();  
        cellStyle2.setDataFormat(format.getFormat("@"));  
        
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 循环创建第一行内容
		
		for (int j = 0; j < params.length; j++) {
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(params[j]);
			cell.setCellStyle(cellStyle2);
		}
		 row = sheet.createRow(1);
			for (int j = 0; j < params.length; j++) {
				cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle2);
				if("部门".equals(params[j])&&deptName!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, deptName.toArray(new String[deptName.size()]), 1, 200, j, j)); 
					continue;
				}
				if("证件类型".equals(params[j])&&certificateType!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, certificateType.toArray(new String[certificateType.size()]), 1, 200, j, j)); 
					continue;
				}
				if("状态".equals(params[j])&&status!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, status.toArray(new String[status.size()]), 1, 200, j,j)); 
					continue;
				}
				if("民族".equals(params[j])&&nation!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, nation.toArray(new String[nation.size()]), 1, 200, j, j)); 
					continue;
				}
				if("婚姻状况".equals(params[j])&&maritalStatus!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, maritalStatus.toArray(new String[maritalStatus.size()]), 1, 200, j, j)); 
					continue;
				}
				if("政治面貌".equals(params[j])&&formalFace!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, formalFace.toArray(new String[formalFace.size()]), 1, 200, j, j)); 
					continue;
				}
				if("性别".equals(params[j])&&sex!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, sex, 1, 200,j, j)); 
					continue;
				}
				if("职位名称".equals(params[j])&&positionId!=null){
					sheet.addValidationData(CreateXLSForUser.setDataValidation(sheet, positionId.toArray(new String[positionId.size()]), 1, 200, j, j)); 
					continue;
				}
				
			}
		return workbook;
	}
	
	
	
	
	/**
	 * 
	 * @author 维斯
	 * @param sheet		工作表
	 * @param textList	列表内容
	 * @param firstRow 	起始行
	 * @param endRow	终止行
	 * @param firstCol	起始列
	 * @param endCol	终止列
	 * @return
	 * 2016年11月1日  上午10:54:27
	 */
    public static HSSFDataValidation setDataValidation(Sheet sheet,String[] textList, int firstRow, int endRow, int firstCol, int endCol) {    
        
    	CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol,endCol);  
        // 生成下拉框内容  
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);  
        // 绑定下拉框和作用区域  
        HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);  
        return data_validation;    
    }  
	
}
