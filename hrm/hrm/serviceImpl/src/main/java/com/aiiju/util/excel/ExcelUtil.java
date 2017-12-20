package com.aiiju.util.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.aiiju.util.exception.BizException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** 
 * @ClassName: ExcelUtil
 * @Description: excel工具类
 * @author BZ
 * @date 2016年11月4日 下午3点05分00秒
 *  
 */

public class ExcelUtil {
	
	// Excel 2003
	private final static String XLS = "xls";

	// Excel 2007
	private final static String XLSX = "xlsx";
	
	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path){
		boolean flag = false;  
	    File file = new File(path);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;
	}

	//以下部分是那托老师写的导入
	public static Workbook getWorkbook(String excelFile) throws IOException {
        return getWorkbook(new FileInputStream(excelFile));
    }
    
    public static Workbook getWorkbook(InputStream is) throws IOException {

        Workbook wb = null;

        ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int count = -1;
        while ((count = is.read(buffer)) != -1)
            byteOS.write(buffer, 0, count);
        byteOS.close();
        byte[] allBytes = byteOS.toByteArray();

        try {
            wb = new XSSFWorkbook(new ByteArrayInputStream(allBytes));
        } catch (Exception ex) {
            wb = new HSSFWorkbook(new ByteArrayInputStream(allBytes));
        }

        return wb;
    }
   
    public static ArrayList<ArrayList<Object>> readAllRows(InputStream is) throws IOException {
        Workbook wb = getWorkbook(is);
        ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
        
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
            Sheet sheet = wb.getSheetAt(i);
            rowList.addAll(readRows(sheet));
        }

        return rowList;
    }

    public static ArrayList<ArrayList<Object>> readRows(String excelFile,
            int startRowIndex, int rowCount) throws IOException {
        return readRows(new FileInputStream(excelFile), startRowIndex, rowCount);
    }
    
    public static ArrayList<ArrayList<Object>> readRows(String excelFile) throws IOException {
        return readRows(new FileInputStream(excelFile));
    }

    public static ArrayList<ArrayList<Object>> readRows(InputStream is,
            int startRowIndex, int rowCount) throws IOException {
        Workbook wb = getWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);

        return readRows(sheet, startRowIndex, rowCount);
    }
    
    public static ArrayList<ArrayList<Object>> readRows(InputStream is) throws IOException {
        Workbook wb = getWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        return readRows(sheet);
    }

    public static ArrayList<ArrayList<Object>> readRows(Sheet sheet,
            int startRowIndex, int rowCount) {
        ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
        
        for (int i = 0; i <= (startRowIndex + rowCount); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                break;
            }

            // 获取列数
            int column = sheet.getRow(0).getPhysicalNumberOfCells();
            ArrayList<Object> cellList = new ArrayList<Object>();
            for (int j = 0; j < column; j++) {
            	if (null != row.getCell(j)) {
            		cellList.add(readCell(row.getCell(j)));
            	} else {
            		cellList.add("");
            	}
            }
            rowList.add(cellList);
        }
        
        return rowList;
    }
    
    public static ArrayList<ArrayList<Object>> readRows(Sheet sheet) {
        int rowCount = sheet.getLastRowNum();
        return readRows(sheet, 0, rowCount);
    }

    /**
     * 从Excel读Cell
     * 
     * @param cell
     * @return
     */
    private static Object readCell(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            String str = cell.getRichStringCellValue().getString();
            return str == null ? "" : str.trim();

        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }

        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue();

        case Cell.CELL_TYPE_FORMULA:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getCellFormula();
            }

        case Cell.CELL_TYPE_BLANK:
            return "";

        default:
            System.err.println("Data error for cell of excel: " + cell.getCellType());
            return "";
        }
    }
    
    /**
     * 
     * @Title: exportExcel 
     * @Description: 生成并返回工作簿对象
     * @param title
     * @param headers
     * @param dataList
     * @return 
     * @throws
     */
    @SuppressWarnings("deprecation")
	public static <T> HSSFWorkbook exportExcel(String title, LinkedHashMap<String, String> headers, List<T> dataList,
			int[] changeTitleFont, Map<String, Object> dropDownMap) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 25);

		// 生成一个标题样式
		HSSFCellStyle headStyle = workbook.createCellStyle();
		// 设置样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		headStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment((short) 20);
		// 生成一个字体
		HSSFFont headFont = workbook.createFont();
		// headFont.setColor(HSSFColor.VIOLET.index);
		headFont.setFontHeightInPoints((short) 16);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		headStyle.setFont(headFont);

		// 生成一个表头样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		// 设置样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		titleStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		titleStyle.setFillPattern(HSSFCellStyle.NO_FILL);
