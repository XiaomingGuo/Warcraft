package com.office.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.Warcraft.Interface.IExcelExecute;

public class ExcelRead implements IExcelExecute
{
	private String path, fileName;
	private FileInputStream fileStream = null;
	private HSSFWorkbook hWorkBook = null;
	private HSSFSheet hWorkSheet = null;
	private HSSFRow hWorkRow = null;
	private HSSFCell hWorkCell = null;
	
	//construct
	public ExcelRead(String path, String fileName)
	{
		this.path = path;
		this.fileName = fileName;
	}

	//Support
	private String parseCellValue()
	{
		String rtnVal = "";
		switch(hWorkCell.getCellType())
		{
		case HSSFCell.CELL_TYPE_STRING:
			rtnVal = hWorkCell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			rtnVal = String.valueOf(hWorkCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			rtnVal = null;
			break;
		default:
			rtnVal = null;
			break;
		}
		return (rtnVal != null)?rtnVal:"";
	}
	
	//Implement interface
	@Override
	public String getPath()
	{
		return path;
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public void setWorkBook()
	{
		try
		{
			fileStream = new FileInputStream(new File(path + "\\" + fileName));
			hWorkBook = new HSSFWorkbook(fileStream);
		}
		catch (FileNotFoundException e)
		{
			closeFile();
			e.printStackTrace();
		}
		catch (IOException e)
		{
			closeWorkBook();
			closeFile();
			e.printStackTrace();
		}
	}

	@Override
	public boolean setWorkSheet(String sheet)
	{
		if (null != hWorkBook)
		{
			hWorkSheet = hWorkBook.getSheet(sheet);
		}
		else
		{
			hWorkSheet = null;
		}
		return false;
	}

	@Override
	public void setWorkRow(int iRow)
	{
		if (null != hWorkSheet)
		{
			hWorkRow = hWorkSheet.getRow(iRow);
		}
		else
		{
			hWorkRow = null;
		}
	}

	@Override
	public void setWorkCell(int iCol)
	{
		if (null != hWorkRow)
		{
			hWorkCell = hWorkRow.getCell(iCol);
		}
		else
		{
			hWorkCell = null;
		}
	}

	@Override
	public String getCellValue()
	{
		if (null != hWorkCell)
		{
			return parseCellValue();
		}
		else
		{
			return null;
		}
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
	public void setCellValue(String setVal)
	{
		
	}

	
	@Override
	public void saveToFile()
	{
		
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
