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
	    	if (!(new File(filePath).isDirectory()))
	    	{
	    		new File(filePath).mkdir();
	    	}
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        String ErrName=java.net.URLEncoder.encode("�ļ��в����ڡ������ļ��г���!");
	        rtnRst = false;
	    }
	    return rtnRst;
	}
	
	public void CheckAndCreateFile(String filePath, String fileName)
	{
		try
		{
			HSSFWorkbook tempWorkbook = new HSSFWorkbook();
			tempWorkbook.createSheet();
			tempWorkbook.setSheetName(0, "Sheet1");
			File xlsFile = new File(filePath,fileName);
			FileOutputStream fos = new FileOutputStream(xlsFile);
			tempWorkbook.write(fos);
			fos.close();
			tempWorkbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
}