//		titleStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 14);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(font);

		// 生成并设置主体内容样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		contentStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 14);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		contentStyle.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("aiiju");

		try {
			fillSheet(sheet, dataList, headers, changeTitleFont, dropDownMap, 0, dataList.size() - 1, headStyle,
					titleStyle, contentStyle);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

    /**
     * 填充数据
     * @Title: fillSheet 
     * @Description: 将数据填充到excel表格中
     * @param sheet
     * @param dataList
     * @param headers
     * @param firstIndex
     * @param lastIndex
     * @throws Exception 
     */
    @SuppressWarnings("deprecation")
    private static <T> void fillSheet(HSSFSheet sheet, List<T> dataList, LinkedHashMap<String, String> headers,
    		int[] changeTitleFont, Map<String, Object> dropDownMap, int firstIndex, int lastIndex,  HSSFCellStyle headStyle,
            HSSFCellStyle titleStyle, HSSFCellStyle contentStyle) throws Exception {

        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[headers.size()];
        String[] cnFields = new String[headers.size()];

        // 填充数组
        int count = 0;
        for (Entry<String, String> entry : headers.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        // 填充表头
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            HSSFRichTextString text = new HSSFRichTextString(cnFields[i]);
            cell.setCellValue(text);
            // 把字体染成红色
            if (null != changeTitleFont && changeTitleFont.length > 0) {
            	changeLocalFontColor(sheet, cell, changeTitleFont, i);
            }
            
            if (null != dropDownMap && dropDownMap.size() > 0) {
            	setDropDownList(sheet, dropDownMap, i);
            }
        }

		// 填充内容
		for (int index = firstIndex; index <= lastIndex; index++) {
			// 创建行
			row = sheet.createRow(index + 1);
			// 获取单个对象
			T item = dataList.get(index);
			// 数据写入表格
			for (int i = 0; i < enFields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(contentStyle);
				Object objValue = getFieldValueByNameSequence(enFields[i], item);
				if (objValue instanceof Integer) {
					int value = ((Integer) objValue).intValue();
					cell.setCellValue(value);
				} else if (objValue instanceof String) {
					String s = (String) objValue;
					cell.setCellValue(s);
				} else if (objValue instanceof Double) {
					double d = ((Double) objValue).doubleValue();
					cell.setCellValue(d);
				} else if (objValue instanceof Float) {
					float f = ((Float) objValue).floatValue();
					cell.setCellValue(f);
				} else if (objValue instanceof Long) {
					long l = ((Long) objValue).longValue();
					cell.setCellValue(l);
				} else if (objValue instanceof Boolean) {
					boolean b = ((Boolean) objValue).booleanValue();
					cell.setCellValue(b);
				} else if (objValue instanceof Date) {
					Date d = (Date) objValue;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					String dateStr = sdf.format(d);
					cell.setCellValue(dateStr);
				}
			}
		}
	}
    
    @SuppressWarnings("deprecation")
	public static <T> HSSFWorkbook exportExcelWithHeader(String title, LinkedHashMap<String, String> headers, List<T> dataList,
			int[] changeTitleFont, Map<String, Object> dropDownMap, Map<String, Object> params) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 25);

		// 生成一个标题样式
		HSSFCellStyle headStyle = workbook.createCellStyle();
		// 设置样式
		headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		headStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment((short) 20);
		// 生成一个字体
		HSSFFont headFont = workbook.createFont();
		// headFont.setColor(HSSFColor.VIOLET.index);
		headFont.setFontHeightInPoints((short) 16);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		headStyle.setFont(headFont);

		// 生成一个表头样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		// 设置样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		titleStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		titleStyle.setFillPattern(HSSFCellStyle.NO_FILL);
