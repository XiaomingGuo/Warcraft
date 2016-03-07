package com.office.core;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MSExcel
{
	public boolean CheckAndCreatePath(String filePath)
	{
		boolean rtnRst = true;
	    try
	    {
	    	if (!(new File(filePath).isDirectory())) //如果文件夹不存在
	    	{
	    		new File(filePath).mkdir();      //不存在 excel 文件夹，则建立此文件夹
	    	}
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();        //创建文件夹失败 
	        String ErrName=java.net.URLEncoder.encode("文件夹不存在。创建文件夹出错!");
	        rtnRst = false;
	    }
	    return rtnRst;
	}
	
	public void CheckAndCreateFile(String filePath, String fileName)
	{
		try
		{
			HSSFWorkbook tempWorkbook = new HSSFWorkbook();//创建Excel工作簿对象	
			tempWorkbook.createSheet();//在工作簿中创建工作表对象
			tempWorkbook.setSheetName(0, "Sheet1");//设置工作表的名称
			File xlsFile = new File(filePath,fileName);
			FileOutputStream fos = new FileOutputStream(xlsFile);
			tempWorkbook.write(fos);//将文档对象写入文件输出流
			fos.close();
			tempWorkbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
}
