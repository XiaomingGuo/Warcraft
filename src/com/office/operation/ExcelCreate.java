package com.office.operation;

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

import com.Warcraft.Interface.IExcelExecute;

public class ExcelCreate implements IExcelExecute
{
	private String path, fileName;
	private FileOutputStream fileStream = null;
	private HSSFWorkbook hWorkBook = null;
	private HSSFSheet hWorkSheet = null;
	private HSSFRow hWorkRow = null;
	private HSSFCell hWorkCell = null;

	//construct
	public ExcelCreate(String path, String fileName)
	{
		this.path = path;
		this.fileName = fileName;
	}
	
	//Implement interface
	@Override
	public void setWorkBook()
	{
		//fileStream = new FileOutputStream(new File(path + "\\" + fileName));
		if(null != hWorkBook)
		{
			hWorkBook = new HSSFWorkbook();
		}
		else
		{
			closeWorkBook();
			hWorkBook = new HSSFWorkbook();
		}
	}

	@Override
	public String getPath()
	{
		return this.path;
	}

	@Override
	public String getFileName()
	{
		return this.fileName;
	}

	@Override
	public boolean setWorkSheet(String sheet)
	{
		if (null != hWorkBook)
		{
			hWorkSheet = hWorkBook.getSheet(sheet);
			if(null == hWorkSheet)
			{
				hWorkSheet = hWorkBook.createSheet();
				hWorkBook.setSheetName(hWorkBook.getSheetIndex(hWorkSheet), sheet);
				return true;
			}
		}
		return false;
	}

	@Override
	public void setWorkRow(int iRow)
	{
		if (null != hWorkSheet)
		{
			hWorkRow = hWorkSheet.createRow(hWorkSheet.getPhysicalNumberOfRows());
		}
	}

	@Override
	public void setWorkCell(int iCol)
	{
		if (null != hWorkRow)
		{
			hWorkCell = hWorkRow.createCell(iCol);
		}
	}
	
	@Override
	public void setCellValue(String setVal)
	{
		if(null != hWorkCell)
		{
			hWorkCell.setCellValue(setVal);
		}
	}
	
	@Override
	public String getCellValue()
	{
		return null;
	}
	
	@Override
	public void closeFile()
	{
		try
		{
			if (null != fileStream)
			{
				fileStream.close();
				fileStream = null;
			}
		}
		catch (IOException e)
		{
			fileStream = null;
			e.printStackTrace();
		}
	}

	@Override
	public void closeWorkBook()
	{
		try
		{
			if (null != hWorkBook)
			{
				hWorkBook.close();
				hWorkBook = null;
			}
		}
		catch (IOException e)
		{
			hWorkBook = null;
			e.printStackTrace();
		}
	}

	
	@Override
	public void saveToFile()
	{
		try
		{
			fileStream = new FileOutputStream(new File(path, fileName));
			hWorkBook.write(fileStream);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeFile();
			closeWorkBook();
		}
	}

	@Override
	public void createWorkRow(int iRow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkCellWithBorder(int iRow) {
		// TODO Auto-generated method stub
		
	}

}
