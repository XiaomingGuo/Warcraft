package com.office.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class ExcelOperationUtil {
	
public boolean CreateExcelFile(String filePath,String fileName){
	try{
		HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel工作簿对象	
		HSSFSheet sheet = workbook.createSheet();//在工作簿中创建工作表对象
		workbook.setSheetName(0, "测试");//设置工作表的名称
		HSSFRow row = sheet.createRow(0);//在工作表中创建第1行对象
		HSSFCell label_num = row.createCell(0);//第1行的第1个单元格
		label_num.setCellValue("数字类型");//添加字符串
		HSSFCell label_date = row.createCell(1);//第1行的第2个单元格
		label_date.setCellValue("日期时间类型");//添加字符串
		HSSFCell label_bool = row.createCell(2);//第1行的第3个单元格
		label_bool.setCellValue("布尔类型");//添加字符串
		HSSFRow row2 = sheet.createRow(1);//在工作表中创建第2行对象
		HSSFCell num_cell = row2.createCell(0);//第2行的第1个单元格
		num_cell.setCellValue(3.1415926);//添加数字
		HSSFCell date_cell = row2.createCell(1);//第2行的第2个单元格
		date_cell.setCellValue(Calendar.getInstance());//添加日期时间
		HSSFCell bool_cell = row2.createCell(2);//第2行的第3个单元格
		bool_cell.setCellValue(false);//添加布尔值
		File xlsFile = new File(filePath,fileName);
		FileOutputStream fos = new FileOutputStream(xlsFile);
		workbook.write(fos);//将文档对象写入文件输出流
		fos.close();
		workbook.close();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	} 
}
}