//		titleStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 14);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(font);

		// 生成并设置主体内容样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		contentStyle.setFillPattern(HSSFCellStyle.NO_FILL);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 14);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		contentStyle.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("aiiju");

		try {
			fillSheetWithHeader(sheet, dataList, headers, changeTitleFont, dropDownMap, 0, dataList.size() - 1, headStyle,
					titleStyle, contentStyle, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
    
    @SuppressWarnings("deprecation")
	private static <T> void fillSheetWithHeader(HSSFSheet sheet, List<T> dataList, LinkedHashMap<String, String> headers,
    		int[] changeTitleFont, Map<String, Object> dropDownMap, int firstIndex, int lastIndex,  HSSFCellStyle headStyle,
            HSSFCellStyle titleStyle, HSSFCellStyle contentStyle, Map<String, Object> params) throws Exception {

        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[headers.size()];
        String[] cnFields = new String[headers.size()];

        // 填充数组
        int count = 0;
        for (Entry<String, String> entry : headers.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }
        
        // 设置标题行
        HSSFRow row = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cnFields.length - 1));
        row.createCell(0);
        row.setHeightInPoints(40);
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        String deptName = (String) params.get("deptName");
        sheet.getRow(0).getCell(0).setCellValue(deptName + " 调薪记录统计    " + startDate + "—" + endDate);
        sheet.getRow(0).getCell(0).setCellStyle(headStyle);

        // 填充表头
        row = sheet.createRow(1);
        for (short i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            HSSFRichTextString text = new HSSFRichTextString(cnFields[i]);
            cell.setCellValue(text);
        }

		// 填充内容
		for (int index = firstIndex; index <= lastIndex; index++) {
			// 创建行
			row = sheet.createRow(index + 2);
			// 获取单个对象
			T item = dataList.get(index);
			// 数据写入表格
			for (int i = 0; i < enFields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(contentStyle);
				Object objValue = getFieldValueByNameSequence(enFields[i], item);
				String fieldValue = "";
                if (objValue instanceof Date) {
                    Date date = (Date) objValue;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    fieldValue = sdf.format(date);
                } else {
                    fieldValue = objValue == null ? "" : objValue.toString();
                }
                HSSFRichTextString richString = new HSSFRichTextString(fieldValue);
                cell.setCellValue(richString);
			}
		}
	}
    
    /**
     * 
     * @Title: exportExcel 
     * @Description: 生成并返回工作簿对象
     * @param title
     * @param headers
     * @param dataList
     * @return 
     * @throws
     */
    @SuppressWarnings("deprecation")
    public static <T> HSSFWorkbook exportExcel(String title, LinkedHashMap<String, String> headers, List<T> dataList, 
            Map<String, Object> params) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        
     // 生成一个标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        // 设置样式
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment((short)20);
        // 生成一个字体
        HSSFFont headFont = workbook.createFont();
