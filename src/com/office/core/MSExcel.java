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
	    	if (!(new File(filePath).isDirectory())) //����ļ��в�����
	    	{
	    		new File(filePath).mkdir();      //������ excel �ļ��У��������ļ���
	    	}
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();        //�����ļ���ʧ�� 
	        String ErrName=java.net.URLEncoder.encode("�ļ��в����ڡ������ļ��г���!");
	        rtnRst = false;
	    }
	    return rtnRst;
	}
	
	public void CheckAndCreateFile(String filePath, String fileName)
	{
		try
		{
			HSSFWorkbook tempWorkbook = new HSSFWorkbook();//����Excel����������	
			tempWorkbook.createSheet();//�ڹ������д������������
			tempWorkbook.setSheetName(0, "Sheet1");//���ù����������
			File xlsFile = new File(filePath,fileName);
			FileOutputStream fos = new FileOutputStream(xlsFile);
			tempWorkbook.write(fos);//���ĵ�����д���ļ������
			fos.close();
			tempWorkbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
}
