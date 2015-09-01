package com.office.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mockito.asm.util.CheckAnnotationAdapter;

public class MSExcel
{
	public String getStringCellValue()
	{
		String tempValue = "";
		if (hWorkCell != null)
		{
			switch(hWorkCell.getCellType())
			{
			case HSSFCell.CELL_TYPE_STRING:
				tempValue = hWorkCell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				tempValue = String.valueOf(hWorkCell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				tempValue = "";
				break;
			default:
				break;
			}
		}
		return (tempValue != null)?tempValue:"";
	}
	
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

	public HSSFSheet getWorkSheet()
	{
		return hWorkSheet;
	}

	public void setWorkSheet(String sheet)
	{
		if (hWorkBook != null)
		{
			this.hWorkSheet = hWorkBook.getSheet(sheet);
		}
	}
	
	public HSSFRow gethWorkRow()
	{
		return hWorkRow;
	}

	public void sethWorkRow(int iRow)
	{
		if (hWorkSheet != null)
		{
			this.hWorkRow = hWorkSheet.getRow(iRow);
		}
	}
	
	public HSSFCell gethWorkCell()
	{
		return hWorkCell;
	}

	public void sethWorkCell(int iCol)
	{
		if (hWorkRow != null)
		{
			this.hWorkCell = hWorkRow.getCell(iCol);
		}
	}

	public void CloseWorkBook() throws IOException
	{
		if (this.hWorkBook != null)
		{
			this.hWorkBook.close();
			this.hWorkBook = null;
		}
	}
	
	public void CloseFile() throws IOException
	{
		if (this.file != null)
		{
			this.file.close();
			this.file = null;
		}
	}
	
	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
