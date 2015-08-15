package com.office.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class ExcelOperationUtil
{
	public List<List<String>> ReadExcel(String path, String fileName, String sheet, int[] startCell, int[] EndCell) throws FileNotFoundException
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		try
		{
			FileInputStream file = new FileInputStream(new File(path + "\\" + fileName));
			HSSFWorkbook hWorkBook = new HSSFWorkbook(file);
			HSSFSheet hWorkSheet = hWorkBook.getSheet(sheet);
			for (int iRow = startCell[0]-1; iRow < EndCell[0]; iRow++)
			{
				List<String> rowList = new ArrayList<String>();
				HSSFRow hWorkRow = hWorkSheet.getRow(iRow);
				for (int iCol = startCell[1]-1; iCol < EndCell[1]; iCol++)
				{
					HSSFCell hWorkCell = hWorkRow.getCell(iCol);
					if (hWorkCell != null)
					{
						String tempValue = "";
						switch(hWorkCell.getCellType())
						{
						case HSSFCell.CELL_TYPE_STRING:
							tempValue = hWorkCell.getStringCellValue();
							if(tempValue != null)
							{
								rowList.add(tempValue);
							}
							else
							{
								rowList.add("");
							}
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							tempValue = String.valueOf(hWorkCell.getNumericCellValue());
							if(tempValue != null)
							{
								rowList.add(tempValue);
							}
							else
							{
								rowList.add("");
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							rowList.add("");
							break;
						default:
							break;
						}
					}
				}
				rtnRst.add(rowList);
			}
			file.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			;
		}
		return rtnRst;
	}
	
	public boolean WriteDataToExcel(String fileFullPath, String fileName, List<List<String>> contentList)
	{
		boolean rtnRst = true;
	    try
	    {
	    	if (!(new File(fileFullPath).isDirectory())) //如果文件夹不存在
	    	{
	    		new File(fileFullPath).mkdir();      //不存在 excel 文件夹，则建立此文件夹
	    	}
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();        //创建文件夹失败 
	        String ErrName=java.net.URLEncoder.encode("文件夹不存在。创建文件夹出错!");
	        rtnRst = false;
	    }
	    
	    try
		{
			HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel工作簿对象	
			HSSFSheet sheet = null;//在工作簿中创建工作表对象
			String saveVendorName = "";
			int iCount = 1;
			int iSheetCount = 0;
			for (int iRow = 1; iRow < contentList.size(); iRow++)
			{
				String vendorName = contentList.get(iRow).get(9);
				if (!saveVendorName.contentEquals(vendorName))
				{
					iCount = 1;
					sheet = workbook.createSheet();//在工作簿中创建工作表对象
					workbook.setSheetName(iSheetCount, vendorName);//设置工作表的名称
					saveVendorName = vendorName;
					HSSFRow row = sheet.createRow(0);//在工作表中创建第1行对象
					for(int iCol=0; iCol < contentList.get(0).size(); iCol++)
					{
						HSSFCell label_num = row.createCell(iCol);//第1行的第1个单元格
						label_num.setCellValue(contentList.get(0).get(iCol));//添加字符串
					}
					iSheetCount += 1;
				}
				
				HSSFRow row = sheet.createRow(iCount);//在工作表中创建第1行对象
				for(int iCol=0; iCol < contentList.get(iRow).size(); iCol++)
				{
					HSSFCell label_num = row.createCell(iCol);//第1行的第1个单元格
					label_num.setCellValue(contentList.get(iRow).get(iCol));//添加字符串
				}
				iCount += 1;
			}
			File xlsFile = new File(fileFullPath, fileName);
			FileOutputStream fos = new FileOutputStream(xlsFile);
			workbook.write(fos);//将文档对象写入文件输出流
			fos.close();
			workbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rtnRst = false;
		} 
		return rtnRst;
	}
	
	public boolean CreateExcelFile(String filePath,String fileName)
	{
		try
		{
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		} 
	}
}