//        headFont.setColor(HSSFColor.VIOLET.index);
        headFont.setFontHeightInPoints((short) 16);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);
        
        // 生成一个表头样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        // 设置样式
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
//        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 14);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        titleStyle.setFont(font);
        
        // 生成并设置主体内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 14);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        contentStyle.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("aiiju");

        try {
            fillSheetForFlowInfo(sheet, dataList, headers, 0, dataList.size() - 1, params, headStyle, titleStyle, contentStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workbook;
    }
    
    /**
     * 填充数据
     * @Title: fillSheet 
     * @Description: 将数据填充到excel表格中
     * @param sheet
     * @param dataList
     * @param headers
     * @param firstIndex
     * @param lastIndex
     * @throws Exception 
     */
    @SuppressWarnings("deprecation")
    private static <T> void fillSheetForFlowInfo(HSSFSheet sheet, List<T> dataList, LinkedHashMap<String, String> headers,
            int firstIndex, int lastIndex, Map<String, Object> params, HSSFCellStyle headStyle,
            HSSFCellStyle titleStyle, HSSFCellStyle contentStyle) throws Exception {

        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[headers.size()];
        String[] cnFields = new String[headers.size()];

        // 填充数组
        int count = 0;
        for (Entry<String, String> entry : headers.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        // 设置标题行
        HSSFRow row = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cnFields.length - 1));
        row.createCell(0);
        row.setHeightInPoints(40);
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        if(params.get("objName")==null){
        	getHeaderContent(params);
        }
        String objName = (String) params.get("objName");
        sheet.getRow(0).getCell(0).setCellValue("对象：" + objName + "  日期范围：" + startDate + "—" + endDate + "    导出日期：" + com.aiiju.util.common.DateUtil.getCurDay());
        sheet.getRow(0).getCell(0).setCellStyle(headStyle);

        // 填充表头
        row = sheet.createRow(1);
        for (short i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            HSSFRichTextString text = new HSSFRichTextString(cnFields[i]);
            cell.setCellValue(text);
        }

        // 填充内容
        for (int index = firstIndex; index <= lastIndex; index++) {
            // 创建行
            row = sheet.createRow(index + 2);
            // 获取单个对象
            T item = dataList.get(index);
            // 数据写入表格
            for (int i = 0; i < enFields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(contentStyle);
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = "";
                if (objValue instanceof Date) {
                    Date date = (Date) objValue;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    fieldValue = sdf.format(date);
                } else {
                    fieldValue = objValue == null ? "" : objValue.toString();
                }
                HSSFRichTextString richString = new HSSFRichTextString(fieldValue);
                cell.setCellValue(richString);
            }
        }

    }
    
    /**
     * 
     * @Title: getFieldValueByNameSequence 
     * @Description: 根据带路径或不带路径的属性名获取属性值。即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等 
     * @param fieldNameSequence 
     * @param o 
     * @return 
     * @throws Exception 
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {

        Object value = null;

        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据属性名获取属性对象
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;
    }
    
    /**
     * 
     * @Title: getFieldValueByName 
     * @Description: 根据字段名获取字段值
     * @param fieldName
     * @param o
     * @return
     * @throws Exception 
     * @throws
     */
    private static Object getFieldValueByName(String fieldName, Object o) throws Exception {

        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }

        return value;
    }
    
    /**
     * 改变表头部分表格字体的颜色
     * @param sheet
     * @param cell
     * @param array 需要改变字体颜色的单元格序号数组
     * @param i 当前单元格的序号 0开始
     */
	private static void changeLocalFontColor(HSSFSheet sheet, HSSFCell cell, int[] array, int i) {
		if (ArrayUtils.contains(array, i)) {
			HSSFFont font = sheet.getWorkbook().createFont();
			font.setColor(HSSFColor.RED.index);
			font.setFontHeightInPoints((short) 14);
			HSSFCellStyle arrayStyle = sheet.getWorkbook().createCellStyle();
			arrayStyle.setFont(font);
			arrayStyle.setFillPattern(HSSFCellStyle.NO_FILL);
//			arrayStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
			arrayStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			arrayStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			arrayStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			arrayStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			arrayStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			// 把字体应用到当前的样式
			arrayStyle.setFont(font);
			cell.setCellStyle(arrayStyle);
		}
	}
    
    /**
     * 设置下拉列表
     * @param sheet
     * @param dropDownList
     * @param i
     */
    private static void setDropDownList(HSSFSheet sheet, Map<String, Object> dropDownMap, int i) {
    	if (dropDownMap.containsKey(String.valueOf(i))) {
    		CellRangeAddressList regions = new CellRangeAddressList(1, 65535, i, i);
            // 生成下拉框内容  
            DVConstraint constraint = DVConstraint.createExplicitListConstraint((String[]) dropDownMap.get(String.valueOf(i)));
            // 绑定下拉框和作用区域  
            HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
            sheet.addValidationData(data_validation);
    	}
    }
    
    // 导入Excel
    /**
	 * 
	 * @param file
	 *            导入的excel文件
	 * @param sheetNum
	 *            excel工作空间,一般情况为0
	 * @param entityClass
	 *            List中对象的类型（Excel中的每一行都要转化为该类型的对象）
	 * @param fieldMap
	 *            Excel中的中文列头和类的英文属性的对应关系Map
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> exportListFromExcel(File file, int sheetNum, Class<T> entityClass,
			LinkedHashMap<String, String> fieldMap) throws IOException {
		return exportListFromExcel(new FileInputStream(file), FilenameUtils.getExtension(file.getName()), sheetNum,
				entityClass, fieldMap);
	}

	/**
	 * 由Excel流的Sheet导出至List
	 * 
	 * @param is
	 * @param extensionName
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> exportListFromExcel(InputStream is, String extensionName, int sheetNum,
			Class<T> entityClass, LinkedHashMap<String, String> fieldMap) throws IOException {

		Workbook workbook = null;

		if (extensionName.toLowerCase().equals(XLS)) {
			workbook = new HSSFWorkbook(is);
		} else if (extensionName.toLowerCase().equals(XLSX)) {
			workbook = new XSSFWorkbook(is);
		}

		// return readCell(workbook, sheetNum, fieldMap);
		return excelToList(workbook, sheetNum, entityClass, fieldMap);
	}

	/**
	 * 读取Cell
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param fieldMap
	 * @return
	 */
	public static List<Map<String, Object>> readCell(Workbook workbook, int sheetNum,
			LinkedHashMap<String, String> fieldMap) {
		Sheet sheet = workbook.getSheetAt(sheetNum);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 获取第一行的表头内容
		Row firstRow = sheet.getRow(0);
		List<String> excelFieldNames = new ArrayList<String>();
		firstRow.forEach(cell -> excelFieldNames.add(cell.getStringCellValue()));

		System.out.println("======" + excelFieldNames.toString());

		boolean isExist = true;
		for (String cnName : fieldMap.keySet()) {
			if (!excelFieldNames.contains(cnName)) {
				isExist = false;
				break;
			}
		}

		// 如果有列名不存在，则抛出异常，提示错误
		if (!isExist) {
			throw new BizException("Excel中缺少必要的字段，或字段名称有误");
		}

		// 将列名和列号放入Map中,这样通过列名就可以拿到列号
		LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < excelFieldNames.size(); i++) {
			colMap.put(excelFieldNames.get(i), firstRow.getCell(i).getColumnIndex());
		}
		System.out.println();

		// 除去表头即第一行
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Map<String, Object> map = new HashMap<String, Object>();
			// 遍历所有列
			for (Cell cell : row) {

				// 获取单元格的类型
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
				String key = cellRef.formatAsString();

				switch (cell.getCellType()) {
				// 字符串
				case Cell.CELL_TYPE_STRING:
					map.put(key, cell.getRichStringCellValue().getString() == null ? ""
							: cell.getRichStringCellValue().getString());
					break;
				// 数字
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						map.put(key, cell.getDateCellValue());
					} else {
						map.put(key, cell.getNumericCellValue());
					}
					break;
				// boolean
				case Cell.CELL_TYPE_BOOLEAN:
					map.put(key, cell.getBooleanCellValue());
					break;
				// 方程式
				case Cell.CELL_TYPE_FORMULA:
					map.put(key, cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
				// 空值
				default:
					map.put(key, "");
				}
			}
			list.add(map);
		}
		return list;

	}

	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param entityClass
	 *            List中对象的类型（Excel中的每一行都要转化为该类型的对象）
	 * @param fieldMap
	 *            Excel中的中文列头和类的英文属性的对应关系Map
	 * @return
	 * @throws BizException
	 */
	public static <T> List<T> excelToList(Workbook workbook, int sheetNum, Class<T> entityClass,
			LinkedHashMap<String, String> fieldMap) throws BizException {

		// 定义要返回的list
		List<T> resultList = new ArrayList<T>();

		try {

			Sheet sheet = workbook.getSheetAt(sheetNum);

			// 解析公式结果
			// FormulaEvaluator evaluator =
			// workbook.getCreationHelper().createFormulaEvaluator();

			// 遍历所有行
			// for (Row row : sheet)

			// 获取第一行的表头内容
			Row firstRow = sheet.getRow(0);
			List<String> excelFieldNames = new ArrayList<String>();
			firstRow.forEach(cell -> excelFieldNames.add(cell.getStringCellValue()));

			System.out.println("======" + excelFieldNames.toString());

			boolean isExist = true;
			for (String cnName : fieldMap.keySet()) {
				if (!excelFieldNames.contains(cnName)) {
					isExist = false;
					break;
				}
			}

			// 如果有列名不存在，则抛出异常，提示错误
			if (!isExist) {
				throw new BizException("Excel中缺少必要的字段，或字段名称有误");
			}

			// 将列名和列号放入Map中,这样通过列名就可以拿到列号
			LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < excelFieldNames.size(); i++) {
				colMap.put(excelFieldNames.get(i), firstRow.getCell(i).getColumnIndex());
			}
			System.out.println();
			
			// 将sheet转换为list
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				// 新建要转换的对象
				T entity = entityClass.newInstance();

				for (int j = 0; j < fieldMap.size(); j++) {
					String entrykey = "";
					String enNormalName = "";
					for (Entry<String, String> entry : fieldMap.entrySet()) {
						// 获取中文字段名
						entrykey = entry.getKey();
						// 获取英文字段名
						enNormalName = entry.getValue();
						String content = "";
						if (null != sheet.getRow(i).getCell(colMap.get(entrykey))) {
							switch (sheet.getRow(i).getCell(colMap.get(entrykey)).getCellType()) {
							case Cell.CELL_TYPE_STRING:
								content = sheet.getRow(i).getCell(colMap.get(entrykey)).getRichStringCellValue()
										.getString();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(sheet.getRow(i).getCell(colMap.get(entrykey)))) {
									content = sheet.getRow(i).getCell(colMap.get(entrykey)).getDateCellValue()
											.toString();
								} else {
									content = String.valueOf(
											sheet.getRow(i).getCell(colMap.get(entrykey)).getNumericCellValue());
								}
							case Cell.CELL_TYPE_BOOLEAN:
								content = String
										.valueOf(sheet.getRow(i).getCell(colMap.get(entrykey)).getBooleanCellValue());
							case Cell.CELL_TYPE_FORMULA:
								if (DateUtil.isCellDateFormatted(sheet.getRow(i).getCell(colMap.get(entrykey)))) {
									content = String
											.valueOf(sheet.getRow(i).getCell(colMap.get(entrykey)).getDateCellValue());
								} else {
									content = sheet.getRow(i).getCell(colMap.get(entrykey)).getCellFormula().toString();
								}
							case Cell.CELL_TYPE_BLANK:
								break;
							default:
								System.err.println("Data error for cell of excel: "
										+ sheet.getRow(i).getCell(colMap.get(entrykey)).getCellType());
								break;
							}
						}
						// 给对象赋值
						setFieldValueByName(enNormalName, content, entity);
					}
				}
				resultList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof BizException) {
				throw (BizException) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				e.printStackTrace();
				throw new BizException("导入Excel失败");
			}
		}
		return resultList;
	}

	/**
	 * 根据字段名获取字段
	 * 
	 * @param fieldName
	 *            字段名
	 * @param clazz
	 *            包含该字段的类
	 * @return
	 */
	private static Field getFieldByName(String fieldName, Class<?> clazz) {
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		// 如果本类中存在该字段，则返回
		for (Field field : selfFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != Object.class) {
			return getFieldByName(fieldName, superClazz);
		}

		// 如果本类和父类都没有，则返回空
		return null;
	}

	/**
	 * 根据字段名给对象的字段赋值
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param o
	 * @throws Exception
	 */
	private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {

		Field field = getFieldByName(fieldName, o.getClass());
		if (field != null) {
			field.setAccessible(true);
			// 获取字段类型
			Class<?> fieldType = field.getType();

			// 根据字段类型给字段赋值
			if (String.class == fieldType) {
				field.set(o, String.valueOf(fieldValue));
			} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
				field.set(o, Integer.parseInt(fieldValue.toString()));
			} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
				field.set(o, Long.valueOf(fieldValue.toString()));
			} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
				field.set(o, Float.valueOf(fieldValue.toString()));
			} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
				field.set(o, Short.valueOf(fieldValue.toString()));
			} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
				field.set(o, Double.valueOf(fieldValue.toString()));
			} else if (Character.TYPE == fieldType) {
				if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
					field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
				}
			} else if (Date.class == fieldType) {
				field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
			} else {
				field.set(o, fieldValue);
			}
		} else {
			throw new BizException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
		}
	}
	
	private static void getHeaderContent(Map<String, Object> params) {
        Object sendToDept = params.get("sendToDept") == null ? null : params.get("sendToDept").toString();
        Object sendToPerson = params.get("sendToPerson") == null ? null : params.get("sendToPerson").toString();
        List<BaseResult> deptPojos = new ArrayList<BaseResult>();
        List<BaseResult> specialUserPojos = new ArrayList<BaseResult>();
        Gson gson = new Gson();
        if (null != sendToDept) {
            // 前端给出的部门节点
            deptPojos = gson.fromJson(sendToDept.toString(), new TypeToken<List<BaseResult>>(){}.getType());
        }
        if (null != sendToPerson) {
            // 前端给出的用户节点
            specialUserPojos = gson.fromJson(sendToPerson.toString(), new TypeToken<List<BaseResult>>(){}.getType());
        }
        // 合并List
        deptPojos.addAll(specialUserPojos);
        if (null != deptPojos && deptPojos.size() > 0) {
            // 获取对象名称
            StringBuffer names = new StringBuffer();
            deptPojos.forEach(apiResponse -> names.append(apiResponse.getName() + ","));
            String objName = "";
            if (null != names && names.length() > 0) {
                objName = names.substring(0, names.length() - 1);
            }
            params.put("objName", objName);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Object>> rows = ExcelUtil.readRows("D:/职务信息.xls");
        for (ArrayList<Object> row: rows) {
        	System.out.println("===========开始读取一行");
        	if (row.contains("")) {
        		System.out.println("空值1");
        	}
            System.out.println(row.toString());
            System.out.println("==========读完一行");
        }
    }
}
