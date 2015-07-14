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
		HSSFWorkbook workbook = new HSSFWorkbook();//����Excel����������	
		HSSFSheet sheet = workbook.createSheet();//�ڹ������д������������
		workbook.setSheetName(0, "����");//���ù����������
		HSSFRow row = sheet.createRow(0);//�ڹ������д�����1�ж���
		HSSFCell label_num = row.createCell(0);//��1�еĵ�1����Ԫ��
		label_num.setCellValue("��������");//����ַ���
		HSSFCell label_date = row.createCell(1);//��1�еĵ�2����Ԫ��
		label_date.setCellValue("����ʱ������");//����ַ���
		HSSFCell label_bool = row.createCell(2);//��1�еĵ�3����Ԫ��
		label_bool.setCellValue("��������");//����ַ���
		HSSFRow row2 = sheet.createRow(1);//�ڹ������д�����2�ж���
		HSSFCell num_cell = row2.createCell(0);//��2�еĵ�1����Ԫ��
		num_cell.setCellValue(3.1415926);//�������
		HSSFCell date_cell = row2.createCell(1);//��2�еĵ�2����Ԫ��
		date_cell.setCellValue(Calendar.getInstance());//�������ʱ��
		HSSFCell bool_cell = row2.createCell(2);//��2�еĵ�3����Ԫ��
		bool_cell.setCellValue(false);//��Ӳ���ֵ
		File xlsFile = new File(filePath,fileName);
		FileOutputStream fos = new FileOutputStream(xlsFile);
		workbook.write(fos);//���ĵ�����д���ļ������
		fos.close();
		workbook.close();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	} 
}
}
